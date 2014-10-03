package com.zaingz.packages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import model.Session;

import org.apache.http.HttpResponse;
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
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {
	EditText[] fields = { null, null };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		fields[0] = (EditText) findViewById(R.id.userNameOrEmail);
		fields[1] = (EditText) findViewById(R.id.password);
	}

	public void signIn(View view) {
		
		ArrayList<EditText> errorEdits = Helper.isTextFieldEmpty(fields[0],fields[1]);
		
		if (errorEdits.isEmpty())
		new Tokken().execute(fields[0].getText().toString(), fields[1]
				.getText().toString());
		else{
			for (int i=0;i<errorEdits.size();i++)
				errorEdits.get(i).setError("Field is required");
				
			errorEdits.get(0).requestFocus();
			
		}
	}

	public void signUp(View view) {
		Intent i = new Intent(Login.this, SignUp.class);
		startActivity(i);

	}

	private class Tokken extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			String URL = Helper.URL_SESSION;
			String URl1 = Helper.URL_USER;
			List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
			String token = null;
			// email method
			if (Helper.isValidEmail(params[0])) {
				requestParams.add(new BasicNameValuePair("email", params[0]
						.trim()));
			} else {
				requestParams.add(new BasicNameValuePair("username", params[0]
						.trim()));
			}

			requestParams.add(new BasicNameValuePair("password", params[1]
					.trim()));
			HttpClient c = new DefaultHttpClient();
			HttpPost p = new HttpPost(URL);

			p.setHeader("User-Agent", "Android-app");
			try {
				p.setEntity(new UrlEncodedFormEntity(requestParams, HTTP.UTF_8));

				HttpResponse response = c.execute(p);
				token = EntityUtils.toString(response.getEntity());
				Log.i("token", token);
				Log.i("login", "after getting token"+ token);
				if (response.getStatusLine().getStatusCode() == 200) {
					Log.i("login", "after getting token"+ token);
					HttpGet g = new HttpGet(URl1);
					g.setHeader("Authorization", "Token token =" + token);

					HttpResponse response1 = c.execute(g);
					Log.i("userinfo", EntityUtils.toString(response1.getEntity()));
                    //{"message":"Password/Token issue"} ya aa raha jab ka details ani chai thi
					if (response1.getStatusLine().getStatusCode() == 200) {

						// save user in the db

					}

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

			return token;
		}

		@Override
		protected void onPostExecute(String result) {

			/*Session s1 = new Session();
			s1._id = 1;
			s1.tokken = result;
			s1.save();*/

		}
	}

}
