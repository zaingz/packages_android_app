package com.zaingz.packages;

import java.io.IOException;

import model.Session;
import model.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DashBoard extends FragmentActivity implements TabListener {
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
   
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBar actionBar;
    private MyAdapter1 myAdapater;
    ViewPager viewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_board);
		
		viewPager= (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		myAdapater=new MyAdapter1(this);
		mDrawerList.setAdapter(myAdapater);
		//mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,itemsInDrawer ));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		actionBar=getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.mycolor)));

		
		ActionBar.Tab tab1 = actionBar.newTab();
		actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.mycolor)));
		
		tab1.setText("Icon 1");
		tab1.setTabListener(this);
		
		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText("Icon 2");
		tab2.setTabListener(this);
		
		ActionBar.Tab tab3 = actionBar.newTab();
		tab3.setText("Icon 3");
		tab3.setTabListener(this);
	
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		actionBar.addTab(tab3);
		
		
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
        if(position==2){
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
			User user = User.getRandom();
			Log.i("token", session.tokken);
			d.setHeader("Authorization", "Token token =" + session.tokken );
			try {
				HttpResponse response = c.execute(d);
				if (response.getStatusLine().getStatusCode() == 204){
					session.delete();
					user.delete();
					
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

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
 
}
class MyAdapter extends FragmentPagerAdapter{
	
	public MyAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
	 Fragment fragment=null;
		switch(arg0){
		case 0:
			fragment = new Fragment1();
			break;
		case 1:
			fragment = new Fragment2();
			break;
		case 2:
			fragment = new Fragments3();
		}
		return fragment;
			
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	
}
class MyAdapter1 extends BaseAdapter{
	private Context context;
	User user = User.getRandom();
	String userName = user.username; 
	String[] itemsInDrawer={userName,"Home","Logout"};
	int[] images = {R.drawable.user,R.drawable.home,R.drawable.logout};
    MyAdapter1(Context context){
    	this.context= context;
	
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemsInDrawer.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemsInDrawer[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = null;
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row=inflater.inflate(R.layout.drawer_list_item, parent, false);
		}
		else{
			row=convertView;
		}
		TextView tittleTextView = (TextView) row.findViewById(R.id.text1);
		ImageView imageOfTittle = (ImageView) row.findViewById(R.id.imageViewOfDrawer);
		tittleTextView.setText(itemsInDrawer[position]);
		imageOfTittle.setImageResource(images[position]);
		return row;
	}
	
}