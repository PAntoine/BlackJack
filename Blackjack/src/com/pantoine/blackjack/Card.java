package com.pantoine.blackjack;

public class Card 
{
	public void SetCard(int resource, int value)
	{
		m_resource = resource; 
		m_value = value;
	}
	
	public int GetValue()
	{
		return m_value;
	}
	
	public int GetResource()
	{
		return m_resource;
	}
	
	public void SetNextPrev(int next, int prev)
	{
		m_next = next;
		m_prev = prev;
	}
	
	public int GetNext()
	{
		return m_next;
	}
	
	public int GetPrev()
	{
		return m_prev;
	}
	
	public void SetNext(int next)
	{
		m_next = next;
	}
	
	public void SetPrev(int prev)
	{
		m_prev = prev;
	}
	
	private int m_resource;
	private int m_value;
	private int m_next;
	private int m_prev;
}
