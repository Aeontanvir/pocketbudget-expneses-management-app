package bitmgroup.aeontanvir.com.pocketbudget.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import bitmgroup.aeontanvir.com.pocketbudget.R;
import bitmgroup.aeontanvir.com.pocketbudget.main.Expense;

/**
 * Created by aeon on 03 Nov, 2016.
 */

public class ExpenseAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Expense> expenseList;



    public ExpenseAdapter(Context context, ArrayList<Expense> expenseList) {
        super(context, R.layout.expense_row_layout, expenseList);
        this.context = context;
        this.expenseList = expenseList;
    }


    private static class ViewHolder{
        TextView txtExpenseRowName;
        TextView txtExpenseRowDetails;
        TextView txtExpenseRowAmount;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpenseAdapter.ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expense_row_layout, parent, false);
            viewHolder = new ExpenseAdapter.ViewHolder();

            viewHolder.txtExpenseRowName = (TextView)convertView.findViewById(R.id.txtExpenseRowName);
            viewHolder.txtExpenseRowDetails = (TextView)convertView.findViewById(R.id.txtExpenseRowDetails);
            viewHolder.txtExpenseRowAmount = (TextView)convertView.findViewById(R.id.txtExpenseRowAmount);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.txtExpenseRowName.setText(expenseList.get(position).getName());
        viewHolder.txtExpenseRowDetails.setText(expenseList.get(position).getDetails());
        viewHolder.txtExpenseRowAmount.setText(""+ expenseList.get(position).getAmount());

        return convertView;
    }
}
