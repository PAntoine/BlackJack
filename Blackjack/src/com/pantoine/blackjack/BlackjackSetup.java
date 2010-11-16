package com.pantoine.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.widget.Toast;
import android.widget.EditText;

import android.util.Log;

public class BlackjackSetup extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blackjacksetup);

		final EditText edittext = (EditText) findViewById(R.id.edittext);
		
		if (edittext == null)
		{
			Log.v("BlackjackSetup","did not find the edittext resource" + edittext.getClass().getName());
		}
		
		edittext.setOnKeyListener(new OnKeyListener() 
			{
				public boolean onKey(View v, int keyCode, KeyEvent event) 
				{
					// If the event is a key-down event on the "enter" button
					if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) 
					{
						int	num_players = 0;
						SharedPreferences preferences = getSharedPreferences(BlackjackSetup.this.getApplication().getPackageName(),MODE_PRIVATE);
						num_players = preferences.getInt("registered_players",0);

						String player_name = edittext.getText().toString();

						// find if the players name is in the resources
						Integer count = 0;

						for (count = 0; count < num_players; count++)
						{
							if (player_name.compareTo(preferences.getString(count.toString(),"")) == 0)
							{
								break;
							}
						}

						// test if player not found
						if (count == num_players)
						{
							// is a real new user and not one that already exists
							SharedPreferences.Editor editor = preferences.edit();

							editor.putInt("registered_players",num_players + 1);
							editor.putString(Integer.toString(num_players + 1),player_name);
							editor.commit();

							Log.v("Blackjack","Adding player[" + (num_players + 1) + "] = " + player_name);

							// Start the game with the new user
							Intent i = new Intent(BlackjackSetup.this, Blackjack.class);
							i.putExtra("player_name",player_name);
						
							startActivity(i);
						}
						else
						{
							// Set the error text and have the user try again
							Toast.makeText(BlackjackSetup.this,"This user already exists.",Toast.LENGTH_SHORT).show();
						}

						return true;
					}
					return false;
				}
			}
		);
	}

	public void onPause()
	{
		super.onPause();
		Log.v("BlackjackSetup","OnPause");
	}

	public void onResume()
	{
		super.onResume();
		Log.v("BlackjackSetup","OnResume");
	}
}
