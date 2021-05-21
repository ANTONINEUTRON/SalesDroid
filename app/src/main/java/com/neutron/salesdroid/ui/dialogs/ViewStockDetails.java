package com.neutron.salesdroid.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.Stock;
import com.neutron.salesdroid.ui.addStock.AddStockViewModel;

public class ViewStockDetails extends DialogFragment {
    private Stock stock;
    private TextView stockName,unit,pricePerUnit,availQty;
    private ImageButton addQty, addNewQty;
    private EditText newQty;
    private LinearLayout layout;
    private AddStockViewModel addStockViewModel;

    public ViewStockDetails(Stock stock) {
        this.stock = stock;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
         View view = LayoutInflater.from(getContext()).inflate(R.layout.stock_details_layout, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Stock Details")
                .setView(view)
                .setPositiveButton("Done",null);
        this.setCancelable(false);

        assignValues(view);

        addQty = view.findViewById(R.id.add_quantity);
        layout = view.findViewById(R.id.increment_qty);

        addQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addQty.getVisibility()==View.VISIBLE){
                    addQty.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                }
            }
        });

        addStockViewModel = new ViewModelProvider(getActivity()).get(AddStockViewModel.class);

        addNewQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newQuantity = newQty.getText().toString();
                int newQty = stock.getAvailableQuantity() + Integer.parseInt(newQuantity);
                stock.setAvailableQuantity(newQty);
                availQty.setText(""+newQty);
                //insert into db
                addStockViewModel.createNewStock(stock);

                addQty.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
            }
        });

        return builder.create();
    }

    private void assignValues(View view) {
        stockName = view.findViewById(R.id.stock_name);
        stockName.setText(makeText("Stock Name: ",stock.getName()));

        unit = view.findViewById(R.id.stock_unit);
        unit.setText(makeText("Unit: ",stock.getUnit()));

        pricePerUnit= view.findViewById(R.id.price_per_unit);
        pricePerUnit.setText(makeText("Price per Unit: ",String.valueOf(stock.getUnitPrice())));

        availQty = view.findViewById(R.id.availQty);
        availQty.setText(makeText("Available Quantity: ",String.valueOf(stock.getAvailableQuantity())));

        newQty = view.findViewById(R.id.new_qty);
        addNewQty = view.findViewById(R.id.increment_a_qty);
    }

    private SpannableString makeText(String boldText, String normalText){
        SpannableString str = new SpannableString(boldText+normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD),0,boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}