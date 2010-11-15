package com.pantoine.blackjack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.util.Log;

public class Blackjack extends Activity
{
	CardDeck	m_deck;
	TextView	m_user_text;
	TextView	m_dealer_text;
	ImageView	m_image[];
	ImageView	m_dealer_image[];
	AlertDialog	m_win;
	AlertDialog	m_lose;
	Button		m_twist;
	Button		m_stick;
	int			m_num_dealt;
	Integer		m_hand_value;
	Integer		m_dealer_value;
	int			m_user_hand[];
	boolean		m_player_bust;
	boolean		m_in_lose;
	boolean		m_in_win;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.v("Blackjack","On Create");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		m_player_bust = false;
		m_in_win = false;
		m_in_lose = false;
		m_hand_value = 0;
		m_dealer_value = 0;
		m_user_hand = new int[5];

		m_user_text = (TextView) findViewById(R.id.TextView01);
		m_user_text.setTextColor(Color.GREEN);
		m_dealer_text = (TextView) findViewById(R.id.TextView02);

		m_image = new ImageView[5];
		m_image[0] = (ImageView) findViewById(R.id.card_1);
		m_image[1] = (ImageView) findViewById(R.id.card_2);
		m_image[2] = (ImageView) findViewById(R.id.card_3);
		m_image[3] = (ImageView) findViewById(R.id.card_4);
		m_image[4] = (ImageView) findViewById(R.id.card_5);

		m_dealer_image = new ImageView[5];
		m_dealer_image[0] = (ImageView) findViewById(R.id.dealer_card_1);
		m_dealer_image[1] = (ImageView) findViewById(R.id.dealer_card_2);
		m_dealer_image[2] = (ImageView) findViewById(R.id.dealer_card_3);
		m_dealer_image[3] = (ImageView) findViewById(R.id.dealer_card_4);
		m_dealer_image[4] = (ImageView) findViewById(R.id.dealer_card_5);

		m_twist = (Button) findViewById(R.id.twist);
		m_twist.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dealCard();
			}
		});

		m_stick = (Button) findViewById(R.id.stick);
		m_stick.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dealerCards();
			}
		});

		final Button redeal = (Button) findViewById(R.id.redeal);
		redeal.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				reDeal();
			}
		});

		m_num_dealt = 0;

		m_user_text.setText("[Player] 0");
		m_dealer_text.setText("[Dealer] 0");
		
		// reset the state of the buttons
		m_twist.setClickable(false);
		m_twist.setTextColor(Color.GRAY);
		m_stick.setClickable(false);
		m_stick.setTextColor(Color.GRAY);
	
		m_deck = new CardDeck();
		m_deck.CreateDeck();

		if (savedInstanceState == null)
		{
		}
		else
		{
			Log.v("Backjack","ReCreate");
			LoadState(savedInstanceState);
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();

		Log.v("Blackjack","On Start called");
	}
	
	public void reDeal()
	{
		if (m_deck.NeedShuffle())
		{
			m_deck.Shuffle();
		}

		m_player_bust = false;
		m_num_dealt = 0;
		dealCard();
		dealCard();

		// reset the state of the buttons
		m_twist.setClickable(true);
		m_twist.setTextColor(Color.BLACK);
		m_stick.setClickable(true);
		m_stick.setTextColor(Color.BLACK);
		
		// reset the user text colour
		m_user_text.setTextColor(Color.GREEN);
		m_dealer_text.setTextColor(Color.WHITE);
		
		// reset other cards
		m_image[2].setImageResource(R.drawable.back_blue);
		m_image[3].setImageResource(R.drawable.back_blue);
		m_image[4].setImageResource(R.drawable.back_blue);

		// reset other cards
		m_dealer_image[0].setImageResource(R.drawable.back_blue);
		m_dealer_image[1].setImageResource(R.drawable.back_blue);
		m_dealer_image[2].setImageResource(R.drawable.back_blue);
		m_dealer_image[3].setImageResource(R.drawable.back_blue);
		m_dealer_image[4].setImageResource(R.drawable.back_blue);

		// clear dealer text
		m_dealer_text.setText("[Dealer] 0");
	}

	public void onPause()
	{
		super.onPause();
		Log.v("Blackjack","onPause() called");
	}

	public void onResume()
	{
		super.onResume();

		Log.v("Blackjack","onResume() called");
	}

	public void onStop()
	{
		super.onStop();
		Log.v("Blackjack","onStop() called");
	}

	public void onDestroy()
	{
		super.onDestroy();
		Log.v("Blackjack","onDestroy() called");
	}

	public void dealCard()
	{
		int num_aces = 0;
		m_hand_value = 0;

		if (m_num_dealt < 5 && !m_player_bust)
		{
			// Get the next card
			m_user_hand[m_num_dealt] = m_deck.GetNextCard();
			m_image[m_num_dealt].setImageResource(m_deck.GetCardResource(m_user_hand[m_num_dealt]));
			m_num_dealt++;

			// calculate the value of the hand.
			for (int count=0;count<m_num_dealt;count++)
			{
				if (m_deck.GetCardValue(m_user_hand[count]) == 11)
				{
					num_aces++;
				}
				
				m_hand_value += m_deck.GetCardValue(m_user_hand[count]);
			}
			
			// ACE adjust the value
			while (m_hand_value > 21 && num_aces > 0)
			{
				m_hand_value -= 10;
				num_aces--;
			}
	 
			// Check for losery-ness.
			if (m_hand_value > 21)
			{
				// Loser :(
				m_twist.setClickable(false);
				m_twist.setTextColor(Color.GRAY);
				
				m_stick.setClickable(false);
				m_stick.setTextColor(Color.GRAY);
				
				m_user_text.setTextColor(Color.RED);
				m_player_bust = true;
				
				ShowLose();
			}
			
			String output = "[Player] " + m_hand_value.toString();
			m_user_text.setText(output);
		}
	}
	
	public void dealerCards()
	{
		int		card;
		int		num_aces = 0;
		int		dealer_cards = 0;
		
		m_dealer_value = 0;

		m_dealer_text.setTextColor(Color.GREEN);
		
		m_twist.setClickable(false);
		m_twist.setTextColor(Color.GRAY);
		
		m_stick.setClickable(false);
		m_stick.setTextColor(Color.GRAY);
		
		while (m_dealer_value <= m_hand_value && dealer_cards < 5)
		{
			card = m_deck.GetNextCard();
			m_dealer_image[dealer_cards].setImageResource(m_deck.GetCardResource(card));
			dealer_cards++;
			
			if (m_deck.GetCardValue(card) == 11)
			{
				num_aces++;
			}
			
			// calculate the value of the dealers cards
			m_dealer_value += m_deck.GetCardValue(card);
			
			while (m_dealer_value > 21 && num_aces > 0)
			{
				m_dealer_value -= 10;
				num_aces--;
			}

			// display result
			String output = "[Dealer] " + m_dealer_value.toString();
			m_dealer_text.setText(output);
		}
		
		if (m_dealer_value > m_hand_value && m_dealer_value <= 21)
		{
			ShowLose();
		}
		else
		{
			if (m_dealer_value > 21)
			{
				m_dealer_text.setTextColor(Color.RED);
			}
			ShowWin();
		}
	}
	
	void ShowLose()
	{
		m_in_lose = true;
		m_user_text.setTextColor(Color.YELLOW);
		
		m_lose = new AlertDialog.Builder(this)
		.setTitle("Loser!!!")
		.setMessage("You were trounced by the dealer :)")
		.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {m_in_lose = false;}
		}).show();
	}
	
	void ShowWin()
	{
		m_in_win = true;
		m_win = new AlertDialog.Builder(this)
		.setTitle("Winner!!")
		.setMessage("You beat the dealer!")
		.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {m_in_win = false;}
		}).show();
	}
	
	@Override
	public void onSaveInstanceState(Bundle state)
	{
		state.putInt("dealer_value", m_dealer_value);
		state.putInt("player_value",m_hand_value);
		state.putInt("num_cards", m_num_dealt);
  	
		state.putBoolean("bust",m_player_bust);
		state.putBoolean("lose",m_in_lose);
		state.putBoolean("win",m_in_win);
  	
		if (m_in_win && m_win != null)
		{
			m_win.dismiss();
		}

		if (m_in_lose && m_lose != null)
		{
			m_lose.dismiss();
		}

		if (m_user_hand != null)
		{
			state.putIntArray("player_hand",m_user_hand);
		}
  	
		m_deck.SaveState(state);

		super.onSaveInstanceState(state);
	}
	
	public void LoadState(Bundle state)
	{
		
		m_dealer_value	= state.getInt("dealer_value");
		m_hand_value	= state.getInt("player_value");
		m_num_dealt		= state.getInt("num_cards");
  	
		m_player_bust	= state.getBoolean("bust");
		m_in_lose		= state.getBoolean("lose");
		m_in_win		= state.getBoolean("win");
  	
		m_user_hand = state.getIntArray("player_hand");

		m_deck.LoadState(state);

		// display the users cards.
		for (int count=0; count < m_num_dealt; count++)
		{
			m_image[count].setImageResource(m_deck.GetCardResource(m_user_hand[count]));
		}
	
		// Set the text values
		m_user_text.setTextColor(Color.GREEN);
		String output = "[Player] " + m_hand_value.toString();
		m_user_text.setText(output);
	
		output = "[Dealer] " + m_dealer_value.toString();
		m_dealer_text.setText(output);

		if (m_in_win)
		{
			ShowWin();
		}
		else if (m_in_lose)
		{
			ShowLose();
		}
		else
		{
			// we are in a player modea
			m_twist.setClickable(true);
			m_twist.setTextColor(Color.BLACK);
			m_stick.setClickable(true);
			m_stick.setTextColor(Color.BLACK);
		}
	}
}
