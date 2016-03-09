package sg.com.kaplan.pdma.currencyexchangerates;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CurrencyAdapter extends ArrayAdapter<Currency> {
    public CurrencyAdapter(Context context, ArrayList<Currency> currencies) {
        super(context, 0, currencies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Currency currency = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.currency_layout,
                    parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textView);

        textView.setText(currency.getCurrency() + " : " + currency.getValue());

        return convertView;
    }
}
