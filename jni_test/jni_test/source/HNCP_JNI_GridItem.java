//--------------------------------------------------------------------------------*
//                          HomeNet Project
//  _     _                      ______
// | |   | |                    |  ___ \       _
// | |__ | | ___  ____   ____   | |   | | ____| |_
// |  __)| |/ _ \|    \ / _  )  | |   | |/ _  )  _)
// | |   | | |_| | | | ( (/ /   | |   | ( (/ /| |__ 
// |_|   |_|\___/|_|_|_|\____)  |_|   |_|\____)\___)
//
// Name  : HNCP_JNI_GridItem
// Desc  : This is a simple class the holds the the GridItem information.
//
// Author: peterantoine
// Date  : 02/02/2011 20:16:36
//--------------------------------------------------------------------------------*
//                     Copyright (c) 2010 AntoineComputers
//                            All rights Reserved.
//--------------------------------------------------------------------------------*/

package com.antoine.jni;

/**
 * HNCP_HNI_GridItem
 * This class defines a grid item.
 */ 
public class HNCP_JNI_GridItem
{
	String	m_name;
	int		m_id;
	int		m_index;
	int		m_size;

	public HNCP_JNI_GridItem(String name, int id, int index, int size)
	{
		m_name = name;
		m_id = id;
		m_index = index;
		m_size = size;
	}

	public int getIndex()
	{
		return m_index;
	}

	public int getSize()
	{
		return m_size;
	}

	public String getName()
	{
		return m_name;
	}
}


