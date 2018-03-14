package com.example.plamena.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    public static double amountToConvert;
    public static TextView textView;
    public static String startCurrency;

    Spinner sourceSpinner;
    Spinner endSpinner;
    EditText amountText;
    Button convertButton;

    Currency currency;
    FetchData obtainData = new FetchData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView_id);
        amountText = findViewById(R.id.amount_id);
        convertButton = findViewById(R.id.convert_id);
        sourceSpinner = findViewById(R.id.spinner_source_id);
        endSpinner = findViewById(R.id.spinner_end_id);

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
