package sg.com.kaplan.pdma.currencyexchangerates;


public class Currency {
    String currency;
    double value;

    public Currency(String currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }
}
