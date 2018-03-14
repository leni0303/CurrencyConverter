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

public class ObtainData extends AsyncTask<Void, Void, Void>{

    //data for Json file
    String data= "";
    String dataParsed= "";

    double resultOfCOnversion;

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

           dataParsed = rates.toString();

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

    public void covertFromEuro(double amount, double conversionRate, String endCurrency, JSONObject jsonObject) {

    }

    public void convertToEuro(double amount, String sourceCurrency) {

    }
}
