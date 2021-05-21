package com.neutron.salesdroid.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.RevenueModel;
import com.neutron.salesdroid.data.model.Sales;
import com.neutron.salesdroid.utils.RevenueProcessor;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportRevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportRevenueFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ReportViewModel reportViewModel;
    private RecyclerView recyclerView;
    private Button dailyBtn, monthlyBtn;
    private RevenueProcessor.Key KEY = RevenueProcessor.Key.DAY;

    public ReportRevenueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ReportRevenueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportRevenueFragment newInstance(){
        ReportRevenueFragment fragment = new ReportRevenueFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_revenue, container, false);

        assignValues(view);

        dailyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KEY = RevenueProcessor.Key.DAY;
                callVMObserver(KEY);
            }
        });

        monthlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KEY = RevenueProcessor.Key.MONTH;
                callVMObserver(KEY);
            }
        });

        callVMObserver(KEY);
        return view;
    }

    private void callVMObserver(final RevenueProcessor.Key key) {
        reportViewModel.getAllSales().observe(getViewLifecycleOwner(), new Observer<List<Sales>>() {
            @Override
            public void onChanged(List<Sales> sales) {
                RevenueProcessor processor = new RevenueProcessor(sales);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                List<RevenueModel> revenue = processor.getRevenue(key);
                recyclerView.setAdapter(new RevenueRVAdapter(revenue));
            }
        });
    }

    private void assignValues(View view) {
        recyclerView = view.findViewById(R.id.report_list);
        dailyBtn = view.findViewById(R.id.daily_btn);
        monthlyBtn = view.findViewById(R.id.monthly_btn);
    }
}