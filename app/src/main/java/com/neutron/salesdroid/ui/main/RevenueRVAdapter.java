package com.neutron.salesdroid.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.RevenueModel;

import java.util.List;

public class RevenueRVAdapter extends RecyclerView.Adapter<RevenueRVAdapter.RevenueVH> {
    private final List<RevenueModel> salesList;

    public RevenueRVAdapter(List<RevenueModel> salesList) {
        this.salesList = salesList;
    }

    @NonNull
    @Override
    public RevenueVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.revenue_list,parent,false);
        return new RevenueVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueVH holder, int position) {
        holder.revenueDate.setText(salesList.get(position).getDate());
        holder.revenue.setText(String.valueOf(salesList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    public class RevenueVH extends  RecyclerView.ViewHolder{
        private TextView revenueDate,revenue;
        public RevenueVH(@NonNull View itemView) {
            super(itemView);
            revenueDate = itemView.findViewById(R.id.revenue_date);
            revenue = itemView.findViewById(R.id.revenue);
        }
    }
}
