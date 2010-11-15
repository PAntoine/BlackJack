package com.pantoine.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import android.util.Log;

public class BlackjackSelect extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		int	num_players = 0;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blackjackselect);
			
		SharedPreferences preferences = getSharedPreferences(this.getApplication().getPackageName(),MODE_PRIVATE);

		if ( (num_players = preferences.getInt("registered_players",0)) != 0)
		{
			Spinner spinner = (Spinner) findViewById(R.id.spinner);
			ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>( this, android.R.layout.simple_spinner_item);

			for (Integer count = 1; count < num_players+1; count++)
			{
				Log.v("BlackjackSelect","Spinner: player[" + count.toString() + "] = " + preferences.getString(count.toString(),""));
				adapter.add(preferences.getString(count.toString(),""));
			}

			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);

			spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		}

		Button play = (Button) findViewById(R.id.play);
		m_play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				// Start the game with the new user
				Intent i = new Intent(BlackjackSetup.this, Blackjack.class);
				i.putExtra("player_name",player_name);

				startActivity(i);
			}
		});

		m_delete = (Button) findViewById(R.id.remove_player);
		m_delete.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dealerCards();
			}
		});



	}

	public void onPause()
	{
		super.onPause();
		Log.v("BlackjackSelect","OnPause");
	}

	public void onResume()
	{
		super.onResume();
		Log.v("BlackjackSelect","OnResume");
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener 
	{
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
		{
		  Toast.makeText(parent.getContext(), "The planet is " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
		}

		public void onNothingSelected(AdapterView parent) 
		{ 
			// Do nothing.
		}
	}



}
