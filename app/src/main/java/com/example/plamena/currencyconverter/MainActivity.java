package com.example.plamena.currencyconverter;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    double amountToConvert;
    //text view for the converted result
    public static TextView textView;
    String startCurrency,endCurrency;
    int year,month,day;

    String monthStr;
    String dateStr;

    Spinner sourceSpinner, endSpinner;
    EditText amountText;
    Button convertButton, dateButton;
    DatePickerDialog datePickerDialog;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wire elements with id's
        textView = findViewById(R.id.textView_id);
        amountText = findViewById(R.id.amount_id);
        convertButton = findViewById(R.id.convert_button_id);
        dateButton = findViewById(R.id.date_button_id);
        sourceSpinner = findViewById(R.id.spinner_source_id);
        endSpinner = findViewById(R.id.spinner_end_id);

        //spinner drop down elements
        String[] currenciesToChoose = {"EUR", "USD", "AUD","CAD","PLN", "MXN"};
        //adapter for spinner
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,currenciesToChoose);
        //attach data adapter to spinner
        sourceSpinner.setAdapter(arrayAdapter);
        //spinner onclick listener
        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //select spinner item
                startCurrency = (String) arrayAdapter.getItem(position);

                //show selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + startCurrency, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //attach data adapter to spinner
        endSpinner.setAdapter(arrayAdapter);
        //spinner onclick listener
        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //select spinner item
                endCurrency = (String) arrayAdapter.getItem(position);

                //show selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + endCurrency, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //create an instance of view.onclicklistener and connect the listener to the button
        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //create a calendar instance to parse in the dates
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);

                //get user input for dates
                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int calendarYear, int calendarMonth, int calendarDay) {
                        year = calendarYear;
                        month = (calendarMonth+1);
                        day = calendarDay;
                        //change format for month and date from "m" to "mm"
                        //2018/3/2 -> 2018/03/02
                        if(month < 10) {
                            monthStr = "0" + month;
                        } else {
                            monthStr = String.valueOf(month);
                        }
                        if(day < 10) {
                            dateStr = "0" + day;
                        } else {
                            dateStr = String.valueOf(day);
                        }
                        //changes the text on the date selection button to the selected date
                        dateButton.setText(day + "/" + month + "/" + calendarYear);
                        //set a maximum for the date picker where it can't exceed today's date
                        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                    }
                },year,month,day);
            datePickerDialog.show();
            }
        });

        //create an instance of view.onclicklistener and connect the listener to the button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gets the user input amount and checks if there is any user input
                if (amountText.getText().toString().length()>0)
                    amountToConvert =  Double.valueOf(amountText.getText().toString());

                if (year == 0)
                     Toast.makeText(MainActivity.this, "Please select a date", Toast.LENGTH_LONG).show();
                //calls the fetch data class where we get the necessary calculations for
                //conversion between currencies
                FetchData obtainData = new FetchData(amountToConvert, startCurrency, endCurrency,year,monthStr, dateStr);
                obtainData.execute();
            }
        });
    }
}
