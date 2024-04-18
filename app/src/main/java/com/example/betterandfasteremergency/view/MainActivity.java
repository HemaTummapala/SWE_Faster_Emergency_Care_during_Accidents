package com.example.betterandfasteremergency.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.betterandfasteremergency.R;
import com.example.betterandfasteremergency.util.AppConstants;
import com.example.betterandfasteremergency.util.GpsUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.example.betterandfasteremergency.dao.DAO;
import com.example.betterandfasteremergency.form.Family;
import com.example.betterandfasteremergency.form.User;
import com.example.betterandfasteremergency.util.Constants;
import com.example.betterandfasteremergency.util.Session;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final int SHAKE_THRESHOLD = 600;
    private Sensor accelerometer;
    Button b1;
    Button b2;
    Button b3;
    Button b5;

    ImageView ivLogout;
    /* access modifiers changed from: private */
    public boolean isGPS = false;
    private long lastUpdate = 0;
    private float last_x;
    private float last_y;
    private float last_z;
    /* access modifiers changed from: private */
    public LocationCallback locationCallback;
    /* access modifiers changed from: private */
    public LocationRequest locationRequest;
    /* access modifiers changed from: private */
    public FusedLocationProviderClient mFusedLocationClient;
    private SensorManager sensorManager;
    SQLiteDatabase sqLiteDatabase;
    /* access modifiers changed from: private */
    public String txtLocation;
    String userName;
    /* access modifiers changed from: private */
    public double wayLatitude = 0.0d;
    /* access modifiers changed from: private */
    public double wayLongitude = 0.0d;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        AppConstants.setStatusBarGradiant(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sensorManager2 = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.sensorManager = sensorManager2;
        if (sensorManager2.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            Sensor defaultSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            this.accelerometer = defaultSensor;
            this.sensorManager.registerListener(this, defaultSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(getApplicationContext(), "dont have accelerometer sensor", Toast.LENGTH_LONG).show();
        }
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        LocationRequest create = LocationRequest.create();
        this.locationRequest = create;
        create.setPriority(100);
        this.locationRequest.setInterval(10000);
        this.locationRequest.setFastestInterval(5000);
        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            public void gpsStatus(boolean isGPSEnable) {
                boolean unused = MainActivity.this.isGPS = isGPSEnable;
            }
        });
        this.locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            double unused = MainActivity.this.wayLatitude = location.getLatitude();
                            double unused2 = MainActivity.this.wayLongitude = location.getLongitude();
                            MainActivity mainActivity = MainActivity.this;
                            String unused3 = mainActivity.txtLocation = MainActivity.this.wayLatitude + "," + MainActivity.this.wayLongitude;
                            if (MainActivity.this.mFusedLocationClient != null) {
                                MainActivity.this.mFusedLocationClient.removeLocationUpdates(MainActivity.this.locationCallback);
                            }
                        }
                    }
                }
            }
        };


        this.b1 = (Button) findViewById(R.id.loginButton);
        this.b2 = (Button) findViewById(R.id.registerButton);
        this.b3 = (Button) findViewById(R.id.emergencyalert);
        this.b5 = (Button) findViewById(R.id.userviewusers);
        this.ivLogout = (ImageView) findViewById(R.id.logout);
        Session session = new Session(getApplicationContext());
        SQLiteDatabase openOrCreateDatabase = openOrCreateDatabase(Constants.sqLiteDatabase, 0, (SQLiteDatabase.CursorFactory) null);
        this.sqLiteDatabase = openOrCreateDatabase;
        openOrCreateDatabase.execSQL("create table if not exists login(username varchar)");
        Cursor cursor = this.sqLiteDatabase.rawQuery("select * from login", (String[]) null);
        if (cursor != null && cursor.moveToFirst()) {
            this.userName = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            cursor.close();
        }
        String str = this.userName;
        if (str == null || str == "") {
            this.ivLogout.setVisibility(View.GONE);
            this.b3.setEnabled(false);
        } else {
            session.setusename(str);
            session.setRole("user");
            this.b1.setEnabled(false);
            this.b2.setEnabled(false);
            this.ivLogout.setVisibility(View.VISIBLE);

        }

        this.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivLogout.setVisibility(View.GONE);
                b1.setEnabled(true);
                b2.setEnabled(true);
                b3.setEnabled(false);
                final Session s = new Session(getApplicationContext());
                s.loggingOut();



            }
        });

        this.b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), LoginActivity.class));
            }
        });
        this.b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
        this.b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(MainActivity.this.getApplicationContext(), "Updating Location", Toast.LENGTH_SHORT).show();
                if (!MainActivity.this.isGPS) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
                    return;
                }
                MainActivity.this.getLocation();
                if (MainActivity.this.txtLocation != null) {
                    final String[] userLatLongs = MainActivity.this.txtLocation.split(",");
                    new DAO();
                    DAO.getDBReference(Constants.FAMILY_DB).addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshotNode : dataSnapshot.getChildren()) {
                                Family family = (Family) snapshotNode.getValue(Family.class);
                                if (family != null && family.getUserName().equals(MainActivity.this.userName)) {
                                    final Set<String> senders = new HashSet<>();
                                    senders.add(family.getMobile1());
                                    senders.add(family.getMobile2());
                                    senders.add(family.getMobile3());
                                    Toast.makeText(MainActivity.this.getApplicationContext(), "Family Added", Toast.LENGTH_LONG).show();
                                    new DAO();
                                    DAO.getDBReference(Constants.USER_DB).addValueEventListener(new ValueEventListener() {
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshotNode : dataSnapshot.getChildren()) {
                                                User user = (User) snapshotNode.getValue(User.class);
                                                if (user != null) {
                                                    Log.v("user info :", user.toString());
                                                    if (!user.getType().equals("user")) {
                                                        try{
                                                            String[] latLongs = user.getAddress().split(",");
                                                            if (MainActivity.getDistanceFromCurrentPosition(new Double(userLatLongs[0]).doubleValue(), new Double(userLatLongs[1]).doubleValue(), new Double(latLongs[0]).doubleValue(), new Double(latLongs[1]).doubleValue()) < 10000.0f) {
                                                                senders.add(user.getMobile());
                                                            }
                                                        }catch (Exception e){

                                                        }

                                                    }
                                                }
                                                Toast.makeText(MainActivity.this.getApplicationContext(), "Nearest Added", Toast.LENGTH_LONG).show();
                                            }
                                            Context applicationContext = MainActivity.this.getApplicationContext();
                                            Toast.makeText(applicationContext, "Count" + senders.size(), Toast.LENGTH_LONG).show();
                                            PendingIntent pi = PendingIntent.getActivity(MainActivity.this.getApplicationContext(), 0, new Intent(MainActivity.this.getApplicationContext(), MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
                                            ArrayList<PendingIntent> pendingIntents = new ArrayList<>();
                                            pendingIntents.add(pi);
                                            SmsManager sms = SmsManager.getDefault();
                                            for (String mobile : senders) {
                                                Toast.makeText(MainActivity.this.getApplicationContext(), "in for while sending", Toast.LENGTH_LONG).show();
                                                sms.sendMultipartTextMessage(mobile, (String) null, sms.divideMessage(MainActivity.this.userName + " is in Emergency at https://maps.google.com/?q=" + userLatLongs[0] + "," + userLatLongs[1]), pendingIntents, (ArrayList) null);
                                                Toast.makeText(MainActivity.this.getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    return;
                }
                Toast.makeText(MainActivity.this.getApplicationContext(), "Location Not Updated", Toast.LENGTH_SHORT).show();
            }
        });
        this.b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.v("going to list users :", "");
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), ListUsers.class));
                finish();
            }
        });
    }

    public static float getDistanceFromCurrentPosition(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = (Math.sin(dLat / 2.0d) * Math.sin(dLat / 2.0d)) + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2.0d) * Math.sin(dLng / 2.0d));
        return new Float(((double) 1609) * 3958.75d * Math.atan2(Math.sqrt(a), Math.sqrt(1.0d - a)) * 2.0d).floatValue();
    }

    /* access modifiers changed from: private */
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, new OnSuccessListener<Location>() {
                public void onSuccess(Location location) {
                    if (location != null) {
                        double unused = MainActivity.this.wayLatitude = location.getLatitude();
                        double unused2 = MainActivity.this.wayLongitude = location.getLongitude();
                        MainActivity mainActivity = MainActivity.this;
                        String unused3 = mainActivity.txtLocation = MainActivity.this.wayLatitude + "," + MainActivity.this.wayLongitude;
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    MainActivity.this.mFusedLocationClient.requestLocationUpdates(MainActivity.this.locationRequest, MainActivity.this.locationCallback, null);
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1003);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                this.mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, new OnSuccessListener<Location>() {
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double unused = MainActivity.this.wayLatitude = location.getLatitude();
                            double unused2 = MainActivity.this.wayLongitude = location.getLongitude();
                            MainActivity mainActivity = MainActivity.this;
                            String unused3 = mainActivity.txtLocation = MainActivity.this.wayLatitude + "," + MainActivity.this.wayLongitude;
                            return;
                        }
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        MainActivity.this.mFusedLocationClient.requestLocationUpdates(MainActivity.this.locationRequest, MainActivity.this.locationCallback, null);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 1001) {
            this.isGPS = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this, this.accelerometer, 3);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        long curTime = System.currentTimeMillis();
        long j = this.lastUpdate;
        if (curTime - j > 100) {
            this.lastUpdate = curTime;
            float speed = (Math.abs(((((x + y) + z) - this.last_x) - this.last_y) - this.last_z) / ((float) (curTime - j))) * 10000.0f;
            if (speed > 600.0f) {
                if (!this.isGPS) {
                    Toast.makeText(getApplicationContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
                    return;
                }
                getLocation();
                String str = this.txtLocation;
                if (str != null) {
                    final String[] userLatLongs = str.split(",");
                    new DAO();
                    DAO.getDBReference(Constants.FAMILY_DB).addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshotNode : dataSnapshot.getChildren()) {
                                Family family = (Family) snapshotNode.getValue(Family.class);
                                if (family != null && family.getUserName().equals(MainActivity.this.userName)) {
                                    final Set<String> senders = new HashSet<>();
                                    senders.add(family.getMobile1());
                                    senders.add(family.getMobile2());
                                    senders.add(family.getMobile3());
                                    new DAO();
                                    DAO.getDBReference(Constants.USER_DB).addValueEventListener(new ValueEventListener() {
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshotNode : dataSnapshot.getChildren()) {
                                                User user = (User) snapshotNode.getValue(User.class);
                                                if (user != null) {
                                                    Log.v("user info :", user.toString());
                                                    if (!user.getType().equals("user")) {
                                                        try {
                                                            String[] latLongs = user.getAddress().split(",");

                                                            if (MainActivity.getDistanceFromCurrentPosition(new Double(userLatLongs[0]).doubleValue(), new Double(userLatLongs[1]).doubleValue(), new Double(latLongs[0]).doubleValue(), new Double(latLongs[1]).doubleValue()) < 10000.0f) {
                                                                senders.add(user.getMobile());
                                                            }
                                                        }catch (Exception e){}

                                                    }
                                                }
                                            }
                                            PendingIntent pi = PendingIntent.getActivity(MainActivity.this.getApplicationContext(), 0, new Intent(MainActivity.this.getApplicationContext(), MainActivity.class), PendingIntent.FLAG_IMMUTABLE);
                                            ArrayList<PendingIntent> pendingIntents = new ArrayList<>();
                                            pendingIntents.add(pi);
                                            SmsManager sms = SmsManager.getDefault();
                                            for (String mobile : senders) {
                                                sms.sendMultipartTextMessage(mobile, (String) null, sms.divideMessage(MainActivity.this.userName + " is in Emergency at https://maps.google.com/?q=" + userLatLongs[0] + "," + userLatLongs[1]), pendingIntents, (ArrayList) null);
                                                Toast.makeText(MainActivity.this.getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
                Context applicationContext = getApplicationContext();
                Toast.makeText(applicationContext, "accident occured x: " + x + " \n y:" + y + " \n z:" + z + " \n speed:" + speed, Toast.LENGTH_LONG).show();
            }
            this.last_x = x;
            this.last_y = y;
            this.last_z = z;
        }
    }

    @Override
    public void onBackPressed() {
        MainActivity.this.moveTaskToBack(true);
        MainActivity.this.finish();
    }
}