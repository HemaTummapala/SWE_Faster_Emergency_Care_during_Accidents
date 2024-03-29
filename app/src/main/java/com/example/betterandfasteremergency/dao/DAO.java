package com.example.betterandfasteremergency.dao;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.betterandfasteremergency.form.User;
import com.example.betterandfasteremergency.util.Constants;
import com.example.betterandfasteremergency.util.MapUtil;
import com.example.betterandfasteremergency.util.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DAO {
    public static DatabaseReference getDBReference(String dbName) {
        return GetFireBaseConnection.getConnection(dbName);
    }

    public static String getUnicKey(String dbName) {
        return getDBReference(dbName).push().getKey();
    }

    public int addObject(String dbName, Object obj, String key) {
        try {
            getDBReference(dbName).child(key).setValue(obj);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setDataToAdapterList(View view, Class c, String dbname, String type) {
        Log.v("in list populated ", "in list populated ");
        new HashMap();
        final Map<String, String> viewMap = new HashMap<>();
        final Class cls = c;
        final String str = dbname;
        final String str2 = type;
        final View view2 = view;
        getDBReference(dbname).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotNode : dataSnapshot.getChildren()) {
                    Log.v("in data found ", "in data found ");
                    String key = snapshotNode.getKey();
                    Object object = snapshotNode.getValue(cls);
                    if (str.equals(Constants.USER_DB)) {
                        User user = (User) object;
                        if (str2.equals("all")) {
                            if (!user.getType().equals("user")) {
                                viewMap.put(user.getName(), user.getMobile());
                            }
                        } else if (user.getType().equals(str2)) {
                            viewMap.put(user.getName(), user.getMobile());
                        }
                    }
                }
                ArrayList<String> al = new ArrayList<>(viewMap.keySet());
                if (view2 instanceof ListView) {
                    Log.v("in list view setting ", al.toString());
                    ListView myView = (ListView) view2;
                    myView.setAdapter(new ArrayAdapter<>(myView.getContext(), android.R.layout.simple_list_item_1, (String[]) al.toArray(new String[al.size()])));
                }
                new Session(view2.getContext()).setViewMap(MapUtil.mapToString(viewMap));
                Log.v("after session setting ", al.toString());
            }

            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public int deleteObject(String dbName, String key) {
        try {
            getDBReference(dbName).child(key).removeValue();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
