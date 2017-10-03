package com.example.vladimirsostaric.walletlog.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vladimirsostaric.walletlog.model.ExpenseType;

import java.util.ArrayList;
import java.util.List;

public class DbUtils extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WALLET_LOG_DB";

    public DbUtils(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL("create table if not exists expense_types (id primary key, name)");

        ContentValues unknown = new ContentValues();
        unknown.put("name", "unknown");
        database.insert("expense_types", null, unknown);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertExpenseType(ExpenseType type) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", type.getName());

        long result = database.insert("expense_types", null, values);
        return result > 0;

    }

    public List<ExpenseType> getExpenseTypes() {

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from expense_types", null);

        List<ExpenseType> types = new ArrayList<>();

        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("NAME"));
            types.add(new ExpenseType(name));
        }

        cursor.close();

        return types;

    }

    public List<String> getExpenseTypeNames() {

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select name from expense_types", null);

        List<String> typeNames = new ArrayList<>();
        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            typeNames.add(name);
        }

        cursor.close();

        return typeNames;

    }
}
