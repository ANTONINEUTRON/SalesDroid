package com.neutron.salesdroid.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.DebtorsModel;

import java.util.List;

public class DebtorsRVAdapter extends RecyclerView.Adapter<DebtorsRVAdapter.DebtorsVH> {
    List<DebtorsModel> debtorsModelList;
    public DebtorsRVAdapter(List<DebtorsModel> debtorsModels) {
        this.debtorsModelList = debtorsModels;
    }

    @NonNull
    @Override
    public DebtorsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debtors_list,parent,false);
        return new DebtorsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtorsVH holder, int position) {
        holder.debtorName.setText(debtorsModelList.get(position).getName());
        holder.amtOwed.setText(""+debtorsModelList.get(position).getAmountOwed());
    }

    @Override
    public int getItemCount() {
        return debtorsModelList.size();
    }

    public class DebtorsVH extends RecyclerView.ViewHolder {
        public TextView debtorName, amtOwed;
        public DebtorsVH(@NonNull View itemView) {
            super(itemView);
            debtorName = itemView.findViewById(R.id.debtor_name);
            amtOwed = itemView.findViewById(R.id.debtor_amt_owed);
        }
    }
}
