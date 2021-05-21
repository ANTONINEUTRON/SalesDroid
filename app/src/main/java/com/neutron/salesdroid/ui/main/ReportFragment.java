package com.neutron.salesdroid.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.Sales;

import java.util.List;

public class ReportFragment extends Fragment {

    private ReportViewModel mViewModel;
    private ImageButton menu;
    private static boolean isListEmpty = false;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        mViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        mViewModel.getAllSales().observe(getViewLifecycleOwner(), new Observer<List<Sales>>() {
            @Override
            public void onChanged(List<Sales> sales) {
                if(sales.size()<1){
                    isListEmpty = true;
                }else{
                    isListEmpty = false;
                }
            }
        });
        TextView txt = view.findViewById(R.id.no_report);
        if(isListEmpty){
            txt.setVisibility(View.VISIBLE);
        }else {
            txt.setVisibility(View.GONE);
            showRevenueFragment();
            menu = view.findViewById(R.id.select_report_type);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getContext(),menu);
                    popupMenu.inflate(R.menu.select_report_type_menu);
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.view_debtors:
                                    showDebtorFragment();
                                    return true;
                                case R.id.view_revenue:
                                    showRevenueFragment();
                                    return true;
                            }
                            return false;
                        }
                    });
                }
            });
        }
        return view;
    }

    private void showDebtorFragment() {
        ReportDebtorFragment rdf = ReportDebtorFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.report_shower, rdf)
                .commit();
    }

    private void showRevenueFragment() {
        ReportRevenueFragment rrf = ReportRevenueFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.report_shower, rrf)
                .commit();
    }

}