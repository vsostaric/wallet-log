package com.example.vladimirsostaric.walletlog.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vladimirsostaric.walletlog.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings, container, false);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("CURRENCY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        List<String> currencies = new ArrayList<>();
        currencies.add("USD");
        currencies.add("HRK");
        currencies.add("EUR");

        Spinner spinner = (Spinner) view.findViewById(R.id.currencySpinner);
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, currencies);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(currencyAdapter);
        spinner.setVisibility(View.VISIBLE);
        return view;

    }
}
