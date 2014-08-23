package gtg.virus.gtpr.utils;

import com.google.gson.Gson;

import gtg.virus.gtpr.entities.User;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public final class Utilities {
	
	// the global tag to access shared file folders
	public final static String GLOBAL_TAG = Utilities.class.getPackage().getName();
	
	
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
		if(shared != null){
			String str_user = shared.getString(USER, null);
			user = new Gson().fromJson(str_user, User.class);
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
		
		String str_user = user.toString();
		editor.putString(USER, str_user).commit();
		
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
