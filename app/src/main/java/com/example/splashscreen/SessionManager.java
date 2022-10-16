package com.example.splashscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

import java.util.HashMap;

import kotlin.contracts.Returns;


public class SessionManager {

    SharedPreferences usersession;
    SharedPreferences.Editor editor;
    Context context;


    public  static final String IS_LOGIN = "IsLoggedIn";
  public  static final String KEY_NAME = "name";
   public  static final String KEY_EMAIL = "email";
   public static final String KEY_MOBILE = "mobile";





    public SessionManager(Context context) {
        this.context = context;
        usersession=   context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor=usersession.edit();

    }

    public void createLoginSession(String name, String mobile, String email)

    {
     editor.putBoolean(IS_LOGIN,true) ;
     editor.putString(KEY_NAME,name);
     editor.putString(KEY_EMAIL,email);
     editor.putString(KEY_MOBILE,mobile);

     editor.commit();

    }
    public  boolean checkLogin()
    {
     if (usersession.getBoolean(IS_LOGIN,true))
     {
         return true;
     }
       else
     {
         return false;
     }
    }
    public  void  LogoutUser()
    {
        editor.clear();
        editor.commit();
        editor.apply();

        
    }
    public HashMap<String,String> getUserInfo()
    {
        HashMap<String,String>  userData= new   HashMap<String,String> ();
        userData.put(KEY_NAME,usersession.getString(KEY_NAME,null));
            userData.put(KEY_EMAIL,usersession.getString(KEY_EMAIL,null));
                userData.put(KEY_MOBILE,usersession.getString(KEY_MOBILE,null));
     return userData;
    }
}
