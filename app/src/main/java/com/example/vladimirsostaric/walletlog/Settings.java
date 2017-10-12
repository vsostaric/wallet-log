package com.example.vladimirsostaric.walletlog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vladimirsostaric.walletlog.utils.DbUtils;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity implements BackInterface {

    private DbUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        List<String> currencies = new ArrayList<>();
        currencies.add("USD");
        currencies.add("HRK");
        currencies.add("EUR");

        Spinner spinner = (Spinner) findViewById(R.id.currencySpinner);
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, currencies);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(currencyAdapter);
        spinner.setVisibility(View.VISIBLE);

        dbUtils = new DbUtils(getApplicationContext());

    }

    @Override
    public void back(View view) {
        Intent backToMain = new Intent();
        backToMain.setClass(this, MainActivity.class);
        startActivity(backToMain);
    }

    public void changeSettings(View view) {

        Spinner spinner = (Spinner) findViewById(R.id.currencySpinner);
        String currency = spinner.getSelectedItem().toString();

        SharedPreferences preferences = getSharedPreferences("CURRENCY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("currency", currency);
        editor.commit();

    }

    public void deleteAllData(View view) {

        dbUtils.resetExpenses();
        dbUtils.resetExpenseTypes();

    }

}
