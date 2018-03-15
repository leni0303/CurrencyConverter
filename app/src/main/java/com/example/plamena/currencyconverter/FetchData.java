package com.example.plamena.currencyconverter;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;


/**
 * Class that gathers data from the API in a Json format,
 * stores it as an array,
 * and does the calculations for the currency conversion.
 */
public class FetchData extends AsyncTask<Void, Void, Void> {

    //data for Json file
    String data= "";
    String dataParsed= "";

    //array of all currencies
    Currency[] currencies;

    //user input
    double sourceAmount = MainActivity.amountToConvert;
    String sourceCurrency = MainActivity.startCurrency;
    String endCurrency = MainActivity.endCurrency;
    //rate from source to euro
    double rateToEuro;
    //rate from end currency to euro
    double rateToEndCurrency;

    //gets the necessary user input data from the main class
    int year = MainActivity.year;
    String month = MainActivity.monthStr;
    String day = MainActivity.dateStr;

    protected Void doInBackground(Void... voids) {
        try {
            /*sample data from URL
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
                        }*/
            //URL from where we extract data
            //because our subscription allows to convert euro to a couple of other currencies we will use EUR as our base
            //data from API refreshes hourly
            URL url = new URL("http://data.fixer.io/api/" + year + "-"+ month + "-" + day + "?access_key=b763c399e454db703701e95da6b5bac8&symbols=USD,AUD,CAD,PLN,MXN&format=1");

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

            //get json object node
            JSONObject root = new JSONObject(data);

            JSONObject rates = root.getJSONObject("rates");
            //currencies rates
            double usd = rates.getDouble("USD");
            double aud = rates.getDouble("AUD");
            double cad = rates.getDouble("CAD");
            double pln = rates.getDouble("PLN");
            double mxn = rates.getDouble("MXN");


            //now we create instances of these currencies
            currencies = new Currency[]{new Currency("EUR",1.0),new Currency("USD",usd), new Currency("AUD",aud),
                    new Currency("CAD",cad), new Currency("PLN",pln), new Currency("MXN",mxn)};

            //checks currencies
            findCurrency();
            //the final amount after calculations
            double endCurrencyAmount = (sourceAmount / rateToEuro) * rateToEndCurrency;

            dataParsed = String.valueOf(endCurrencyAmount);

        } catch (MalformedURLException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Method in which we set the text view for the target amount
     * @param aVoid
     */
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //call the expandable list and set its text
        MainActivity.textView.setText(this.dataParsed);
    }

    /**
     * Method that finds the user source currency and end currency.
     */
    public void findCurrency() {
        //loops through the currency array and finds the user input
        for(Currency currency: currencies) {
            //calculates rate from source to euro
            if (sourceCurrency.equalsIgnoreCase(currency.getName()))
                rateToEuro = currency.getRate();
            //calculates rate from end currency to euro
            if (endCurrency.equalsIgnoreCase(currency.getName()))
                rateToEndCurrency = currency.getRate();
        }
    }

}
