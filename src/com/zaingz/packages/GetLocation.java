package com.zaingz.packages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.GPSTracker;
import model.Session;
import model.User;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class GetLocation extends Service {
	String stringLatitude ;
	String stringLongitude;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		  final GPSTracker gpsTracker = new GPSTracker(this);
		  Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
					
					
						stringLatitude = String.valueOf(gpsTracker.latitude);
						stringLongitude = String.valueOf(gpsTracker.longitude);
						Log.i("lat",""+ stringLatitude);
						
						User user = User.getRandom();
						Log.i("id",""+ user.id);
						String URL = Helper.URL_USER+user.id;
					
						HttpClient c = new DefaultHttpClient();
						HttpPatch patch = new HttpPatch(URL);
						Session session = Session.getRandom();
						
						Log.i("token", session.tokken);
						patch.setHeader("Authorization", "Token token =" + session.tokken );
						List<NameValuePair> params = new ArrayList<NameValuePair>(2);
						params.add(new BasicNameValuePair("user[lat]", stringLatitude.trim()));
						params.add(new BasicNameValuePair("user[lon]", stringLongitude.trim()));
						
						try {
							
							patch.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
							HttpResponse response = c.execute(patch);
							Log.d("Http Response:", response.toString());
							if (response.getStatusLine().getStatusCode() == 202){
								Log.i("lat",""+ stringLatitude);
								user.lat=stringLatitude;
								user.lon=stringLongitude;
								Log.i("lon",""+ user.lon);
								
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				
			}
		});
		
		t.start();
    
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	

}
