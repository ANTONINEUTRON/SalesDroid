package com.neutron.salesdroid.ui.addStock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.Stock;

public class AddStockActivity extends AppCompatActivity {
    private EditText stockName,unit,unitPrice,availableQuantity;
    private Button submit;
    private TextView errorMsg;
    private AddStockViewModel addStockViewModel;
    private String name, mUnit;
    private int availQty=0;
    private double mUnitPrice=0;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addStockViewModel = new ViewModelProvider(this).get(AddStockViewModel.class);

        stockName = findViewById(R.id.stock_names);
        unit = findViewById(R.id.unit);
        unitPrice = findViewById(R.id.unit_price);
        availableQuantity = findViewById(R.id.availQty);
        errorMsg = findViewById(R.id.stock_err_msg);
        submit = findViewById(R.id.record_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String errMsg = validateValues();
                if(errMsg.isEmpty()){
                    Stock stock = new Stock(name,availQty,mUnit,mUnitPrice);
                    addStockViewModel.createNewStock(stock);
                    resetSelection();
                    AddStockActivity.this.finish();
                }else{
                    errorMsg.setText(errMsg);
                    errorMsg.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void resetSelection() {
        stockName.setText("");
        unit.setText("");
        unitPrice.setText("");
        availableQuantity.setText("");
    }

    private String validateValues() {
        //assign values
        name = stockName.getText().toString();
        mUnit = unit.getText().toString();
        String sAvQty = availableQuantity.getText().toString();
        String sUP = unitPrice.getText().toString();

        if(!sAvQty.isEmpty())
            availQty = Integer.parseInt(sAvQty);
        if(!sUP.isEmpty())
            mUnitPrice = Double.parseDouble(sUP);
        //perform checks
        if(name.isEmpty()){
            return "you can't have an empty stock name";
        }
        if(mUnit.isEmpty()){
            return "you can't have an empty unit for measuring your stock";
        }
        if(mUnitPrice == 0){
            return "you should specify price for each unit of "+name+" sold";
        }
        return "";
    }
}