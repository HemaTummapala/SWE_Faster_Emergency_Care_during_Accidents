package com.example.betterandfasteremergency.view;
        import android.content.Intent;
        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;

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
        final Session s = new Session(getApplicationContext());
        this.addHospital.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), AddHospital.class));
            }
        });
        this.addAmbulance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), AddAmbulance.class));
            }
        });
        this.addbloodbank.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), AddBloodBank.class));
            }
        });
        this.adminLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                s.loggingOut();
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), MainActivity.class));
            }
        });
        this.viewUsers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.v("in list view action ", "");
                AdminHome.this.startActivity(new Intent(AdminHome.this.getApplicationContext(), AdminListUser.class));
            }
        });
    }
}