package com.example.my2ndproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.PrivateKey;
import java.security.PublicKey;

public class PrefManager {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private  String key ="MY_PREF";

    public PrefManager(Context context) {
        this.context = context;

        preferences = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveUserData(String uid,String email,String userName){
        editor.putString("PROFILE_ID",uid);
        editor.putString("PROFILE_NAME",userName);
        editor.putString("PROFILE_NAME",email);
        editor.putBoolean("IS_LOGIN",true);
        editor.apply();
    }
    public String getUId(){
        return preferences.getString("PROFILE_ID","NULL");
    }
    public String getUserName(){
        return preferences.getString("PROFILE_NAME","Null");
    }
    public String getUserEmail(){
        return preferences.getString("PROFILE_Email","Null");
    }
    public boolean isLogin(){
        return preferences.getBoolean("IS_LOGIN",false);
    }
}
