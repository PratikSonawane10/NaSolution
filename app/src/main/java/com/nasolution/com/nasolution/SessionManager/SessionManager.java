package com.nasolution.com.nasolution.SessionManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.common.base.Strings;
import com.nasolution.com.nasolution.Home;
import com.nasolution.com.nasolution.Login;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;      // Shared pref mode
    SessionManager sessionManager;

    // Sharedpref file name
    private static final String PREF_NAME = "Preference";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_USER_ID = "user_MasterId";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_USERPASSWORD = "password";
    public static final String REGISTERED = "registered";
    public static final String MENU_REQUEST_CODE= "menuRequestCode";

    public SessionManager(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String user_MasterId, String name, String email, String password) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

        editor.putString(KEY_USER_ID, user_MasterId);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERPASSWORD, password);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user email id
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID,null));
        user.put(KEY_USERPASSWORD, pref.getString(KEY_USERPASSWORD,null));

        // return user
        return user;
    }

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
        else{
            Intent intent = new Intent(context, Home.class);
            context.startActivity(intent);
        }
    }
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, Login.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isRegisteredToken() {
        return pref.getBoolean(REGISTERED, false);
    }

    public void createRequestCode(int menuRquestCode){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

        editor.putString(MENU_REQUEST_CODE, String.valueOf(menuRquestCode));

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getMenuRequestCode() {
        HashMap<String, String> code = new HashMap<String, String>();
        // user email id
        code.put(MENU_REQUEST_CODE, pref.getString(MENU_REQUEST_CODE, "0"));

        // return user
        return code;
    }

}
