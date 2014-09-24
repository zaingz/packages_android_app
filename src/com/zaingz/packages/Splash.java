package com.zaingz.packages;

import model.Session;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Session")
public class Splash extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       /* Session s1 = new Session();
        s1._id = 1;
        s1.tokken = "Restaurants";
        s1.save();
        Session s;*/
         if (new Select().from(Session.class).execute().isEmpty()){
        	 Intent i = new Intent(Splash.this ,Login.class);
        	 startActivity(i);
         }
         else{
        	//Todo
         }
    }
	 @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	}


  

