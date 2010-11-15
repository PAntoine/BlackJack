package com.pantoine.blackjack;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.util.Log;

public class BlackjackRoot extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	}

	public void onPause()
	{
		super.onPause();
		Log.v("BlackjackRoot","OnPause");
	}

	public void onResume()
	{
		super.onResume();

		SharedPreferences preferences = getSharedPreferences(this.getApplication().getPackageName(),MODE_PRIVATE);

		if (preferences.getInt("registered_players",0) == 0)
		{
			startActivity(new Intent(this,BlackjackSetup.class));
		}
		else
		{
			startActivity(new Intent(this,BlackjackSelect.class));
		}
	}
}
