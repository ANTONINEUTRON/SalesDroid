package com.neutron.salesdroid.ui.addProfile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.neutron.salesdroid.R;

public class BusinessProfileActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 10;
    public static final String LOGO_KEY = "LogoUri";
    public static final String BIZ_KEY = "Biz_Key";
    private EditText businessName;
    private ImageView businessLogo;
    private Toolbar toolbar;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        businessName = findViewById(R.id.biz_name);
        businessLogo = findViewById(R.id.biz_logo);
        preferences = this.getSharedPreferences("Business Details",MODE_PRIVATE);

        //set logo if it exists
        String uriTxt = preferences.getString(LOGO_KEY,"");
        if(!uriTxt.isEmpty()){
            loadLogo(Uri.parse(uriTxt));
        }

        //set biz name if it exists
        String bizName = preferences.getString(BIZ_KEY,"");
        if(!bizName.isEmpty()){
            businessName.setText(bizName);
        }

        businessLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open filechooser
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode == RESULT_OK){
            Uri photoUri = data.getData();
            loadLogo(photoUri);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString(LOGO_KEY,photoUri.toString());
            edit.apply();
        }
    }

    @Override
    public void onBackPressed() {
        String bizName = businessName.getText().toString();
        if(!bizName.isEmpty()){
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString(BIZ_KEY,bizName);
            edit.apply();
        }
        super.onBackPressed();
    }

    private void loadLogo(Uri photoUri) {
        Glide.with(this)
                .load(photoUri)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(businessLogo);
    }
}