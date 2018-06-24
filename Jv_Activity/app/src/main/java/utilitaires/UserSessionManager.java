package utilitaires;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import dominique.fr.jv_activity.ConnexionActivity;
import dominique.fr.jv_activity.MainActivity;


/**
 * Created by Dominique DURI on 16/02/2018.
 */

public class UserSessionManager {
    //Shared Preferences reference
    SharedPreferences pref;

    //Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    //context
    Context cont;

    //shared pref mode
    int PRIVATE_MODE = 0;

    //Sharedpref file name
    private static final String PREFER_NAME = "AndroidExemplePref";

    //All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    //User id (make variable public to access from outside)
    public static final String KEY_ID = "user_id";

    /*
    //User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    //Email adress (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    */

    //Constructor
    public UserSessionManager(Context context){
        this.cont = context;
        pref = cont.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    //create login session
    public void createUserLoginSession(Integer user_id) {
        //storing login value as true
        editor.putBoolean(IS_USER_LOGIN, true);
        //storing id in pref
        editor.putInt(KEY_ID, user_id);
        /*
        //storing name in pref
        editor.putString(KEY_NAME, name);
        //storing email in pref
        editor.putString(KEY_EMAIL, email);
        */

        //commit changes
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
    /*
    Check login method will check user login status
    if false it will redirect user to login page
    else do anything
     */
    public boolean checkLogin() {
        //Check login status
        //if not loggin :
        if(!this.isUserLoggedIn()){
            //user is not logged in -> redirect him to Login Activity
            Intent i = new Intent(cont, ConnexionActivity.class);
            //closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //Add new flag to star new activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Starting Login Activity
            cont.startActivity(i);
            return true;
        }
        return false;
    }

    /*
    Get stored session data
     */
    public HashMap<String, Integer> getUserDetails() {
        //Use hashmap to store user credentials
        HashMap<String, Integer> user = new HashMap<>();
        //user name
        user.put(KEY_ID, pref.getInt(KEY_ID, 0));
       /*
        //user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        //user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        */
        //return user
        return user;
    }

    /*
    Clear session details
     */
    public void logoutUser() {
        //clearing all user's datas from SharedPreferences
        editor.clear();
        editor.commit();

        //after logout redirect user to login Activity
        Intent i = new Intent(cont, MainActivity.class);
        //closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Add new flag to star new activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Starting Login Activity
        cont.startActivity(i);
    }
}

