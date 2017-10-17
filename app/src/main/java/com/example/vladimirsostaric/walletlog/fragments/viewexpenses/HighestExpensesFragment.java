package com.example.vladimirsostaric.walletlog.fragments.viewexpenses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.vladimirsostaric.walletlog.BackInterface;
import com.example.vladimirsostaric.walletlog.MainActivity;
import com.example.vladimirsostaric.walletlog.R;
import com.example.vladimirsostaric.walletlog.fragments.ViewExpensesFragment;
import com.example.vladimirsostaric.walletlog.model.Expense;
import com.example.vladimirsostaric.walletlog.model.ExpenseType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HighestExpensesFragment extends ViewExpensesFragment implements BackInterface {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = super.onCreateView(inflater, container, savedInstanceState);

        final Map<ExpenseType, BigDecimal> expensePercentageByType = getExpensePercentageByType(expenses);
        final String[] topExpenses = getTopExpenses(expensePercentageByType);

        final ListAdapter topExpensesAdapter = new ArrayAdapter<>(this.getActivity().getApplicationContext(),
                R.layout.list_black_text, R.id.list_content, topExpenses);

        final ListView topExpensesList = (ListView) view.findViewById(R.id.topExpensesList);
        topExpensesList.setAdapter(topExpensesAdapter);

        return view;

    }

    @Override
    public void back(View view) {
        Intent backToMain = new Intent();
        backToMain.setClass(this.getActivity(), MainActivity.class);
        startActivity(backToMain);
    }

    private Map<ExpenseType, BigDecimal> getExpensePercentageByType(List<Expense> expenses) {

        BigDecimal sumOfExpenses = BigDecimal.ZERO;

        for (final Expense expense : expenses) {
            sumOfExpenses = sumOfExpenses.add(expense.getAmount());
        }

        final Map<ExpenseType, BigDecimal> percentageByType = new HashMap<>();

        for (final Expense expense : expenses) {
            if (percentageByType.keySet().contains(expense.getType())) {
                final BigDecimal amount = percentageByType.get(expense.getType()).add(expense.getAmount());
                percentageByType.put(expense.getType(), amount);
            } else {
                percentageByType.put(expense.getType(), expense.getAmount());
            }
        }

        for (final ExpenseType type : percentageByType.keySet()) {
            final BigDecimal percentage = percentageByType.get(type).divide(sumOfExpenses, 4, RoundingMode.HALF_EVEN);
            percentageByType.put(type, percentage);
        }

        return percentageByType;

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_highest_expenses;
    }

    private String[] getTopExpenses(Map<ExpenseType, BigDecimal> expensesByType) {

        Map<ExpenseType, BigDecimal> sortedExpensesByType = sortByValue(expensesByType);

        List<String> topExpensesListItem = new ArrayList<>();

        for (ExpenseType type : sortedExpensesByType.keySet()) {
            topExpensesListItem.add(type.getName() + " - " + sortedExpensesByType.get(type).toPlainString() + " %");
        }


        return topExpensesListItem.toArray(new String[topExpensesListItem.size()]);

    }

    private Map<ExpenseType, BigDecimal> sortByValue(Map<ExpenseType, BigDecimal> input) {

        List<Map.Entry<ExpenseType, BigDecimal>> list = new LinkedList<>(input.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<ExpenseType, BigDecimal>>() {
            public int compare(Map.Entry<ExpenseType, BigDecimal> o1, Map.Entry<ExpenseType, BigDecimal> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<ExpenseType, BigDecimal> result = new LinkedHashMap<>();
        for (Map.Entry<ExpenseType, BigDecimal> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;

    }
}
