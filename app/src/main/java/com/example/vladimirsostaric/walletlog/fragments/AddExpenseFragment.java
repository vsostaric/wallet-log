package com.example.vladimirsostaric.walletlog.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vladimirsostaric.walletlog.R;
import com.example.vladimirsostaric.walletlog.utils.DbUtils;

import java.util.List;

public class AddExpenseFragment extends Fragment {

    private DbUtils dbUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_expense, container, false);

        dbUtils = new DbUtils(this.getActivity().getApplicationContext());

        List<String> typeChoices = dbUtils.getExpenseTypeNames();

        Spinner spinner = (Spinner) view.findViewById(R.id.typeSpinner);
        ArrayAdapter<String> typeChoicesAdapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, typeChoices);
        typeChoicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(typeChoicesAdapter);
        spinner.setVisibility(View.VISIBLE);

        return view;
    }

}
