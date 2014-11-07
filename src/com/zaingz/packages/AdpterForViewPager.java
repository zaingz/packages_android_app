package com.zaingz.packages;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AdpterForViewPager extends FragmentPagerAdapter {
	public AdpterForViewPager(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
	 Fragment fragment=null;
		switch(arg0){
		case 0:
			fragment = new NearByPackages();
			break;
		case 1:
			fragment = new UserPackages();
			break;
		case 2:
			fragment = new Notification();
			break;
		
		}
		return fragment;
			
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	
}


