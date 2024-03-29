package com.example.betterandfasteremergency;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class App extends Application {
    public static App mInstance;

    public static Context applicationContext;


    public static App instance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        applicationContext = this;


        FirebaseApp.initializeApp(App.this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        FirebaseCrashlytics.getInstance().sendUnsentReports();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

}
