package gtg.virus.gtpr.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import gtg.virus.gtpr.entities.User;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

public final class Utilities {
	
	// the global tag to access shared file folders
	public final static String GLOBAL_TAG = Utilities.class.getPackage().getName();
	private static final String TAG = GLOBAL_TAG;
	
	
	/**
	 * 
	 * @param context
	 * @return SharedPreferences with GLOBAL_TAG used as name
	 */
	public static SharedPreferences getSharedPref(Context context){
		SharedPreferences shared = context.getSharedPreferences(GLOBAL_TAG, Context.MODE_PRIVATE);
		return shared;
	}
	
	/**
	 * 
	 * @param context
	 * @return User class 
	 */
	public static User getUser(Context context){
		final String USER = "_user_tag";
		User user = null;
		SharedPreferences shared = getSharedPref(context);
		
		String str_user = shared.getString(USER, null);
		user = new Gson().fromJson(str_user, User.class);
		
		if(user == null){
			Log.i(TAG, "User is null");
		}else{
			Log.i(TAG, "User is not null");
		}
		
		return user;
	}
	/**
	 * Saves user
	 * @param context
	 * @param user
	 */
	public static void saveUser(Context context , User user){
		final String USER = "_user_tag";
		final SharedPreferences shared = getSharedPref(context);
		final SharedPreferences.Editor editor = shared.edit();
		Log.i(TAG, "Saving user");
		String str_user = user.toString();
		boolean c = editor.putString(USER, str_user).commit();
		Log.i(TAG, "User saved... " + c);
	}
	
	 public static void walkdir(File dir , HashMap<String , String> data) {
		    String pdfPattern = "[a-zA-Z0-9,.-_]*.(pdf|epub)";

		    File listFile[] = dir.listFiles();

		    if (listFile != null) {
		        for (int i = 0; i < listFile.length; i++) {

		            if (listFile[i].isDirectory()) {
		                walkdir(listFile[i] , data);
		            } else {
		              if (Pattern.matches(pdfPattern, listFile[i].getName())){
		                  // add to hashmap
		            	  data.put(listFile[i].getName(), listFile[i].getAbsolutePath());
		            	  Log.i(TAG, "Not Test " + listFile[i].getName()  +" " + listFile[i].getAbsolutePath());
		              }
		              //Log.i(TAG,Pattern.matches(pdfPattern, listFile[i].getName()) + " Name = " + listFile[i].getName());
		            }
		        }
		    }    
		}
	
	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public static boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
}
