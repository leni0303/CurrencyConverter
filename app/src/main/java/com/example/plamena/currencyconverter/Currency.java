package com.example.plamena.currencyconverter;

/**
 * Class that represents a currency object.
 */
public class Currency{

    //name of the currency
    String name;
    //rate of the currency
    double rate;

    /**
     * Constructor that creates a currency object with a name.
     * @param currencyName name of teh currency
     */
    public Currency(String currencyName) {
        this.name = currencyName;
    }

    /**
     * Constructor that creates a currency object with a name and rate
     * to the common currency.
     * @param currencyName name of the currency
     * @param currencyRate rate of the currency
     */
    public Currency(String currencyName, double currencyRate) {
        this.name = currencyName;
        this.rate = currencyRate;
    }

    /**
     * method that gets the name of the currency
     * @return name of the currency
     */
    public String getName() {
        return name;
    }

    /**
     * Method that gets the rate of teh currency.
     * @return rate of the currency
     */
    public double getRate() {
        return rate;
    }

}
