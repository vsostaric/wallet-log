package com.example.vladimirsostaric.walletlog;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vladimirsostaric.walletlog.fragments.AddExpenseFragment;
import com.example.vladimirsostaric.walletlog.fragments.ViewExpensesFragment;
import com.example.vladimirsostaric.walletlog.model.Expense;
import com.example.vladimirsostaric.walletlog.model.ExpenseType;
import com.example.vladimirsostaric.walletlog.utils.DbUtils;

import java.math.BigDecimal;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DbUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbUtils = new DbUtils(getApplicationContext());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_add_expense) {
            fragment = new AddExpenseFragment();
            title = "Add Expense";
        } else if (id == R.id.nav_view_expenses) {
            fragment = new ViewExpensesFragment();
            title = "View Expenses";
        }

        if (fragment != null) {
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            tr.replace(R.id.content_frame, fragment);
            tr.commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void addExpense(View view) {

        EditText amountInputView = (EditText) findViewById(R.id.amountInputField);
        String amountInput = amountInputView.getText().toString();

        if (amountInput == null || amountInput.isEmpty()) {
            return;
        }
        BigDecimal amount = new BigDecimal(amountInputView.getText().toString()).setScale(2);

        Spinner spinner = (Spinner) findViewById(R.id.typeSpinner);
        String typeName = spinner.getSelectedItem().toString();

        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setType(new ExpenseType(typeName));
        dbUtils.insertExpense(expense);

        amountInputView.setText("");

    }

    public void addNewType(View view) {

        EditText newTypeInput = (EditText) findViewById(R.id.newTypeInputField);
        String newTypeName = newTypeInput.getText().toString();

        ExpenseType newType = new ExpenseType(newTypeName);

        dbUtils.insertExpenseType(newType);


    }
}
