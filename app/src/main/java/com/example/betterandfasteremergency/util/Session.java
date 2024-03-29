package com.example.betterandfasteremergency.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class Session {
    Context c;
    private SharedPreferences prefs;

    public Session(Context cntx) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
        this.c = cntx;
    }

    public void setusename(String usename) {
        this.prefs.edit().putString("usename", usename).commit();
    }

    public String getusename() {
        return this.prefs.getString("usename", "");
    }

    public void setCart(Set<String> cart) {
        this.prefs.edit().putStringSet("cart", cart).commit();
    }

    public Set<String> getCart() {
        return this.prefs.getStringSet("cart", new HashSet());
    }

    public void setRole(String role) {
        this.prefs.edit().putString("role", role).commit();
    }

    public String getRole() {
        return this.prefs.getString("role", "");
    }

    public void setViewMap(String viewMap) {
        this.prefs.edit().putString("viewMap", viewMap).commit();
    }

    public String getViewMap() {
        return this.prefs.getString("viewMap", "");
    }

    public void loggingOut() {
        this.c.getSharedPreferences(getusename(), 0).edit().remove(getusename()).commit();
    }
}