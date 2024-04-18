package com.example.betterandfasteremergency.view;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.dao.DAO;
import com.example.betterandfasteremergency.form.User;
import com.example.betterandfasteremergency.util.AppConstants;
import com.example.betterandfasteremergency.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AddHospital extends AppCompatActivity {
    Button b1;
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;
    ImageView i1;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyBE87m2FfpEopd_tF6cW8zJ9ibHx0o0vOQ";

    private static final String DESCRIPTION = "description";


    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(AddHospital.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);
        this.e1 = (EditText) findViewById(R.id.addhospitalname);
        this.e2 = (EditText) findViewById(R.id.addhospitalEmail);
        this.e3 = (EditText) findViewById(R.id.addhospitalMobile);
        this.e4 = (EditText) findViewById(R.id.addhospitalDescription);
        this.e5 = (EditText) findViewById(R.id.addhospitalAddress);
        Button button = (Button) findViewById(R.id.registerHospital);
        this.b1 = button;
        this.i1=(ImageView) findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = AddHospital.this.e1.getText().toString();
                String email = AddHospital.this.e2.getText().toString();
                String mobile = AddHospital.this.e3.getText().toString();
                String description = AddHospital.this.e4.getText().toString();
                String address = AddHospital.this.e5.getText().toString();
                if (name == null || email == null || mobile == null || description == null || address == null) {
                    Toast.makeText(AddHospital.this.getApplicationContext(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                } else if (mobile.length() < 10 || mobile.length() > 12) {
                    Toast.makeText(AddHospital.this.getApplicationContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setMobile(mobile);
                    user.setDescription(description);
                    user.setAddress(address);
                    user.setType("hospital");
                    try {
                        new DAO().addObject(Constants.USER_DB, user, user.getMobile());
                        Toast.makeText(AddHospital.this.getApplicationContext(), "Hospital Added Successfully", Toast.LENGTH_SHORT).show();
                        AddHospital.this.startActivity(new Intent(AddHospital.this.getApplicationContext(), AdminHome.class));
                        finish();
                    } catch (Exception ex) {
                        Toast.makeText(AddHospital.this.getApplicationContext(), "Register Error", Toast.LENGTH_SHORT).show();
                        Log.v("Hospital Registration", ex.toString());
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
        Intent intent = new Intent(AddHospital.this, AdminHome.class);
        startActivity(intent);
        finish();
    }


}
