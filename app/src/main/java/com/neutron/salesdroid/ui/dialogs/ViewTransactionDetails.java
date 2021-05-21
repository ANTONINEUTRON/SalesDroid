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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.Sales;
import com.neutron.salesdroid.data.model.Transaction;
import com.neutron.salesdroid.ui.addSales.AddSalesViewModel;

public class ViewTransactionDetails extends DialogFragment {
    private Transaction transaction;
    private TextView date,stockName,quantity,price,discount,customerName,customerEmail,customerPhn,customerAddress,paymentStatus;
    private Switch newPaymentStatus;
    private ImageButton editPS;
    private LinearLayout editPSLayout;

    public ViewTransactionDetails(Transaction transaction) {
        this.transaction = transaction;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.transaction_details_layout, null, false);

        assignValues(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Transaction Details")
                .setView(view)
                .setPositiveButton("Done",null);
        this.setCancelable(false);

        final AddSalesViewModel addSalesViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(AddSalesViewModel.class);
        editPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transaction.getPaymentStatus().equals("Paid")){
                    newPaymentStatus.setChecked(true);
                }else{
                    newPaymentStatus.setChecked(false);
                }
                editPSLayout.setVisibility(View.VISIBLE);
                editPS.setVisibility(View.GONE);
            }
        });

        newPaymentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPS;
                if(newPaymentStatus.isChecked()){
                    newPS = newPaymentStatus.getTextOn().toString();
                }else{
                    newPS = newPaymentStatus.getTextOff().toString();
                }
                transaction.setPaymentStatus(newPS);
                addSalesViewModel.setTransactionDetails(transaction);
                Sales sales = addSalesViewModel.prepareValues();
                Toast.makeText(getContext(),"id "+sales.getCustomerId()+" ps"+sales.getPaymentStatus()+" date"+sales.getDate(),Toast.LENGTH_LONG).show();
                addSalesViewModel.updatePS();
                editPSLayout.setVisibility(View.GONE);
                editPS.setVisibility(View.VISIBLE);
                paymentStatus.setText(makeText("Payment Status: ",transaction.getPaymentStatus()));
            }
        });

        return builder.create();
    }

    private void assignValues(View view) {
        date = view.findViewById(R.id.trans_date);
        date.setText(transaction.getDate());

        stockName = view.findViewById(R.id.stock_name);
        stockName.setText(makeText("Stock: ",transaction.getStockName()));

        quantity = view.findViewById(R.id.trans_quantity);
        quantity.setText(makeText("Quantity: ",String.valueOf(transaction.getQuantity())+" "+transaction.getUnit()));

        price = view.findViewById(R.id.price);
        price.setText(makeText("Price: ",String.valueOf(transaction.getPrice())));

        discount = view.findViewById(R.id.discount);
        discount.setText(makeText("Discount: ",String.valueOf(transaction.getDiscount())));

        paymentStatus = view.findViewById(R.id.payment_status);
        paymentStatus.setText(makeText("Payment Status: ",transaction.getPaymentStatus()));

        customerName = view.findViewById(R.id.customer_name);
        customerName.setText(makeText("Customer Name: ",transaction.getCustomerName()));

        customerAddress = view.findViewById(R.id.customer_address);
        customerAddress.setText(makeText("Customer Address: ",transaction.getCustomerAddress()));

        customerEmail = view.findViewById(R.id.customer_email);
        customerEmail.setText(makeText("Customer Email: ",transaction.getCustomerEmail()));

        customerPhn = view.findViewById(R.id.customer_phn);
        customerPhn.setText(makeText("Customer Phone Number: ",transaction.getCustomerPhnNumber()));


        editPSLayout = view.findViewById(R.id.edit_ps_layout);
        editPS = view.findViewById(R.id.edit_payment_status);
        newPaymentStatus = view.findViewById(R.id.new_ps);

    }

    private SpannableString makeText(String boldText, String normalText){
        SpannableString str = new SpannableString(boldText+normalText);
        str.setSpan(new StyleSpan(Typeface.BOLD),0,boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}
