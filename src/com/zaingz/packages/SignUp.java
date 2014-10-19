package com.zaingz.packages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import model.Error;
import model.Session;
import model.User;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SignUp extends Activity {
	EditText[] fields = { null, null, null, null };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		fields[0] = (EditText) findViewById(R.id.userName1);
		fields[1] = (EditText) findViewById(R.id.email);
		fields[2] = (EditText) findViewById(R.id.password1);
		fields[3] = (EditText) findViewById(R.id.password2);
	}

	public void signup1(View view) {
	
		
	ArrayList<EditText> errorEdits = Helper.isTextFieldEmpty(fields[0],fields[1],fields[2],fields[3]);
		
		if (errorEdits.isEmpty())
			if (Helper.isValidEmail(fields[1].getText().toString()))
				if (fields[2].getText().toString().equals(fields[3].getText().toString()))
				if(fields[0].getText().toString().length()>3)	
			new Signup1().execute(fields[0].getText().toString(), fields[1]
					.getText().toString(), fields[2].getText().toString(),
					fields[3].getText().toString());
				else
					fields[0].setError("Username must be more than 3 chartacter");
				else{
					fields[3].setError("Password does not match");
				}
				else
				fields[1].setError("Email not valid");
			
		else{
			for (int i=0;i<errorEdits.size();i++)
				errorEdits.get(i).setError(errorEdits.get(i).getHint() +" is required");
				
			errorEdits.get(0).requestFocus();
		}
		
		
	}
	private class Signup1 extends AsyncTask<String, Void, String> {
		String error = null;
		 ProgressDialog pd;
		  protected void onPreExecute() {
	          // NOTE: You can call UI Element here.
	    	  pd = new ProgressDialog(SignUp.this);
				pd.setMessage("Siging in...");
				pd.show();
	          
	      }
		

		@Override
		protected String doInBackground(String... urls) {
			String URL = Helper.URL_USER;

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user[username]", urls[0].trim().toLowerCase()));
			params.add(new BasicNameValuePair("user[email]", urls[1].trim()));
			params.add(new BasicNameValuePair("user[password]", urls[2].trim()));
			params.add(new BasicNameValuePair("user[password_confirmation]",
					urls[3].trim()));

			HttpClient c = new DefaultHttpClient();
			HttpPost p = new HttpPost(URL);

			p.setHeader("User-Agent", "Android-app");
			try {
				p.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

				HttpResponse response = c.execute(p);

				if (response.getStatusLine().getStatusCode() == 201) {
					// save to tokken to db
					
					  Session s1 = new Session(); 
					  s1._id = 1; 
					  s1.tokken = EntityUtils.toString(response.getEntity()) ;
					  s1.save();
					 
					String token =  EntityUtils.toString(response.getEntity());
				
					HttpGet g = new HttpGet(URL);
				
					g.setHeader("Authorization", "Token token =" +token);
					Log.i("token in sign up", token);
					HttpResponse response1 = c.execute(g);
					String result = EntityUtils.toString(response1.getEntity());
					Log.i("user",result);
					//parse user and save it
					GsonBuilder gsonBuilder = new GsonBuilder();
		            Gson gson = gsonBuilder.create();
		            User user = gson.fromJson(result,User.class );
					Log.i("userinfo", user.username);
					user.save();
					

				} else if (response.getStatusLine().getStatusCode() == 422) {

					 error = EntityUtils.toString(response.getEntity());
					//Log.i("Sexo", error);
					return error;
				}

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

			return error;
		}

		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();
			if(error==null){
				Intent i = new Intent(SignUp.this ,DashBoard.class);
       	        startActivity(i);
       	        }
			
			if(error!=null){
			GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
			Error error = gson.fromJson(result, Error.class);
			ArrayList<String> userName = error.userName;
			ArrayList<String> email = error.email;
			
			if(userName.size()!= 0){
				fields[0].setError("Username"+userName.get(0));
			}
            if(email.size()!= 0){
            	fields[1].setError("Email"+email.get(0));
			}
            }
		}
	}

}
