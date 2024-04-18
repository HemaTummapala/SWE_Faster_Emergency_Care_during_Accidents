package com.example.betterandfasteremergency.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.betterandfasteremergency.util.Session;

public class RegisterActivity extends AppCompatActivity {
    Button b1;
    SQLiteDatabase db;
    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;
    EditText e6;
    EditText e7;
    ImageView i1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(RegisterActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Session s = new Session(getApplicationContext());
        this.e1 = (EditText) findViewById(R.id.registerPhone);
        this.e2 = (EditText) findViewById(R.id.registerPassword);
        this.e3 = (EditText) findViewById(R.id.registerConPass);
        this.e4 = (EditText) findViewById(R.id.registerEmail);
        this.e5 = (EditText) findViewById(R.id.registerMobile);
        this.e6 = (EditText) findViewById(R.id.registerName);
        this.e7 = (EditText) findViewById(R.id.registerAddress);
        Button button = (Button) findViewById(R.id.registerButton);
        this.b1 = button;
        this.i1=(ImageView) findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = RegisterActivity.this.e1.getText().toString();
                String password = RegisterActivity.this.e2.getText().toString();
                String conformPassword = RegisterActivity.this.e3.getText().toString();
                String email = RegisterActivity.this.e4.getText().toString();
                String mobile = RegisterActivity.this.e5.getText().toString();
                String name = RegisterActivity.this.e6.getText().toString();
                String address = RegisterActivity.this.e7.getText().toString();
                if (username == null || password == null || conformPassword == null || email == null || mobile == null || name == null || address == null) {
                    Toast.makeText(RegisterActivity.this.getApplicationContext(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                } else if (mobile.length() != 10) {
                    Toast.makeText(RegisterActivity.this.getApplicationContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(conformPassword)) {
                    Toast.makeText(RegisterActivity.this.getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.setMobile(mobile);
                    user.setName(name);
                    user.setAddress(address);
                    user.setType("user");
                    try {
                        new DAO().addObject(Constants.USER_DB, user, user.getUsername());
                        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                        s.setusename(username);
                        RegisterActivity.this.startActivity(new Intent(RegisterActivity.this.getApplicationContext(), AddFamily.class));
                        RegisterActivity registerActivity = RegisterActivity.this;
                        registerActivity.db = registerActivity.openOrCreateDatabase(Constants.sqLiteDatabase, 0, (SQLiteDatabase.CursorFactory) null);
                        RegisterActivity.this.db.execSQL("create table if not exists login(username varchar)");
                        SQLiteDatabase sQLiteDatabase = RegisterActivity.this.db;
                        sQLiteDatabase.execSQL("insert into login values('" + username + "')");
                        finish();
                    } catch (Exception ex) {
                        Toast.makeText(RegisterActivity.this.getApplicationContext(), "Register Error", Toast.LENGTH_SHORT).show();
                        Log.v("User Registration Ex", ex.toString());
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
        Intent intent= new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}