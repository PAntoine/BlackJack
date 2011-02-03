//--------------------------------------------------------------------------------*
//                          HomeNet Project
//  _     _                      ______
// | |   | |                    |  ___ \       _
// | |__ | | ___  ____   ____   | |   | | ____| |_
// |  __)| |/ _ \|    \ / _  )  | |   | |/ _  )  _)
// | |   | | |_| | | | ( (/ /   | |   | ( (/ /| |__ 
// |_|   |_|\___/|_|_|_|\____)  |_|   |_|\____)\___)
//
// Name  : java_adapter
// Desc  : This file holds the java_adapter class that interfaces with the
//         jni classes.
//
// Author: peterantoine
// Date  : 02/02/2011 13:02:13
//--------------------------------------------------------------------------------*
//                     Copyright (c) 2010 AntoineComputers
//                            All rights Reserved.
//--------------------------------------------------------------------------------*/

package com.antoine.jni;

import com.antoine.jni.HNCP_JNI_GridItem;

public class java_adapter
{
	// communication functions
	
	// ------------------------------------------------------------
	// Java helper functions
	// The following functions convert the C structures to java
	// ------------------------------------------------------------
	public native HNCP_JNI_GridItem[] HNCP_JNI_getChannelGridRow(int channel, int start_time, int end_time, int grid_seconds);

	static
	{
		System.loadLibrary("java_adapter");
	}
}


