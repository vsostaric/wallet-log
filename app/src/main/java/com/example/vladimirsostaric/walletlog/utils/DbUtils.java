package com.example.vladimirsostaric.walletlog.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vladimirsostaric.walletlog.model.Expense;
import com.example.vladimirsostaric.walletlog.model.ExpenseType;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DbUtils extends SQLiteOpenHelper {

    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    private static final String DATABASE_NAME = "WALLET_LOG_DB";

    private final String EXPENSE_TYPES = "expense_types";

    public DbUtils(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL("create table if not exists expense_types (name text primary key not null)");
        database.execSQL("create table if not exists expenses (id int primary key, amount real not null, type_name text not null, date text not null)");

        ContentValues unknown = new ContentValues();
        unknown.put("name", "unknown");
        database.insert(EXPENSE_TYPES, null, unknown);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<Expense> getExpenses() {

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from expenses", null);

        List<Expense> expenses = new ArrayList<>();

        while (cursor.moveToNext()) {

            Expense expense = new Expense();
            try {
                expense.setDate(new SimpleDateFormat(DATE_FORMAT).parse(cursor.getString(cursor.getColumnIndex("date"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            expense.setAmount(BigDecimal.valueOf(cursor.getFloat(cursor.getColumnIndex("amount"))));
            expense.setType(new ExpenseType(cursor.getString(cursor.getColumnIndex("type_name"))));

            expenses.add(expense);
        }

        return expenses;

    }

    public boolean insertExpense(Expense expense) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("type_name", expense.getType().getName());
        values.put("amount", expense.getAmount().toString());
        values.put("date", new SimpleDateFormat(DATE_FORMAT).format(expense.getDate()));

        long result = database.insert("expenses", null, values);
        database.close();
        return result > 0;

    }

    public boolean insertExpenseType(ExpenseType type) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", type.getName());

        long result = database.insert(EXPENSE_TYPES, null, values);
        database.close();
        return result > 0;

    }

    public List<String> getExpenseTypeNames() {

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select name from expense_types", null);

        List<String> typeNames = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            typeNames.add(name);
        }

        database.close();
        cursor.close();

        return typeNames;

    }

    public void resetExpenseTypes() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("expense_types", "", new String[]{});

        ContentValues unknown = new ContentValues();
        unknown.put("name", "unknown");
        database.insert("expense_types", null, unknown);

    }

    public void resetExpenses() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("expenses", "", new String[]{});
    }

    public void removeType(String selectedType) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("expenses", "type_name = '" + selectedType + "'", new String[]{});
        database.delete(EXPENSE_TYPES, "name = '" + selectedType + "'", new String[]{});
    }
}
