package com.example.plamena.currencyconverter;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class that gathers data from the API in a Json format
 * performing it as a background process
 */

public class Currency extends AsyncTask<Void, Void, Void>{

    //data for Json file
    String data= "";
    String dataParsed= "";

    //name of the currency
    String name;
    //rate of the currency
    double rate;

    //empty constructor
    public Currency() {
    }

    //constructor that creates a currency object with a name and rate
    //to the common currency
    public Currency(String currencyName, double currencyRate) {
        this.name = currencyName;
        this.rate = currencyRate;
    }

    protected Void doInBackground(Void... voids) {
        try {
            //URL from where we are extracting data
            //sample data from URL
            /*{
            "success":true,
                    "timestamp":1394668799,
                    "historical":true,
                    "base":"EUR",
                    "date":"2014-03-12",
                    "rates":{
                        "USD":1.39031,
                        "AUD":1.546179,
                        "CAD":1.54573,
                        "PLN":4.224128,
                        "MXN":18.410208
                        }
             }*/
            //because our subscription allows to convert euro to a couple of other currencies we will use EUR as our base
            URL url = new URL("http://data.fixer.io/api/latest?access_key=b763c399e454db703701e95da6b5bac8&symbols=USD,AUD,CAD,PLN,MXN&format=1");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //gets input stream from the connection
            InputStream inputStream = httpURLConnection.getInputStream();
            //reads the data from input stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //line for every string
            String line = "";
            while(line !=null) {
                //reads each line of Json file and assign it to line
                line = bufferedReader.readLine();
                //adds lines to Json file
                data = data + line;
            }

            JSONObject root = new JSONObject(data);

            JSONObject rates = root.getJSONObject("rates");
            double usd = rates.getDouble("USD");
            double aud = rates.getDouble("AUD");
            double cad = rates.getDouble("CAD");
            double pln = rates.getDouble("PLN");
            double mxn = rates.getDouble("MXN");


            //now we create instances of these currencies
            Currency[] currencies = {new Currency("EUR",1.0),new Currency("USD",usd), new Currency("AUD",aud),
                    new Currency("CAD",cad), new Currency("PLN",pln), new Currency("MXN",mxn)};

            //user input info
            double sourceAmount = 10;
            String sourceCurrency = "USD";
            String targetCurrency = "EUR";
            //rate from source to euro
            double rateToEuro = 1.0;
            //rate from target to euro
            double rateToTarget = 1.0;

            //loops through the currency array and finds the user input
            for(Currency currency: currencies) {
                //calculates rate from souce to euro
                if (sourceCurrency == currency.getName())
                    rateToEuro = currency.getRate();
                //calculates rate from target to euro
                if (targetCurrency == currency.getName())
                    rateToTarget = currency.getRate();
            }

            //the final amount after calculations
            double targetAmount = (sourceAmount / rateToEuro) * rateToTarget;

            dataParsed = String.valueOf(targetAmount);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //can change UI here
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //call the expandable list and set its text
        MainActivity.textView.setText(this.dataParsed);
    }
    
    //method that gets the name of the currency
    public String getName() {
        return name;
    }

    //method that gets the rate of teh currency
    public double getRate() {
        return rate;
    }
}
