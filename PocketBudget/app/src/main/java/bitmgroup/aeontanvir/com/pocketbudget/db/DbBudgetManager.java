package bitmgroup.aeontanvir.com.pocketbudget.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import bitmgroup.aeontanvir.com.pocketbudget.main.Budget;

/**
 * Created by aeon on 29 Oct, 2016.
 */

public class DbBudgetManager {

    Context context;
    DBHelper helper;
    SQLiteDatabase database;

    public DbBudgetManager(Context context) {
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

    public boolean addBudget(Budget budget){
        this.openDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.BGET_COL_NAME, budget.getName());
        contentValues.put(DBHelper.BGET_COL_SOURCES, budget.getSources());
        contentValues.put(DBHelper.BGET_COL_AMOUNT, budget.getAmount());

        long inserted = database.insert(DBHelper.TABLE_NAME_BUDGET, null, contentValues);
        this.closeDatabase();
        if(inserted > 0){
            return true;
        }else{return false;}
    }

    public Budget getBudget(int id){
        this.openDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NAME_BUDGET,
                new String[]{DBHelper.BGET_COL_ID, DBHelper.BGET_COL_NAME, DBHelper.BGET_COL_SOURCES, DBHelper.BGET_COL_AMOUNT},
                DBHelper.BGET_COL_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();

        int budgetId = cursor.getInt(cursor.getColumnIndex(DBHelper.BGET_COL_ID));
        String name = cursor.getString(cursor.getColumnIndex(DBHelper.BGET_COL_NAME));
        String sources = cursor.getString(cursor.getColumnIndex(DBHelper.BGET_COL_SOURCES));
        float amount = cursor.getFloat(cursor.getColumnIndex(DBHelper.BGET_COL_AMOUNT));

        this.closeDatabase();

        Budget budget = new Budget(budgetId, name, sources, amount);

        return budget;
    }

    public ArrayList<Budget> getAllBudget(){
        this.openDatabase();
        ArrayList<Budget> budgetList = new ArrayList<>();
        Budget budget;
        Cursor cursor = database.query(DBHelper.TABLE_NAME_BUDGET,
                null, null, null, null, null, null);

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int budgetId = cursor.getInt(cursor.getColumnIndex(DBHelper.BGET_COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.BGET_COL_NAME));
                String sources = cursor.getString(cursor.getColumnIndex(DBHelper.BGET_COL_SOURCES));
                float amount = cursor.getFloat(cursor.getColumnIndex(DBHelper.BGET_COL_AMOUNT));
                budget = new Budget(budgetId, name, sources, amount);

                budgetList.add(budget);

                cursor.moveToNext();
            }
            this.closeDatabase();
        }

        return budgetList;
    }

    public boolean updateBudget(int id, Budget budget){
        this.openDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.BGET_COL_NAME, budget.getName());
        contentValues.put(DBHelper.BGET_COL_SOURCES, budget.getSources());
        contentValues.put(DBHelper.BGET_COL_AMOUNT, budget.getAmount());

        int updated = database.update(DBHelper.TABLE_NAME_BUDGET, contentValues, DBHelper.BGET_COL_ID + "= " + id, null);

        this.closeDatabase();

        if(updated > 0){
            return true;
        }else {return false;}

    }

    public boolean deleteBudget(int id){
        this.openDatabase();
        int deleted = database.delete(DBHelper.TABLE_NAME_BUDGET, DBHelper.BGET_COL_ID + "=" + id, null);
        this.closeDatabase();
        if(deleted > 0){
            return true;
        }else {return false;}
    }
}
