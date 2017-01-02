package bitmgroup.aeontanvir.com.pocketbudget.customadapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bitmgroup.aeontanvir.com.pocketbudget.BudgetActivity;
import bitmgroup.aeontanvir.com.pocketbudget.R;
import bitmgroup.aeontanvir.com.pocketbudget.db.DbBudgetManager;
import bitmgroup.aeontanvir.com.pocketbudget.main.Budget;

/**
 * Created by aeon on 30 Oct, 2016.
 */

public class BudgetAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Budget> budgetList;

    DbBudgetManager dbBudgetManager;


    public BudgetAdapter(Context context, ArrayList<Budget> budgetList) {
        super(context, R.layout.budget_row_layout,budgetList);
        this.context = context;
        this.budgetList = budgetList;
    }
    private static class ViewHolder{
        TextView txtBudgetRowName;
        Button btnBudgetRowView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        BudgetAdapter.ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.budget_row_layout, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.txtBudgetRowName = (TextView)convertView.findViewById(R.id.txtBudgetRowName);
            viewHolder.btnBudgetRowView = (Button)convertView.findViewById(R.id.btnBudgetRowView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtBudgetRowName.setText(budgetList.get(position).getName());


        viewHolder.btnBudgetRowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBudgetIntent(budgetList.get(position));
            }
        });

        return convertView;
    }

    private void goBudgetIntent(Budget budget){
        Intent intent = new Intent(getContext(), BudgetActivity.class);
        intent.putExtra("id", budget.getId());
        getContext().startActivity(intent);
    }




}
