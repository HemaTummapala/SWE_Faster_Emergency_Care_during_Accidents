package com.example.betterandfasteremergency.view;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = LoginActivity.this.e1.getText().toString();
                String password = LoginActivity.this.e2.getText().toString();
                if (username == null || password == null || username.length() <= 0 || password.length() <= 0) {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Please Enter UserName and Password", Toast.LENGTH_SHORT).show();
                } else if (!username.equals("admin") || !password.equals("admin")) {
                    LoginActivity.this.session.setusename(username);
                    LoginActivity.this.session.setRole("user");
                    LoginActivity loginActivity = LoginActivity.this;
                    loginActivity.db = loginActivity.openOrCreateDatabase(Constants.sqLiteDatabase, 0, (SQLiteDatabase.CursorFactory) null);
                    LoginActivity.this.db.execSQL("create table if not exists login(username varchar)");
                    SQLiteDatabase sQLiteDatabase = LoginActivity.this.db;
                    sQLiteDatabase.execSQL("insert into login values('" + username + "')");
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class));
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Please Enter UserName and Password", Toast.LENGTH_SHORT).show();
                } else {
                    LoginActivity.this.session.setusename("admin");
                    LoginActivity.this.session.setRole("admin");
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), AdminHome.class));
                }
            }
        });
    }
}