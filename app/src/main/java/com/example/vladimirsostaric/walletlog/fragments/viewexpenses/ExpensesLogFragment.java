package com.example.vladimirsostaric.walletlog.fragments.viewexpenses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.vladimirsostaric.walletlog.R;
import com.example.vladimirsostaric.walletlog.fragments.PlaceholderFragment;
import com.example.vladimirsostaric.walletlog.model.Expense;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExpensesLogFragment extends PlaceholderFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        final List<Expense> loggedExpenses = expenses;
        Collections.sort(loggedExpenses, new Comparator<Expense>() {
            @Override
            public int compare(Expense o1, Expense o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        final String[] expensesLog = getExpensesLog(expenses);
        final ListAdapter expenseLogAdapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(),
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
            expensesLog.add(expense.toString());
        }

        return expensesLog.toArray(new String[]{});

    }

}
