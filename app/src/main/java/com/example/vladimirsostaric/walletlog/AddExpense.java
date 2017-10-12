package com.example.vladimirsostaric.walletlog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladimirsostaric.walletlog.model.Expense;
import com.example.vladimirsostaric.walletlog.model.ExpenseType;
import com.example.vladimirsostaric.walletlog.utils.DbUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AddExpense extends AppCompatActivity implements BackInterface {

    private DbUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        dbUtils = new DbUtils(getApplicationContext());

        List<String> typeChoices = dbUtils.getExpenseTypeNames();

        SharedPreferences preferences = getSharedPreferences("CURRENCY_PREF", Context.MODE_PRIVATE);
        String currency = preferences.getString("currency", "USD");

        TextView currencyView = (TextView) findViewById(R.id.addExpenseCurrency);
        currencyView.setText(currency);

        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        ArrayAdapter<String> typeChoicesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, typeChoices);
        typeChoicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(typeChoicesAdapter);
        spinner.setVisibility(View.VISIBLE);

    }

    @Override
    public void back(View view) {
        Intent backToMain = new Intent();
        backToMain.setClass(this, MainActivity.class);
        startActivity(backToMain);
    }

    public void addExpense(View view) {

        EditText amountInputView = (EditText) findViewById(R.id.amountInputField);
        String amountInput = amountInputView.getText().toString();

        if (amountInput == null || amountInput.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Amount is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        Expense expense = new Expense();

        expense.setAmount(new BigDecimal(amountInputView.getText().toString()).setScale(2, BigDecimal.ROUND_DOWN));

        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        expense.setType(new ExpenseType(spinner.getSelectedItem().toString()));

        expense.setDate(new Date());

        dbUtils.insertExpense(expense);

        Toast.makeText(getApplicationContext(), "New expense added.", Toast.LENGTH_SHORT).show();

        amountInputView.setText("");

    }

    public void addNewType(View view) {

        EditText newTypeInput = (EditText) findViewById(R.id.newTypeInputField);
        String newTypeName = newTypeInput.getText().toString();

        if(newTypeName == null || newTypeName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Type name is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        ExpenseType newType = new ExpenseType(newTypeName);

        dbUtils.insertExpenseType(newType);

        Toast.makeText(getApplicationContext(), "Expense type '" + newTypeName + "' was added.", Toast.LENGTH_SHORT).show();


    }

}
