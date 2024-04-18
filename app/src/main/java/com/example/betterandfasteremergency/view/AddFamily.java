package com.example.betterandfasteremergency.view;
import android.content.Intent;
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
import com.example.betterandfasteremergency.form.Family;
import com.example.betterandfasteremergency.util.AppConstants;
import com.example.betterandfasteremergency.util.Constants;
import com.example.betterandfasteremergency.util.Session;
public class AddFamily extends AppCompatActivity {
    Button b1;
    EditText e1;
    EditText e2;
    EditText e3;
    ImageView i1;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(AddFamily.this);
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_add_family);
        final Session session = new Session(getApplicationContext());
        this.e1 = (EditText) findViewById(R.id.addfamilymobile1);
        this.e2 = (EditText) findViewById(R.id.addfamilymobile2);
        this.e3 = (EditText) findViewById(R.id.addfamilymobile3);
        Button button = (Button) findViewById(R.id.addFamilyButton);
        this.b1 = button;
        this.i1=(ImageView) findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String m1 = AddFamily.this.e1.getText().toString();
                String m2 = AddFamily.this.e2.getText().toString();
                String m3 = AddFamily.this.e3.getText().toString();
                if (m1 == null || m2 == null || m3 == null) {
                    Toast.makeText(AddFamily.this.getApplicationContext(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                } else if (m1.length() == 10 && m2.length() == 10 && m3.length() == 10) {
                    Family family = new Family();
                    family.setFamilyId(DAO.getUnicKey(Constants.FAMILY_DB));
                    family.setMobile1(m1);
                    family.setMobile2(m2);
                    family.setMobile3(m3);
                    family.setUserName(session.getusename());
                    try {
                        new DAO().addObject(Constants.FAMILY_DB, family, family.getFamilyId());
                        Toast.makeText(AddFamily.this.getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                        AddFamily.this.startActivity(new Intent(AddFamily.this.getApplicationContext(), MainActivity.class));
                        finish();
                    } catch (Exception ex) {
                        Toast.makeText(AddFamily.this.getApplicationContext(), "Register Error", Toast.LENGTH_SHORT).show();
                        Log.v("Family Registration", ex.toString());
                        ex.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddFamily.this.getApplicationContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
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
        Intent intent= new Intent(AddFamily.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}