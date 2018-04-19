package com.example.a10011878.orientationproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    Spinner original, converted;
    EditText number;
    TextView newConverted;
    ArrayList<String> originalcoins,convertedcoins;
    int origChosen, convertedChosen;
    String entered;
    Double result;
    ArrayList<Cryptocurrency> arrayList;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button = findViewById(R.id.button);
        original = findViewById(R.id.spinner3);
        converted = findViewById(R.id.spinner4);
        number = findViewById(R.id.editText);
        newConverted = findViewById(R.id.textView12);
        originalcoins = new ArrayList<>();
        convertedcoins = new ArrayList<>();
        originalcoins.add("BTC");
        originalcoins.add("ETH");
        originalcoins.add("XRP");
        originalcoins.add("LTC");
        originalcoins.add("DASH");
        originalcoins.add("XRM");
        originalcoins.add("OMG");
        convertedcoins.add("BTC");
        convertedcoins.add("ETH");
        convertedcoins.add("XRP");
        convertedcoins.add("LTC");
        convertedcoins.add("DASH");
        convertedcoins.add("XRM");
        convertedcoins.add("OMG");
        Intent getter = getIntent();
        arrayList = (ArrayList<Cryptocurrency>) getter.getSerializableExtra("List");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convert();
            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number.setText("");
            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                entered = charSequence+"";
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,originalcoins);
        original.setAdapter(adapter);
        ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,convertedcoins);
        converted.setAdapter(newAdapter);
        original.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                origChosen = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        converted.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convertedChosen = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void convert(){
        Double price1 = Double.parseDouble(arrayList.get(origChosen).getPrice());
        Double price2 = Double.parseDouble(arrayList.get(convertedChosen).getPrice());
        DecimalFormat df = new DecimalFormat("#.##");
        result = ((Double.parseDouble(entered))*price2)/price1;
        newConverted.setText(df.format(result)+"");
    }
}
