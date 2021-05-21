package com.neutron.salesdroid.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.Transaction;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class SalesFragment extends Fragment {

    private SalesViewModel salesViewModel;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SalesFragment() {
    }

    public static SalesFragment newInstance() {
        SalesFragment fragment = new SalesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salesViewModel = new ViewModelProvider(this).get(SalesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sales_list, container, false);
        //observe the Livedata which contains list of all sales
        salesViewModel.getAllSales().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> sales) {
                TextView tv = view.findViewById(R.id.no_sales);
                if(sales.size()<1){
                    tv.setVisibility(View.VISIBLE);
                }else {
                    setupRecyclerView(view, sales);
                    tv.setVisibility(View.GONE);
                }
            }
        });
        // Set the adapter
        return view;
    }

    private void setupRecyclerView(View view, List<Transaction> sales){
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sales_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new SalesRVAdapter(sales));
    }
}