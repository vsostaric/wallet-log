package com.example.vladimirsostaric.walletlog.fragments.viewexpenses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.vladimirsostaric.walletlog.R;
import com.example.vladimirsostaric.walletlog.fragments.ViewExpensesFragment;
import com.example.vladimirsostaric.walletlog.model.Expense;
import com.example.vladimirsostaric.walletlog.utils.DbUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExpensesLogFragment extends ViewExpensesFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = super.onCreateView(inflater, container, savedInstanceState);

        final List<Expense> loggedExpenses = expenses;
        Collections.sort(loggedExpenses, new Comparator<Expense>() {
            @Override
            public int compare(Expense o1, Expense o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        final String[] expensesLog = getExpensesLog(expenses);
        final ListAdapter expenseLogAdapter = new ArrayAdapter<>(this.getActivity().getApplicationContext(),
                R.layout.list_black_text, R.id.list_content, expensesLog);

        final ListView expenseLogList = (ListView) view.findViewById(R.id.expensesLogList);
        expenseLogList.setAdapter(expenseLogAdapter);

        return view;

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_expense_log;
    }

    private String[] getExpensesLog(List<Expense> expenses) {

        List<String> expensesLog = new ArrayList<>();

        for (final Expense expense : expenses) {
            expensesLog.add(createLogRow(expense));
        }

        return expensesLog.toArray(new String[]{});

    }

    private String createLogRow(final Expense expense) {

        final SharedPreferences preferences = this.getActivity().getSharedPreferences("CURRENCY_PREF", Context.MODE_PRIVATE);
        final String currency = preferences.getString("currency", "USD");

        final StringBuilder builder = new StringBuilder();
        builder.append(expense.getAmount());
        builder.append(" ");
        builder.append(currency);
        builder.append(", ");
        builder.append(expense.getType().getName());
        builder.append(", ");
        builder.append(new SimpleDateFormat(DbUtils.DATE_FORMAT).format(expense.getDate()));

        return  builder.toString();

    }

}
