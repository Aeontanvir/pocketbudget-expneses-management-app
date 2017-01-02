package bitmgroup.aeontanvir.com.pocketbudget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import bitmgroup.aeontanvir.com.pocketbudget.customadapter.BudgetAdapter;
import bitmgroup.aeontanvir.com.pocketbudget.db.DbBudgetManager;
import bitmgroup.aeontanvir.com.pocketbudget.main.Budget;


public class MainActivity extends AppCompatActivity {
    ListView listViewBudgetList;

    ArrayList<Budget> budgetList;
    Budget budget;
    BudgetAdapter budgetAdapter;
    DbBudgetManager dbBudgetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //button

        //my budget list view
        listViewBudgetList = (ListView) findViewById(R.id.listViewBudgetList);
        //database and manager inisalize
        dbBudgetManager = new DbBudgetManager(MainActivity.this);
        budgetList = new ArrayList<>();
        displayBudgetList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        displayBudgetList();
    }

    public void displayBudgetList(){
        budgetList = dbBudgetManager.getAllBudget();
        Collections.reverse(budgetList);
        budgetAdapter = new BudgetAdapter(MainActivity.this, budgetList);
        listViewBudgetList.setAdapter(budgetAdapter);

    }


    public void openBudgetCreateDialogWindow(View view) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Create Budget");
        dialog.setContentView(R.layout.create_budget_dialog_layout);
        dialog.show();

        final Button btnBudgetDialogCancel = (Button) dialog.findViewById(R.id.btnBudgetDialogCancel);
        final Button btnBudgetCreate = (Button) dialog.findViewById(R.id.btnBudgetCreate);



        btnBudgetDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnBudgetCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtBudgetName = (EditText) dialog.findViewById(R.id.edtBudgetName);
                EditText edtBudgetSources = (EditText) dialog.findViewById(R.id.edtBudgetSource);
                EditText edtBudgetAmount = (EditText) dialog.findViewById(R.id.edtBudgetAmount);

                String name = edtBudgetName.getText().toString();
                String sources = edtBudgetSources.getText().toString();
                String amount = edtBudgetAmount.getText().toString();

                if(name.isEmpty()){
                    makeToastMessage("Enter a budget name");
                }else if(sources.isEmpty()){
                    makeToastMessage("Enter budget sources");
                }else if(amount.isEmpty()){
                    makeToastMessage("Enter amount");
                }else{
                    budget = new Budget(name, sources, Float.parseFloat(amount));
                    boolean test = dbBudgetManager.addBudget(budget);
                    if(test){
                        displayBudgetList();
                        dialog.cancel();
                        makeToastMessage("Budget created successfully");

                    }else{
                        makeToastMessage("Budget creation failed");
                    }
                }
            }
        });
    }

    private void makeToastMessage(String message){
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }
}
