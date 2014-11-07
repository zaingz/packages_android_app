package com.zaingz.packages;

import model.User;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterForNavigationDrawer extends BaseAdapter {
	private Context context;
	User user = User.getRandom();
	String userName = user.username; 
	String[] itemsInDrawer={userName,"Home","Logout"};
	int[] images = {R.drawable.user,R.drawable.home,R.drawable.logout};
	AdapterForNavigationDrawer(Context context){
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
