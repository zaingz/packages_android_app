package com.zaingz.packages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SignUp extends Activity {
	EditText[] fields = {null,null,null,null};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		fields[0]=(EditText) findViewById(R.id.userName1);
		fields[1]=(EditText) findViewById(R.id.email);
		fields[2]=(EditText) findViewById(R.id.password1);
		fields[3]=(EditText) findViewById(R.id.password2);
	}
	 public void signup1(View view)  {
		 new signup1().execute(fields[0].getText().toString(),fields[1].getText().toString(),fields[2].getText().toString(),fields[3].getText().toString());
	   }
	private class signup1 extends AsyncTask<String, Void, Void> {
		String response,response1;
		@Override
		protected Void doInBackground(String... urls) {
			String URL1 = "http://packages-api2.herokuapp.com/users";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user[username]", urls[0].trim()));
			params.add(new BasicNameValuePair("user[email]", urls[1].trim()));
			params.add(new BasicNameValuePair("user[password]", urls[2].trim()));
			params.add(new BasicNameValuePair("user[password_confirmation]", urls[3].trim()));
			
			HttpClient c = new DefaultHttpClient();
			HttpPost p = new HttpPost(URL1);
			
			p.setHeader("User-Agent", "Android-app");
	          try {
	        	  p.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
	        	  ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        	  response= c.execute(p,responseHandler);
	        	  Log.i("Login", response);
	      
	        	  HttpGet g = new HttpGet(URL1);
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
