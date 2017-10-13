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

public class HighestExpensesFragment extends PlaceholderFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_highest_expenses;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        final Map<ExpenseType, BigDecimal> averageExpensesByType = getAverageExpensesByType(expenses);
        final String[] topExpenses = getTopExpenses(averageExpensesByType);

        final ListAdapter topExpensesAdapter = new ArrayAdapter<String>(this.getActivity().getApplicationContext(),
                R.layout.list_black_text, R.id.list_content, topExpenses);

        final ListView topExpensesList = (ListView) view.findViewById(R.id.topExpensesList);
        topExpensesList.setAdapter(topExpensesAdapter);

        return view;

    }

    private Map<ExpenseType, BigDecimal> getAverageExpensesByType(List<Expense> expenses) {

        Map<ExpenseType, Set<Expense>> expensesByType = new HashMap<>();

        for (Expense expense : expenses) {
            if (expensesByType.containsKey(expense.getType()) == false) {
                Set<Expense> newExpensesSet = new HashSet<>();
                expensesByType.put(expense.getType(), newExpensesSet);
            }
            expensesByType.get(expense.getType()).add(expense);
        }

        Map<ExpenseType, BigDecimal> avgExpenseByType = new HashMap<>();

        for (ExpenseType type : expensesByType.keySet()) {

            BigDecimal sum = BigDecimal.ZERO;
            BigDecimal num = BigDecimal.ZERO;
            for (Expense expense : expensesByType.get(type)) {

                sum = sum.add(expense.getAmount());
                num = num.add(BigDecimal.ONE);

            }

            BigDecimal avg = sum.divide(num, 2, RoundingMode.CEILING);
            avgExpenseByType.put(type, avg);

        }

        return avgExpenseByType;

    }

    private String[] getTopExpenses(Map<ExpenseType, BigDecimal> averageExpensesByType) {

        Map<ExpenseType, BigDecimal> sortedAverageExpensesByType = sortByValue(averageExpensesByType);

        List<String> topExpensesListItem = new ArrayList<>();

        for (ExpenseType type : sortedAverageExpensesByType.keySet()) {
            topExpensesListItem.add(type.getName() + " - " + sortedAverageExpensesByType.get(type).toPlainString());
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
