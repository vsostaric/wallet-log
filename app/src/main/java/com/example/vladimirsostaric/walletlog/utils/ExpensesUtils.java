package com.example.vladimirsostaric.walletlog.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vladimirsostaric.walletlog.R;

import java.util.List;

public class ExpensesUtils {

    public static void addExpenseTypesToSpinner(final Activity activity, final DbUtils dbUtils) {

        final List<String> typeChoices = dbUtils.getExpenseTypeNames();
        final Spinner spinner = (Spinner) activity.findViewById(R.id.typeSpinner);
        final ArrayAdapter<String> typeChoicesAdapter = new ArrayAdapter<>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, typeChoices);
        typeChoicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(typeChoicesAdapter);
        spinner.setVisibility(View.VISIBLE);

    }

}
