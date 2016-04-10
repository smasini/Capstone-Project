package smasini.it.thetvdb.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ServerTime {

	private final String SERVER_TIME_KEY = "thetvdb_server_previous_time";
	private String previousTime;
	
	public ServerTime(Context context) {
		readPreviousTime(context);
	}

	public void readPreviousTime(Context context){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		this.previousTime = sharedPreferences.getString(SERVER_TIME_KEY, "");
	}
	
	public void storePreviousTime(Context context){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPreferences.edit()
				.putString(SERVER_TIME_KEY, previousTime)
				.apply();
	}
	
	public String getPreviousTime() {
		return previousTime;
	}

	public void setPreviousTime(String previousTime, Context context) {
		this.previousTime = previousTime;
		storePreviousTime(context);
	}
}
