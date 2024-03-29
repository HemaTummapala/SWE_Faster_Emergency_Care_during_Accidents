package com.example.betterandfasteremergency.view;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.betterandfasteremergency.App;
import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.dao.DAO;
import com.example.betterandfasteremergency.form.User;
import com.example.betterandfasteremergency.util.AppConstants;
import com.example.betterandfasteremergency.util.Constants;
public class AddAmbulance extends AppCompatActivity {
    Button b1;
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(AddAmbulance.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ambulance);
        this.e1 = (EditText) findViewById(R.id.addambulancedrivername);
        this.e2 = (EditText) findViewById(R.id.addambulanceEmail);
        this.e3 = (EditText) findViewById(R.id.addambulanceMobile);
        this.e4 = (EditText) findViewById(R.id.addambulanceDescription);
        this.e5 = (EditText) findViewById(R.id.addambulanceAddress);
        Button button = (Button) findViewById(R.id.registerAmbulance);
        this.b1 = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = AddAmbulance.this.e1.getText().toString();
                String email = AddAmbulance.this.e2.getText().toString();
                String mobile = AddAmbulance.this.e3.getText().toString();
                String description = AddAmbulance.this.e4.getText().toString();
                String address = AddAmbulance.this.e5.getText().toString();
                if (name == null || email == null || mobile == null || description == null || address == null) {
                    Toast.makeText(AddAmbulance.this.getApplicationContext(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                } else if (mobile.length() < 10 || mobile.length() > 12) {
                    Toast.makeText(AddAmbulance.this.getApplicationContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setMobile(mobile);
                    user.setDescription(description);
                    user.setAddress(address);
                    user.setType("ambulance");
                    try {
                        new DAO().addObject(Constants.USER_DB, user, user.getMobile());
                        Toast.makeText(AddAmbulance.this.getApplicationContext(), "Ambulance Added Successfully", Toast.LENGTH_SHORT).show();
                        AddAmbulance.this.startActivity(new Intent(AddAmbulance.this.getApplicationContext(), AdminHome.class));
                    } catch (Exception ex) {
                        Toast.makeText(AddAmbulance.this.getApplicationContext(), "Register Error", Toast.LENGTH_SHORT).show();
                        Log.v("Ambulance Registration", ex.toString());
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}