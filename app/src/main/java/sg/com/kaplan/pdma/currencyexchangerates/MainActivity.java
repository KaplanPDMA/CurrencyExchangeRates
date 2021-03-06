package sg.com.kaplan.pdma.currencyexchangerates;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    String url = "http://api.fixer.io/latest?base=SGD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Construct the data source
        final ArrayList<Currency> currencies = new ArrayList<Currency>();

        // Create the adapter to convert the array to views
        CurrencyAdapter adapter = new CurrencyAdapter(this, currencies);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


//        final TextView textViewRates = (TextView) findViewById(R.id.textViewRates);
//        final TextView textViewBaseCurrency = (TextView) findViewById(R.id.textViewBaseCurrency);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //http://developer.android.com/training/volley/request.html
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                //textViewRates.setText("Response: " + response.toString());

                //process response
                try {
                    String base = response.getString("base");
//                    textViewBaseCurrency.setText("Base currency: " + base);
//                    textViewBaseCurrency.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject rates = response.getJSONObject("rates");

                    Iterator<String> keys = rates.keys();

                    StringBuilder sb = new StringBuilder();
                    while (keys.hasNext()) {
                        String currency = keys.next();
                        //sb.append(currency.toString() + " : " + rates.getDouble(currency) + "\n");
                        Currency c = new Currency(currency, rates.getDouble(currency));
                        currencies.add(c);
                    }
//                    textViewRates.setText(sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                textViewRates.setText("Error: " + error.toString());
            }
        });

        //http://stackoverflow.com/a/22169775/3839235
        //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions.
        //Volley does retry for you if you have specified the policy.
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }


}
