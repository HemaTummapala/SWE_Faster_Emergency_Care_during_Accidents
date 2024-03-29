package com.example.betterandfasteremergency.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.betterandfasteremergency.util.AppConstants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.dao.DAO;
import com.example.betterandfasteremergency.form.User;
import com.example.betterandfasteremergency.util.Constants;
import com.example.betterandfasteremergency.util.Session;

public class ViewUser extends AppCompatActivity {
    Button cancel;
    Button delete;
    EditText description;
    Button request;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(ViewUser.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        this.cancel = (Button) findViewById(R.id.viewUserCanel);
        this.delete = (Button) findViewById(R.id.viewUserDelete);
        final String role = new Session(getApplicationContext()).getRole();
        if (!role.equals("admin")) {
            this.delete.setEnabled(false);
        }
        Log.v("in view user role ", role);
        final String userId = getIntent().getExtras().getString("userid");
        Log.v("in view user userid ", userId);
        this.t1 = (TextView) findViewById(R.id.textviewname);
        this.t2 = (TextView) findViewById(R.id.textviewemail);
        this.t3 = (TextView) findViewById(R.id.textviewmobile);
        this.t4 = (TextView) findViewById(R.id.textviewaddress);
        this.t5 = (TextView) findViewById(R.id.textviewdescription);
        new DAO();
        DAO.getDBReference(Constants.USER_DB).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = (User) dataSnapshot.getValue(User.class);
                if (user != null) {
                    if (user.getType().equals("ambulance")) {
                        ViewUser.this.t1.setText("Driver Name : " + user.getName());
                    } else if (user.getType().equals("hospital")) {
                        ViewUser.this.t1.setText("Hospital Name : " + user.getName());
                    } else if (user.getType().equals("user")) {
                        ViewUser.this.t1.setText("Patient Name : " + user.getName());
                    } else if (user.getType().equals("bloodbank")) {
                        ViewUser.this.t1.setText("Blood Bank Name : " + user.getName());
                    }
                    String[] workLocation = user.getAddress().split(",");
                    String userAddress = "";
                    try {
                        List<Address> addresses = new Geocoder(ViewUser.this.getApplicationContext(), Locale.getDefault()).getFromLocation(new Double(workLocation[0]).doubleValue(), new Double(workLocation[1]).doubleValue(), 1);
                        String address = addresses.get(0).getAddressLine(0);
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        if (address != null) {
                            userAddress = userAddress + address + "\n";
                        }
                        if (city != null) {
                            userAddress = userAddress + city + "\n";
                        }
                        if (state != null) {
                            userAddress = userAddress + state + "\n";
                        }
                        if (country != null) {
                            userAddress = userAddress + country + "\n";
                        }
                        if (postalCode != null) {
                            userAddress = userAddress + postalCode + "\n";
                        }
                        if (knownName != null) {
                            userAddress = userAddress + knownName + "\n";
                        }
                    } catch (Exception e) {
                        Log.v("voidmain ", "in on succes ");
                    }
                    Log.v("voidmai work address ", userAddress);
                    ViewUser.this.t2.setText("Email: " + user.getEmail());
                    ViewUser.this.t3.setText("Mobile: " + user.getMobile());
                    ViewUser.this.t4.setText("Address: " + userAddress);
                    ViewUser.this.t5.setText("Description : " + user.getDescription());
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
        this.cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i;
                if (role.equals("admin")) {
                    i = new Intent(ViewUser.this.getApplicationContext(), AdminHome.class);
                } else {
                    i = new Intent(ViewUser.this.getApplicationContext(), MainActivity.class);
                }
                ViewUser.this.startActivity(i);
            }
        });
        this.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new DAO().deleteObject(Constants.USER_DB, userId);
                ViewUser.this.startActivity(new Intent(ViewUser.this.getApplicationContext(), AdminHome.class));
            }
        });
    }
}