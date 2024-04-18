package com.example.betterandfasteremergency.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.dao.DAO;
import com.example.betterandfasteremergency.form.User;
import com.example.betterandfasteremergency.util.AppConstants;
import com.example.betterandfasteremergency.util.Constants;

public class AddBloodBank extends AppCompatActivity {
    Button b1;
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;
    ImageView i1;
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(AddBloodBank.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_bank);
        this.e1 = (EditText) findViewById(R.id.addbloodbankname);
        this.e2 = (EditText) findViewById(R.id.addbloodbankEmail);
        this.e3 = (EditText) findViewById(R.id.addbloodbankMobile);
        this.e4 = (EditText) findViewById(R.id.addbloodbankDescription);
        this.e5 = (EditText) findViewById(R.id.addbloodbankAddress);
        Button button = (Button) findViewById(R.id.registerBloodBank);
        this.b1 = button;
        this.i1=(ImageView) findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = AddBloodBank.this.e1.getText().toString();
                String email = AddBloodBank.this.e2.getText().toString();
                String mobile = AddBloodBank.this.e3.getText().toString();
                String description = AddBloodBank.this.e4.getText().toString();
                String address = AddBloodBank.this.e5.getText().toString();
                if (name == null || email == null || mobile == null || description == null || address == null) {
                    Toast.makeText(AddBloodBank.this.getApplicationContext(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                } else if (mobile.length() < 10 || mobile.length() > 12) {
                    Toast.makeText(AddBloodBank.this.getApplicationContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setMobile(mobile);
                    user.setDescription(description);
                    user.setAddress(address);
                    user.setType("bloodbank");
                    try {
                        new DAO().addObject(Constants.USER_DB, user, user.getMobile());
                        Toast.makeText(AddBloodBank.this.getApplicationContext(), "BloodBank Added Successfully", Toast.LENGTH_SHORT).show();
                        AddBloodBank.this.startActivity(new Intent(AddBloodBank.this.getApplicationContext(), AdminHome.class));
                        finish();
                    } catch (Exception ex) {
                        Toast.makeText(AddBloodBank.this.getApplicationContext(), "Register Error", Toast.LENGTH_SHORT).show();
                        Log.v("BloodBank Registration", ex.toString());
                        ex.printStackTrace();
                    }
                }
            }
        });
        this.i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /*Back press handling*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddBloodBank.this, AdminHome.class);
        startActivity(intent);
        finish();
    }
}