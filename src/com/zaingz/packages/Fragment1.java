package com.zaingz.packages;



import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;


public class Fragment1 extends android.support.v4.app.Fragment {
	 ListView lv ;
	 FloatingActionButton fabButton;
	 
	 
		

	public Fragment1() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_icon1, container, false);
		 fabButton = new FloatingActionButton.Builder(this.getActivity()) 
		    .withDrawable(getResources().getDrawable(R.drawable.ic_drawer)) 
		    .withButtonColor(Color.WHITE) 
		    .withGravity(Gravity.BOTTOM | Gravity.RIGHT) 
		    .withMargins(0, 0, 16, 16) 
		    .create();
		 
		lv=(ListView) view.findViewById(R.id.list1);
		
		lv.setOnScrollListener(new OnScrollListener() {
			int mLastFirstVisibleItem;
			boolean mIsScrollingUp;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(scrollState==0){
					Log.i("scroll", "scrolling stop");
					}
				if(view.getId() == lv.getId()){
					final int currentFirstVisibleItem = lv.getFirstVisiblePosition();
					if(currentFirstVisibleItem > mLastFirstVisibleItem){
						mIsScrollingUp=false;
						Log.i("scroll", "scrolling down");
						fabButton.hideFloatingActionButton();
					}
					else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
						mIsScrollingUp=true;
						fabButton.showFloatingActionButton();
						Log.i("scroll", "scrolling up");
					}
					mLastFirstVisibleItem=currentFirstVisibleItem;
				}
				
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			    
		    }		
			
				
			
		});
		
		return view;
		
	}

}
