package com.zaingz.packages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import model.Session;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class Login extends Activity {
	EditText[] fields = {null,null};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	fields[0]=(EditText) findViewById(R.id.userName);
	fields[1]=(EditText) findViewById(R.id.password);
	}
	public void signIn(View view)  {
		  
		   new Tokken().execute(fields[0].getText().toString(),fields[1].getText().toString());
	   }
	
	   public void signUp(View view)  {
		   Intent i = new Intent(Login.this ,SignUp.class);
		   startActivity(i);
		   
	   }
	private class Tokken extends AsyncTask<String, Void, Void> {
		String response,response1;
		@Override
		protected Void doInBackground(String... urls) {
			String URL = "http://packages-api2.herokuapp.com/users/session";
			String URl1 = "http://packages-api2.herokuapp.com/users";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if(urls[0].contains("@")&&urls[0].contains(".")){
				params.add(new BasicNameValuePair("email", urls[0].trim()));
			}
			else
	        {
				params.add(new BasicNameValuePair("username", urls[0].trim()));
	        }
	        
	      	params.add(new BasicNameValuePair("password", urls[1].trim()));
			HttpClient c = new DefaultHttpClient();
			HttpPost p = new HttpPost(URL);
			
			p.setHeader("User-Agent", "Android-app");
	          try {
	      p.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
	      response= c.execute(p,responseHandler);
	      
	      Log.i("Login", response);
	      
	      HttpGet g = new HttpGet(URl1);
	      g.setHeader("Authorization", "Token token ="+response);
	      ResponseHandler<String> responseHandler1 = new BasicResponseHandler();
	      response1 = c.execute(g,responseHandler1);
	      Log.i("Login1", response1);
	     
	     } catch (UnsupportedEncodingException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	     } catch (ClientProtocolException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	     } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	     }
	    	

			return null;
		}
	@Override
	protected void onPostExecute(Void result) {
		/* Session s1 = new Session();
	        s1._id = 1;
	        s1.tokken = response;
	        s1.save();*/
		 
	}
	}
	
	
}
