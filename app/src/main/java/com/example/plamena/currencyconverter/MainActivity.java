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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    public static double amountToConvert;
    public static TextView textView;
    public static String startCurrency,endCurrency;
    public static int year,month,day;

    public static String monthStr,dateStr;

    Spinner sourceSpinner, endSpinner;
    EditText amountText;
    Button convertButton, dateButton;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView_id);
        amountText = findViewById(R.id.amount_id);
        convertButton = findViewById(R.id.convert_button_id);
        dateButton = findViewById(R.id.date_button_id);
        sourceSpinner = findViewById(R.id.spinner_source_id);
        endSpinner = findViewById(R.id.spinner_end_id);

        //array of currencies name
        String[] currenciesToChoose = {"EUR", "USD", "AUD","CAD","PLN", "MXN"};

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,currenciesToChoose);
        sourceSpinner.setAdapter(arrayAdapter);
        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startCurrency = (String) arrayAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        endSpinner.setAdapter(arrayAdapter);
        endSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                endCurrency = (String) arrayAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DATE);


                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int calendarYear, int calendarMonth, int calendarDay) {
                        year = calendarYear;
                        month = (calendarMonth+1);
                        day = calendarDay;
                        if(month < 10)
                            monthStr = "0" + month;

                        if(day < 10)
                            dateStr = "0" + day;

                        dateButton.setText(calendarDay + "-" + (calendarMonth+1) + "-" + calendarYear);
                    }
                },year,month,day);
            datePickerDialog.show();
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountToConvert =  Double.valueOf(amountText.getText().toString());
                FetchData obtainData = new FetchData();
                obtainData.execute();
            }
        });

    }
}
