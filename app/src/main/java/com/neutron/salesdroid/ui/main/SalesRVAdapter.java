package com.neutron.salesdroid.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.Transaction;
import com.neutron.salesdroid.ui.dialogs.ViewTransactionDetails;
import com.neutron.salesdroid.utils.DeleteTransaction;

import java.util.List;

public class SalesRVAdapter extends RecyclerView.Adapter<SalesRVAdapter.ViewHolder> {

    private final List<Transaction> mValues;
    private Context context;

    public SalesRVAdapter(List<Transaction> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sales, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Transaction transDetails = mValues.get(position);
        holder.date.setText(transDetails.getDate());
        holder.customerName.setText(transDetails.getCustomerName());
        holder.transactionDetail.setText(transDetails.getQuantity()+" "+ transDetails.getUnit()
                +" of "+ transDetails.getStockName());

        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show Transaction details in a dialog
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                ViewTransactionDetails dialog = new ViewTransactionDetails(transDetails);
                dialog.show(activity.getSupportFragmentManager(),"transaction Details");
            }
        });
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.menu);
                popupMenu.inflate(R.menu.rv_popup_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.delete:
                                DeleteTransaction deleteTransaction = new DeleteTransaction(context);
                                deleteTransaction.delete(transDetails);
                                holder.itemView.setVisibility(View.GONE);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date, transactionDetail,customerName;
        ImageButton menu;

        public ViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            transactionDetail = (TextView) view.findViewById(R.id.trans_details);
            customerName = (TextView) view.findViewById(R.id.customer_name);
            menu= view.findViewById(R.id.menu_bt);
        }
    }
}