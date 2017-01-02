package bitmgroup.aeontanvir.com.pocketbudget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import bitmgroup.aeontanvir.com.pocketbudget.customadapter.ExpenseAdapter;
import bitmgroup.aeontanvir.com.pocketbudget.db.DbBudgetManager;
import bitmgroup.aeontanvir.com.pocketbudget.db.DbExpenseManager;
import bitmgroup.aeontanvir.com.pocketbudget.main.Budget;
import bitmgroup.aeontanvir.com.pocketbudget.main.Expense;

public class BudgetActivity extends AppCompatActivity {
    TextView txtBudgetName, txtBudgetSources, txtBudgetBalance;
    private int budgetId;
    private float budgetAmount;

    Budget budget;

    ArrayList<Expense> expenseList;
    ExpenseAdapter expenseAdapter;
    ListView listViewExpenseList;
    DbBudgetManager dbBudgetManager;
    DbExpenseManager dbExpenseManager;

    Expense expense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        // initialsition
        txtBudgetName = (TextView) findViewById(R.id.txtBudgetName);
        txtBudgetSources = (TextView) findViewById(R.id.txtBudgetSources);
        txtBudgetBalance = (TextView) findViewById(R.id.txtBudgetBalance);

        listViewExpenseList = (ListView) findViewById(R.id.listViewExpenseList);

        dbBudgetManager = new DbBudgetManager(BudgetActivity.this);
        dbExpenseManager = new DbExpenseManager(BudgetActivity.this);


        updateBudgetInfo();
        displayExpenseList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateBudgetInfo();
        displayExpenseList();
    }

    public void displayExpenseList(){
        expenseList = dbExpenseManager.getAllExpenseByBudgetId(budgetId);
        Collections.reverse(expenseList);
        expenseAdapter = new ExpenseAdapter(BudgetActivity.this, expenseList);
        listViewExpenseList.setAdapter(expenseAdapter);

    }


    private void updateBudgetInfo(){
        Bundle bundle = getIntent().getExtras();
        budgetId = bundle.getInt("id");
        budget = dbBudgetManager.getBudget(budgetId);

        txtBudgetName.setText(budget.getName());
        txtBudgetSources.setText(budget.getSources());
        budgetAmount = budget.getAmount();

        txtBudgetBalance.setText("Budget Balance : "+String.format("%.2f", getBalance(budgetId, budgetAmount)));
    }

    private float getBalance(int budgetId, float budgetAmount){
        float totalExpenseAmount = dbExpenseManager.getSumOfAmountByBudgetId(budgetId);
        float balance = budgetAmount - totalExpenseAmount;
        return balance;
    }

    public void openExpenseCreateDialogWindow(View view) {
        final Dialog dialog = new Dialog(BudgetActivity.this);
        dialog.setTitle("Create Expense");
        dialog.setContentView(R.layout.create_expense_dialog_layout);
        dialog.show();

        final Button btnExpenseDialogCancel = (Button) dialog.findViewById(R.id.btnExpenseDialogCancel);
        final Button btnExpenseCreate = (Button) dialog.findViewById(R.id.btnExpenseCreate);

        btnExpenseDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnExpenseCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtExpenseName = (EditText) dialog.findViewById(R.id.edtExpenseName);
                EditText edtExpenseDetails = (EditText) dialog.findViewById(R.id.edtExpenseDetails);
                EditText edtExpenseAmount = (EditText) dialog.findViewById(R.id.edtExpenseAmount);
                String name = edtExpenseName.getText().toString();
                String details = edtExpenseDetails.getText().toString();
                String amount = edtExpenseAmount.getText().toString();

                if(name.isEmpty()){
                    makeToastMessage("Enter a expense name");
                }else if(details.isEmpty()){
                    makeToastMessage("Enter expense details");
                }else if(amount.isEmpty()){
                    makeToastMessage("Enter amount");
                }else{
                    //budget = new Budget(name, sources, Float.parseFloat(amount));
                    expense = new Expense(budgetId, name, details, Float.parseFloat(amount));
                    boolean test = dbExpenseManager.addExpense(expense);
                    if(test){
                        updateBudgetInfo();
                        displayExpenseList();
                        dialog.cancel();
                        makeToastMessage("Expense created successfully");

                    }else{
                        makeToastMessage("Expense creation failed");
                    }
                }
            }
        });

    }

    public void updateBudgetListener(View view) {
        final Dialog dialog = new Dialog(BudgetActivity.this);
        dialog.setTitle("Update Budget Info");
        dialog.setContentView(R.layout.update_budget_dialog_layout);

        final EditText edtUpdateBudgetName = (EditText) dialog.findViewById(R.id.edtUpdateBudgetName);
        final EditText edtUpdateBudgetSource = (EditText) dialog.findViewById(R.id.edtUpdateBudgetSource);
        final EditText edtUpdateBudgetAmount = (EditText) dialog.findViewById(R.id.edtUpdateBudgetAmount);
        edtUpdateBudgetName.setText(budget.getName());
        edtUpdateBudgetSource.setText(budget.getSources());
        edtUpdateBudgetAmount.setText(budget.getAmount()+"");
        dialog.show();

        Button btnBudgetUpdate = (Button) dialog.findViewById(R.id.btnBudgetUpdate);
        Button btnBudgetUpdateDialogCancel = (Button) dialog.findViewById(R.id.btnBudgetUpdateDialogCancel);

        btnBudgetUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budget.setName(edtUpdateBudgetName.getText().toString());
                budget.setSources(edtUpdateBudgetSource.getText().toString());
                budget.setAmount(Float.parseFloat(edtUpdateBudgetAmount.getText().toString()));
                boolean updated = dbBudgetManager.updateBudget(budgetId, budget);
                if(updated){
                    onRestart();
                    dialog.cancel();
                    makeToastMessage("Updated Budget Successfully");

                }else{
                    makeToastMessage("Update Failed");
                }
            }
        });

        btnBudgetUpdateDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });



    }


    public void deleteBudgetListener(View view) {
        final Dialog dialog = new Dialog(BudgetActivity.this);
        dialog.setTitle("Delete Confirmation");
        dialog.setContentView(R.layout.delete_budget_confirm_layout);
        dialog.show();

        Button btnBudgetDeleteConfirmYes = (Button) dialog.findViewById(R.id.btnBudgetDeleteConfirmYes);
        Button btnBudgetDeleteConfirmNo = (Button) dialog.findViewById(R.id.btnBudgetDeleteConfirmNo);

        btnBudgetDeleteConfirmYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deletedBudget = dbBudgetManager.deleteBudget(budgetId);
                boolean deletedExpense = dbExpenseManager.deleteExpenseByBudgetId(budgetId);
                finish();
            }
        });

        btnBudgetDeleteConfirmNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }


    private void makeToastMessage(String message){
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }
}







































