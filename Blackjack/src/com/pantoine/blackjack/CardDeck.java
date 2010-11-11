package com.pantoine.blackjack;
import android.os.Bundle;

public class CardDeck 
{
	private Card m_deck[];
	private int m_card_index;
	private int m_cards_dealt;
	
	public void CreateDeck ()
	{
		int first_card = R.drawable.clubs_01_75;
		int card = 0;

		final int value[] = {11,2,3,4,5,6,7,8,9,10,10,10,10};
		
		m_deck = new Card[52];

		for (int suit=0; suit< 4; suit++)
		{
			for (int rank=0;rank<13;rank++,card++)
			{
				// Set the value of the cards up.
				m_deck[card] = new Card();
				m_deck[card].SetCard(first_card+card,value[rank]);
				m_deck[card].SetNextPrev(card+1,card-1);
			}
		}
		
		m_deck[0].SetPrev(-1);
		m_deck[51].SetNext(-1);
		
		// force shuffle on next read.
		m_cards_dealt = 43;
	}
	
	public void Shuffle()
	{
		int swap_card;
		int index;
		
		for (index=0; index < 52; index++)
		{
			swap_card = (int)(Math.random() * 52);
			
			if (swap_card != index)
			{
				Remove(index);
				AddAfter(swap_card,index);
			}
		}
		
		for (index=0; index < 52; index++)
		{
			if (m_deck[index].GetPrev() == -1)
			{
				m_card_index = index;
				break;
			}
		}

		m_cards_dealt = 0;
	}
	
	public void Remove(int card)
	{
		if (m_deck[card].GetPrev() != -1)
		{
			m_deck[m_deck[card].GetPrev()].SetNext(m_deck[card].GetNext());
		}
		
		if (m_deck[card].GetNext() != -1)
		{
			m_deck[m_deck[card].GetNext()].SetPrev(m_deck[card].GetPrev());
		}
	}

	public void AddAfter(int card,int new_card)
	{
		if (m_deck[card].GetNext() != -1)
		{
			m_deck[m_deck[card].GetNext()].SetPrev(new_card);
		}
		
		m_deck[new_card].SetNextPrev(m_deck[card].GetNext(),card);
		m_deck[card].SetNext(new_card);
	}
		
	public int GetNextCard() 
	{ 
		int result = m_card_index;
		m_card_index = m_deck[m_card_index].GetNext();
		m_cards_dealt++;
		
		return result;
	}
	public void SaveState(Bundle state)
	{
		int[] prev_array = new int[52];
		int[] next_array = new int[52];
		
		for (int count=0;count<52;count++)
		{
			prev_array[count] = m_deck[count].GetPrev();
			next_array[count] = m_deck[count].GetNext();
		}
		
		state.putIntArray("prev_list",prev_array);
		state.putIntArray("next_array", next_array);
		state.putInt("card_index",m_card_index);
		state.putInt("cards_dealt",m_cards_dealt);
	}
	
	public void LoadState(Bundle state)
	{
		int[] prev_array = state.getIntArray("prev_list");
		int[] next_array = state.getIntArray("next_array");

		m_cards_dealt = state.getInt("cards_dealt");
		m_card_index = state.getInt("card_index");
		
		for (int count=0; count<52; count++)
		{
			m_deck[count].SetNextPrev(next_array[count],prev_array[count]);

		}
	}
	
	public int GetCardResource(int card){return m_deck[card].GetResource();}
	public boolean NeedShuffle() { return m_cards_dealt > 42?true:false; }
	public int GetCardValue(int card){return m_deck[card].GetValue();}
}
