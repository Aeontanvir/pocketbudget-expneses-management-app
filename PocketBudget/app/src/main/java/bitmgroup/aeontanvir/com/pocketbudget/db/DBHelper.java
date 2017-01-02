package bitmgroup.aeontanvir.com.pocketbudget.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aeon on 29 Oct, 2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bitm17_pocketbudget";


    public static final String TABLE_NAME_BUDGET = "budget_list";
    public static final String BGET_COL_ID = "bget_id";
    public static final String BGET_COL_NAME = "bget_name";
    public static final String BGET_COL_SOURCES = "bget_sources";
    public static final String BGET_COL_AMOUNT = "bget_amount";

    public static final String TABLE_NAME_EXPENSES = "expenses_list";
    public static final String EXPN_COL_ID = "expn_id";
    public static final String EXPN_COL_BGET_ID = "bget_id";
    public static final String EXPN_COL_NAME = "expn_name";
    public static final String EXPN_COL_DETAILS = "expn_details";
    public static final String EXPN_COL_AMOUNT = "expn_amount";

    public static final String CREATE_BUDGET_TABLE = "CREATE TABLE " + TABLE_NAME_BUDGET + "(" +
            BGET_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BGET_COL_NAME + " TEXT, " +
            BGET_COL_SOURCES + " TEXT, "+
            BGET_COL_AMOUNT + " FLOAT )";

    public static final String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_NAME_EXPENSES + "("+
            EXPN_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EXPN_COL_BGET_ID + " INTEGER," +
            EXPN_COL_NAME + " TEXT, "+
            EXPN_COL_DETAILS + " TEXT, "+
            EXPN_COL_AMOUNT + " FLOAT )";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BUDGET_TABLE);
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
