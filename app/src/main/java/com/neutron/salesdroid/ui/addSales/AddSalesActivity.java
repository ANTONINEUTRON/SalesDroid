package com.neutron.salesdroid.ui.addSales;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.Customer;
import com.neutron.salesdroid.data.model.Stock;
import com.neutron.salesdroid.data.model.Transaction;
import com.neutron.salesdroid.ui.addStock.AddStockActivity;

import java.util.ArrayList;
import java.util.List;

public class AddSalesActivity extends AppCompatActivity {
    private EditText quantity,price,discount, customerAddress, customerPhnNumber, customerEmail;
    private AutoCompleteTextView customerName;
    private Spinner stockName,units;
    private Switch paymentStatus;
    private Button record;
    private TextView errorMsg;
    private AddSalesViewModel addSalesViewModel;
    private double mPrice=0;
    private int mQuantity=0;
    private double mDiscount=0;
    private String mStockname, mPaymentStatus, mUnit, mCustomerName, mCustomerPhnNumber, mCustomerEmail, mCustomerAddress;

    private ArrayList<String> spinnerUnits, spinnerStockname, customerNames=new ArrayList<>();
    private List<Customer> customersDetails;
    private Toolbar toolbar;
    private double pricePerUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addSalesViewModel = new ViewModelProvider(this).get(AddSalesViewModel.class);

        assignValues();

        setSupportActionBar(toolbar);

        addSalesViewModel.getAllCustomerDetails().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                for(Customer customer : customers){
                    customerNames.add(customer.getCustomerName());
                }
                customersDetails = customers;
            }
        });

        customerName.setAdapter(arrayAdapter(customerNames));
        customerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer customer = getCustomersDetails(customerName.getText().toString(), customersDetails);
                customerAddress.setText(customer.getAddress());
                customerEmail.setText(customer.getEmail());
                customerPhnNumber.setText(customer.getPhoneNumber());
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Assign values
                //mUnit and mStockName already assigned
                if(!price.getText().toString().isEmpty())
                    mPrice = Double.parseDouble(price.getText().toString());
                if(!quantity.getText().toString().isEmpty())
                    mQuantity = Integer.parseInt(quantity.getText().toString());
                if(!discount.getText().toString().isEmpty())
                    mDiscount = Double.parseDouble(discount.getText().toString());
                if(paymentStatus.isChecked()){
                    mPaymentStatus = paymentStatus.getTextOn().toString();
                }else{
                    mPaymentStatus = paymentStatus.getTextOff().toString();
                }
                mCustomerName = customerName.getText().toString();
                mCustomerPhnNumber = customerPhnNumber.getText().toString();
                mCustomerEmail = customerEmail.getText().toString();
                mCustomerAddress = customerAddress.getText().toString();
                //set default values
                setDefaultValues();
                //run validation
                String res = validateValues();
                if(res != null){
                    //show error message
                    errorMsg.setText(res);
                    errorMsg.setVisibility(View.VISIBLE);
                }else {
                    saveTransaction();
                }
            }
        });

        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int value;
                if(charSequence.toString().isEmpty()){
                    value = 0;
                }else{
                    value = Integer.parseInt(charSequence.toString());
                }
                price.setText(String.valueOf(value*pricePerUnit));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onResume() {
        addSalesViewModel.getStockDetails().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(final List<Stock> stockDetails) {
                spinnerUnits  = new ArrayList<String>();
                spinnerStockname = new ArrayList<String>();
                for(Stock s : stockDetails){
                    spinnerUnits.add(s.getUnit());
                    spinnerStockname.add(s.getName());
                }
                final String CNS = "Create New Stock";
                spinnerStockname.add(CNS);

                units.setAdapter(arrayAdapter(spinnerUnits));
                units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mUnit = units.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                stockName.setAdapter(arrayAdapter(spinnerStockname));
                stockName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mStockname = stockName.getSelectedItem().toString();
                        if(mStockname.equals(CNS)){
                            Intent intent = new Intent(AddSalesActivity.this, AddStockActivity.class);
                            startActivity(intent);
                        }else {
                            //set unit according to the stock name
                            units.setSelection(i,true);
                            //set price per unit to be use in AddOnTextChangedListener
                            for(int j = 0;j<stockDetails.size();j++){
                                if(((String)units.getSelectedItem()).equals(stockDetails.get(j).getUnit())){
                                    pricePerUnit = stockDetails.get(j).getUnitPrice();
                                }
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
        super.onResume();
    }

    private void assignValues() {
        toolbar = findViewById(R.id.toolbar);
        quantity =  findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        discount = findViewById(R.id.discount);
        paymentStatus = findViewById(R.id.payment_status);
        units = findViewById(R.id.units);
        stockName = findViewById(R.id.stock_names);
        customerAddress = findViewById(R.id.address);
        customerPhnNumber = findViewById(R.id.phone_num);
        customerEmail = findViewById(R.id.email);
        customerName = findViewById(R.id.customer_name);
        errorMsg = findViewById(R.id.err_msg);
        record = findViewById(R.id.record);
    }

    private void saveTransaction() {
        Transaction transactionDetails = new Transaction();
        transactionDetails.setStockName(mStockname);
        transactionDetails.setPrice(mPrice);
        transactionDetails.setQuantity(mQuantity);
        transactionDetails.setUnit(mUnit);
        transactionDetails.setDiscount(mDiscount);
        transactionDetails.setPaymentStatus(mPaymentStatus);
        transactionDetails.setCustomerName(mCustomerName);
        transactionDetails.setCustomerPhnNumber(mCustomerPhnNumber);
        transactionDetails.setCustomerEmail(mCustomerEmail);
        transactionDetails.setCustomerAddress(mCustomerAddress);

        addSalesViewModel.setTransactionDetails(transactionDetails);
        addSalesViewModel.saveValues();
        resetSelection();
    }

    private void resetSelection() {
        quantity.setText("");
        price.setText("");
        discount.setText("");
        customerName.setText("");
        customerAddress.setText("");
        customerPhnNumber.setText("");
        customerEmail.setText("");
    }

    private Customer getCustomersDetails(String name, List<Customer> customersDetails) {
        for(Customer customer : customersDetails){
            if(customer.getCustomerName().equals(name)){
                return customer;
            }
        }
        return new Customer("","","","");
    }

    private ArrayAdapter<String> arrayAdapter(ArrayList<String> list) {
        return new ArrayAdapter<String>(AddSalesActivity.this, R.layout.support_simple_spinner_dropdown_item, list);
    }

    private String validateValues(){
        StringBuilder errMsg = new StringBuilder();
        if(mQuantity < 1){
            return "Quantity sold should be more than 1";
        }
        return null;
    }

    private void setDefaultValues(){
        //default values if customer details is empty
        if(mCustomerName.isEmpty()){
            mCustomerName = "Anonymous";
        }
        if(mCustomerAddress.isEmpty()){
            mCustomerAddress = "Unknown";
        }
        if(mCustomerEmail .isEmpty()){
            mCustomerEmail = "unknown";
        }
        if(mCustomerPhnNumber.isEmpty()){
            mCustomerPhnNumber = "unknown";
        }
    }
}