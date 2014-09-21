package com.zaingz.packages;

import model.Session;
import android.app.Activity;
import android.os.Bundle;

import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.util.Log;

@Table(name = "Categories")
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Session s1 = new Session();
        s1.id = 1;
        s1.tokken = "Restaurants";
        s1.save();
      Session s;
      s = (Session) new Select().from(Session.class).execute().get(0);
      Log.i("String",s.tokken);
      
        	
        }
        
        
        
    }


  

