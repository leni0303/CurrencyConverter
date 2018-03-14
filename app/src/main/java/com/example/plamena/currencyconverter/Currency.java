package com.example.plamena.currencyconverter;

public class Currency{

    //name of the currency
    String name;
    //rate of the currency
    double rate;

    public Currency(String currencyName) {
        this.name = currencyName;
    }

    //constructor that creates a currency object with a name and rate
    //to the common currency
    public Currency(String currencyName, double currencyRate) {
        this.name = currencyName;
        this.rate = currencyRate;
    }

    //method that gets the name of the currency
    public String getName() {
        return name;
    }

    //method that gets the rate of teh currency
    public double getRate() {
        return rate;
    }

    public String toString() {
        return name;
    }
}
