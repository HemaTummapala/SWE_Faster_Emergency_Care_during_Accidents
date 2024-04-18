package com.example.betterandfasteremergency.view;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.util.AppConstants;
import com.example.betterandfasteremergency.util.Session;

public class AdminHome extends AppCompatActivity {
    Button addAmbulance;
    Button addHospital;
    Button addbloodbank;
    Button adminLogout;
    private Session session;
    Button viewUsers;
    ImageView i1;


    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(AdminHome.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        this.addHospital = (Button) findViewById(R.id.addhospital);
        this.addAmbulance = (Button) findViewById(R.id.addambulance);
        this.addbloodbank = (Button) findViewById(R.id.addbloodbank);
        this.viewUsers = (Button) findViewById(R.id.adminviewusers);
        this.adminLogout = (Button) findViewById(R.id.adminlogout);
        this.i1 = (ImageView) findViewById(R.id.back);
        final Session s = new Session(getApplicationContext());
        this.addHospital.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), AddHospital.class));
                finish();
            }
        });
        this.addAmbulance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), AddAmbulance.class));
                finish();
            }
        });
        this.addbloodbank.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), AddBloodBank.class));
                finish();
            }
        });
        this.adminLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                s.loggingOut();
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        this.viewUsers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.v("in list view action ", "");
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), AdminListUser.class));
                finish();
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
        Intent intent = new Intent(AdminHome.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}