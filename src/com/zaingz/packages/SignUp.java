package com.zaingz.packages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;




import android.content.Context;
import android.content.pm.FeatureInfo;
import android.widget.Toast;
import model.User;
import model.Error;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.zaingz.packages.R.drawable;

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
	/*EditText et =	Helper.isTextFieldEmpty(fields);
	if(et != null){
	et.setError("This field can not be empty");
	
	}
	//idhar aik issue hai agr error hai tu dobara theek kasa karing ga
	//wahan type ni o raha zra deahko yahan
	else{	
	
		
	}*/
		
	ArrayList<EditText> errorEdits = Helper.isTextFieldEmpty(fields[0],fields[1],fields[2],fields[3]);
		
		if (errorEdits.isEmpty())
			if (Helper.isValidEmail(fields[1].getText().toString()))
			new Signup1().execute(fields[0].getText().toString(), fields[1]
					.getText().toString(), fields[2].getText().toString(),
					fields[3].getText().toString());
			else
				fields[1].setError("Email not valid");
		else{
			for (int i=0;i<errorEdits.size();i++)
				errorEdits.get(i).setError("Field is required");
				
			errorEdits.get(0).requestFocus();
		}
		
		
	}
	private class Signup1 extends AsyncTask<String, Void, String> {
		String error = null;
		

		@Override
		protected String doInBackground(String... urls) {
			String URL = Helper.URL_USER;

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user[username]", urls[0].trim()));
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
					/*
					  Session s1 = new Session(); 
					  s1._id = 1; 
					  s1.tokken = EntityUtils.toString(response.getEntity()) ;
					  s1.save();
					 */
					Log.i("Signup", EntityUtils.toString(response.getEntity()) );
					HttpGet g = new HttpGet(URL);
					String token =  EntityUtils.toString(response.getEntity());
					g.setHeader("Authorization", "Token token =" +token);
					
					HttpResponse response1 = c.execute(p);
					String result = EntityUtils.toString(response1.getEntity());
					Log.i("user",result);
					//parse user and save it
					//User user = new Gson().fromJson(EntityUtils.toString(response1.getEntity()), User.class);
      // Log.i("sexo11", user.email);
					
					

				} else if (response.getStatusLine().getStatusCode() == 422) {

					 error = EntityUtils.toString(response.getEntity());
					Log.i("Sexo", error);
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
			//Log.i("Sexo1", result);
			Error error = new Gson().fromJson(result, Error.class);
			Log.i("Sexo1", error.username);
			
		
			
			

		}
	}

}
