package model;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;


public class Error {
	 @SerializedName("username")
	public ArrayList<String> userName;
	@SerializedName("email")
	public ArrayList<String> email;
	
	 public Error(){
	    	
	    }


}