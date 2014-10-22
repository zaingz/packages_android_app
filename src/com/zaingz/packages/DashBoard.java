package com.zaingz.packages;

import java.io.IOException;

import model.Session;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DashBoard extends Activity {
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String[] itemsInDrawer={"Home","Logout"};
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_board);
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,itemsInDrawer ));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		 
		

	       
	        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  
	                mDrawerLayout,         
	                R.drawable.ic_drawer,  
	                R.string.drawer_open, 
	                R.string.drawer_close 
	                ) {
	            public void onDrawerClosed(View view) {
	                getActionBar().setTitle(mTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }

	            public void onDrawerOpened(View drawerView) {
	                getActionBar().setTitle(mDrawerTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);

	        getActionBar().setDisplayHomeAsUpEnabled(true);
		     getActionBar().setHomeButtonEnabled(true);
	}
	 @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        mDrawerToggle.syncState();
	    }
	   @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	         
	        if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	   }
	 
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        if(position==1){
        	mDrawerList.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mDrawerList);
            new Logout().execute("");
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    private class Logout extends AsyncTask<String, Void , String>{
    	 ProgressDialog pd;
		  protected void onPreExecute() {
	          // NOTE: You can call UI Element here.
	    	    pd = new ProgressDialog(DashBoard.this);
				pd.setMessage("Siging out...");
				pd.show();
	          
	      }

		@Override
		protected String doInBackground(String... params) {
			String URL = Helper.URL_SESSION;
			HttpClient c = new DefaultHttpClient();
			HttpDelete d = new HttpDelete(URL);
			Session session = Session.getRandom(); 
			Log.i("token", session.tokken);
			d.setHeader("Authorization", "Token token =" + session.tokken );
			try {
				HttpResponse response = c.execute(d);
				if (response.getStatusLine().getStatusCode() == 204){
					session.delete();
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			pd.dismiss();
			Intent i = new Intent(DashBoard.this ,Login.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(i);
			
		}
    

	}
 
}