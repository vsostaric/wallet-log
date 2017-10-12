package com.example.vladimirsostaric.walletlog.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladimirsostaric.walletlog.fragments.viewexpenses.ExpensesLogFragment;
import com.example.vladimirsostaric.walletlog.fragments.viewexpenses.HighestExpensesFragment;

public abstract class PlaceholderFragment extends Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";

    protected abstract int getLayout();

    public static Fragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = null;

        if(sectionNumber == 0) {
            fragment = new HighestExpensesFragment();
        } else if(sectionNumber == 1) {
            fragment = new HighestExpensesFragment();
        } else if(sectionNumber == 2) {
            fragment = new ExpensesLogFragment();
        }

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        return rootView;
    }
}
