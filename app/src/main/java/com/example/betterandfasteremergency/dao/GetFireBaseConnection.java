package com.example.betterandfasteremergency.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GetFireBaseConnection {
    public static FirebaseDatabase firebaseDatabase = null;

    public static DatabaseReference getConnection(String database) {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }
        return firebaseDatabase.getReference(database);
    }
}
