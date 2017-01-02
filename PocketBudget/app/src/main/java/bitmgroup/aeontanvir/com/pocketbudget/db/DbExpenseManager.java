package bitmgroup.aeontanvir.com.pocketbudget.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import bitmgroup.aeontanvir.com.pocketbudget.main.Expense;

/**
 * Created by Mobile App Develop on 29-10-16.
 */
public class DbExpenseManager {
    Context context;
    DBHelper helper;
    SQLiteDatabase database;

    public DbExpenseManager(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    private void openDatabase(){
        database = helper.getWritableDatabase();
    }
    private void closeDatabase(){
        helper.close();
        database.close();
    }

    public boolean addExpense(Expense expense){
        this.openDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.EXPN_COL_BGET_ID, expense.getBudgetId());
        contentValues.put(DBHelper.EXPN_COL_NAME, expense.getName());
        contentValues.put(DBHelper.EXPN_COL_DETAILS, expense.getDetails());
        contentValues.put(DBHelper.EXPN_COL_AMOUNT, expense.getAmount());

        long inserted = database.insert(DBHelper.TABLE_NAME_EXPENSES, null, contentValues);
        this.closeDatabase();
        if(inserted > 0){
            return true;
        }else{ return false;}
    }

    public Expense getExpense(int id){
        this.openDatabase();
        Expense expense;

        Cursor cursor = database.query(DBHelper.TABLE_NAME_EXPENSES,
                new String[]{DBHelper.EXPN_COL_ID, DBHelper.EXPN_COL_BGET_ID, DBHelper.EXPN_COL_NAME, DBHelper.EXPN_COL_DETAILS, DBHelper.EXPN_COL_AMOUNT},
                DBHelper.EXPN_COL_ID + "=" + id, null, null, null, null);
        cursor.moveToFirst();

        int expenseId = cursor.getInt(cursor.getColumnIndex(DBHelper.EXPN_COL_ID));
        int budgetId = cursor.getInt(cursor.getColumnIndex(DBHelper.EXPN_COL_BGET_ID));
        String name = cursor.getString(cursor.getColumnIndex(DBHelper.EXPN_COL_NAME));
        String details = cursor.getString(cursor.getColumnIndex(DBHelper.EXPN_COL_DETAILS));
        float amount = cursor.getFloat(cursor.getColumnIndex(DBHelper.EXPN_COL_AMOUNT));

        expense = new Expense(expenseId, budgetId, name, details, amount);
        this.closeDatabase();
        return expense;
    }

    public ArrayList<Expense> getAllExpenseByBudgetId(int budgetId){
        this.openDatabase();
        ArrayList<Expense> expenseList = new ArrayList<>();
        Expense expense;

        Cursor cursor = database.query(DBHelper.TABLE_NAME_EXPENSES,
                new String[]{DBHelper.EXPN_COL_ID, DBHelper.EXPN_COL_BGET_ID, DBHelper.EXPN_COL_NAME, DBHelper.EXPN_COL_DETAILS, DBHelper.EXPN_COL_AMOUNT},
                DBHelper.EXPN_COL_BGET_ID + "=" + budgetId, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int expenseId = cursor.getInt(cursor.getColumnIndex(DBHelper.EXPN_COL_ID));
                int budgetId_Temp = cursor.getInt(cursor.getColumnIndex(DBHelper.EXPN_COL_BGET_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.EXPN_COL_NAME));
                String details = cursor.getString(cursor.getColumnIndex(DBHelper.EXPN_COL_DETAILS));
                float amount = cursor.getFloat(cursor.getColumnIndex(DBHelper.EXPN_COL_AMOUNT));

                expense = new Expense(expenseId, budgetId_Temp, name, details, amount);
                expenseList.add(expense);
                cursor.moveToNext();
            }
            this.closeDatabase();
        }

        return expenseList;
    }

    public ArrayList<Expense> getAllExpense(int id){
        this.openDatabase();
        ArrayList<Expense> expenseList = new ArrayList<>();
        Expense expense;

        Cursor cursor = database.query(DBHelper.TABLE_NAME_EXPENSES,
                new String[]{DBHelper.EXPN_COL_ID, DBHelper.EXPN_COL_BGET_ID, DBHelper.EXPN_COL_NAME, DBHelper.EXPN_COL_DETAILS, DBHelper.EXPN_COL_AMOUNT},
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int expenseId = cursor.getInt(cursor.getColumnIndex(DBHelper.EXPN_COL_ID));
                int budgetId = cursor.getInt(cursor.getColumnIndex(DBHelper.EXPN_COL_BGET_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.EXPN_COL_NAME));
                String details = cursor.getString(cursor.getColumnIndex(DBHelper.EXPN_COL_DETAILS));
                float amount = cursor.getFloat(cursor.getColumnIndex(DBHelper.EXPN_COL_AMOUNT));

                expense = new Expense(expenseId, budgetId, name, details, amount);
                expenseList.add(expense);
                cursor.moveToNext();
            }
            this.closeDatabase();
        }

        return expenseList;
    }

    public boolean updateExpense(int id, Expense expense){
        this.openDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.EXPN_COL_NAME, expense.getName());
        contentValues.put(DBHelper.EXPN_COL_DETAILS, expense.getDetails());
        contentValues.put(DBHelper.EXPN_COL_AMOUNT, expense.getAmount());

        int updated = database.update(DBHelper.TABLE_NAME_EXPENSES,contentValues, DBHelper.EXPN_COL_ID + "=" + id, null);
        this.closeDatabase();
        if(updated > 0){
            return true;
        }else {return false;}
    }

    public boolean deleteExpense(int id){
        this.openDatabase();
        int deleted = database.delete(DBHelper.TABLE_NAME_EXPENSES, DBHelper.EXPN_COL_ID + "=" +id, null);
        this.closeDatabase();
        if(deleted > 0){
            return true;
        }else{return false;}
    }

    public boolean deleteExpenseByBudgetId(int budgetId){
        this.openDatabase();
        int deleted = database.delete(DBHelper.TABLE_NAME_EXPENSES, DBHelper.EXPN_COL_BGET_ID + "=" +budgetId, null);
        this.closeDatabase();
        if(deleted > 0){
            return true;
        }else{return false;}
    }

    public float getSumOfAmountByBudgetId(int budgetId){
        this.openDatabase();
        float total = 0;

        Cursor cursor = database.rawQuery("SELECT IFNULL(SUM(" + DBHelper.EXPN_COL_AMOUNT + "), 0)  as Total  FROM " + DBHelper.TABLE_NAME_EXPENSES + " WHERE "+ DBHelper.EXPN_COL_BGET_ID +"=" + budgetId, null);
        if (cursor.moveToFirst()) {
            total = cursor.getFloat(cursor.getColumnIndex("Total"));
        }

        this.closeDatabase();
        return total;
    }


}
