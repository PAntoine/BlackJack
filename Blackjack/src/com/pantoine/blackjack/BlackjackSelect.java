package com.pantoine.blackjack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.graphics.Color;

import android.util.Log;

public class BlackjackSelect extends Activity
{
	private String	m_player_name;
	private Button	m_play;
	private Button	m_remove;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		int	num_players = 0;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blackjackselect);
	
		m_player_name = "";

		Log.v("BlackjackSelect","My Context is " + this.getPackageName());

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

		m_play = (Button) findViewById(R.id.play);
		m_play.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				// Start the game with the new user
				Intent i = new Intent(BlackjackSelect.this, Blackjack.class);
				i.putExtra("player_name",m_player_name);

				startActivity(i);
			}
		});

		m_remove = (Button) findViewById(R.id.remove_player);
		m_remove.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});


		// The button to add a new player
		Button add = (Button) findViewById(R.id.add_player);
		add.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(BlackjackSelect.this,BlackjackSetup.class));
			}
		});

		// Check to see if there are players if, so make some of the buttons unclickable
		if (num_players == 0)
		{
			m_remove.setClickable(false);
			m_remove.setTextColor(Color.GRAY);
			m_play.setClickable(false);
			m_play.setTextColor(Color.GRAY);
		}
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

		// These buttons are always viewable
		m_remove.setClickable(true);
		m_remove.setTextColor(Color.BLACK);
		m_play.setClickable(true);
		m_play.setTextColor(Color.BLACK);
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener 
	{
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) 
		{
		 	Toast.makeText(parent.getContext(), "The planet is " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
			m_player_name = parent.getItemAtPosition(pos).toString();
		}

		public void onNothingSelected(AdapterView parent) 
		{ 
			// Do nothing.
		}
	}
}
