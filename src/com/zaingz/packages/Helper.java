package com.zaingz.packages;

import java.util.ArrayList;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

public class Helper {

	public static final String URL_USER = "http://packages-api2.herokuapp.com/users/";
	public static final String URL_SESSION = "http://packages-api2.herokuapp.com/users/session";

	protected static boolean isValidEmail(String email) {
		if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() )
			return true;
		else
			return false;

	}

	protected static void showToast(Context con, String message) {

		Toast.makeText(con, message, Toast.LENGTH_SHORT).show();
	}

	protected static ArrayList<EditText> isTextFieldEmpty(EditText... editTexts) {
		ArrayList<EditText> retEdit = new ArrayList<EditText>();
		for (EditText et : editTexts) {

			if (et.getText().toString().length() < 1) {
				retEdit.add(et);
			}
		}
		return retEdit;
	}

}
