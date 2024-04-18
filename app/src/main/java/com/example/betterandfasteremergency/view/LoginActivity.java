package com.example.betterandfasteremergency.view;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.util.AppConstants;
import com.example.betterandfasteremergency.util.Constants;
import com.example.betterandfasteremergency.util.Session;
public class LoginActivity extends AppCompatActivity {
    Button b1;
    SQLiteDatabase db;
    EditText e1;
    EditText e2;
    ImageView i1;
    /* access modifiers changed from: private */
    public Session session;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(LoginActivity.this);
        super.onCreate(savedInstanceState);
        this.session = new Session(getApplicationContext());
        setContentView(R.layout.activity_login);
        this.e1 = (EditText) findViewById(R.id.loginPhone);
        this.e2 = (EditText) findViewById(R.id.loginPass);
        Button button = (Button) findViewById(R.id.loginConfirm);
        this.b1 = button;
        this.i1=(ImageView) findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = LoginActivity.this.e1.getText().toString();
                String password = LoginActivity.this.e2.getText().toString();
                if (username == null || password == null || username.length() <= 0 || password.length() <= 0) {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Please Enter UserName and Password", Toast.LENGTH_SHORT).show();
                } else if (!username.equalsIgnoreCase("admin") || !password.equalsIgnoreCase("admin")) {
                    LoginActivity.this.session.setusename(username);
                    LoginActivity.this.session.setRole("user");
                    LoginActivity loginActivity = LoginActivity.this;
                    loginActivity.db = loginActivity.openOrCreateDatabase(Constants.sqLiteDatabase, 0, (SQLiteDatabase.CursorFactory) null);
                    LoginActivity.this.db.execSQL("create table if not exists login(username varchar)");
                    SQLiteDatabase sQLiteDatabase = LoginActivity.this.db;
                    sQLiteDatabase.execSQL("insert into login values('" + username + "')");
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    LoginActivity.this.session.setusename("admin");
                    LoginActivity.this.session.setRole("admin");
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), AdminHome.class));
                    finish();
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

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}