package com.neutron.salesdroid.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.DebtorsModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportDebtorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportDebtorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ReportViewModel reportViewModel;
    private RecyclerView recyclerView;

    public ReportDebtorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ReportDebtorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportDebtorFragment newInstance() {
        ReportDebtorFragment fragment = new ReportDebtorFragment();
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
        View view = inflater.inflate(R.layout.fragment_report_debtor, container, false);
        recyclerView = view.findViewById(R.id.debtor_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reportViewModel.getDebtorsList().observe(getViewLifecycleOwner(), new Observer<List<DebtorsModel>>() {
            @Override
            public void onChanged(List<DebtorsModel> debtorsModels) {
                recyclerView.setAdapter(new DebtorsRVAdapter(debtorsModels));
            }
        });
        return view;
    }
}