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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.Stock;

import java.util.List;

public class StocksFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StocksFragment() {
    }

    public static StocksFragment newInstance() {
        StocksFragment fragment = new StocksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_stocks_list, container, false);
        StocksViewModel stocksViewModel = new ViewModelProvider(this).get(StocksViewModel.class);
        //observe data from viewmodel
        stocksViewModel.getAllStocks().observe(getViewLifecycleOwner(), new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                TextView tv = view.findViewById(R.id.no_stock);
                if(stocks.size()<1){
                    tv.setVisibility(View.VISIBLE);
                }else{
                    setupRecyclerView(view, stocks);
                    tv.setVisibility(View.GONE);
                }
            }
        });
        // Set the adapter
        return view;
    }

    private void setupRecyclerView(View view, List<Stock> stocks){
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(new StocksRVAdapter(stocks));
    }
}