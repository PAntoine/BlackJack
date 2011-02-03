/*--------------------------------------------------------------------------------*
 *                          HomeNet Project
 *  _     _                      ______
 * | |   | |                    |  ___ \       _
 * | |__ | | ___  ____   ____   | |   | | ____| |_
 * |  __)| |/ _ \|    \ / _  )  | |   | |/ _  )  _)
 * | |   | | |_| | | | ( (/ /   | |   | ( (/ /| |__ 
 * |_|   |_|\___/|_|_|_|\____)  |_|   |_|\____)\___)
 *
 * Name  : java_adapter
 * Desc  : This function holds the JNI functions for the homenet java_adapter.
 *
 * Author: peterantoine
 * Date  : 02/02/2011 13:50:57
 *--------------------------------------------------------------------------------*
 *                     Copyright (c) 2010 AntoineComputers
 *                            All rights Reserved.
 *--------------------------------------------------------------------------------*/
#include <stdlib.h>
#include "java_adapter.h"
#include "com_antoine_jni_java_adapter.h"

/*----- FUNCTION -----------------------------------------------------------------*
 * Name : Java_com_antoine_jni_java_1adapter_HNCP_1JNI_1getChannelGridRow
 * Desc : This function will return a channel.
 *--------------------------------------------------------------------------------*/
JNIEXPORT jobjectArray JNICALL Java_com_antoine_jni_java_1adapter_HNCP_1JNI_1getChannelGridRow (JNIEnv *env, jobject defaultObj, jint channel, jint start_time, jint end_time, jint seconds)
{
	jobjectArray	result = NULL;
	jcharArray		string;
	jclass			item_class;
	jmethodID		constructor;
	jint			count;
	jobject			new_item;
	GRID_ITEM		grid[10];
	unsigned int	list_size;

	printf("here\n");


	item_class = (*env)->FindClass(env,"com/antoine/jni/HNCP_JNI_GridItem");

	if (item_class != NULL)
	{
		constructor = (*env)->GetMethodID(env,item_class,"<init>","(Ljava/lang/String;III)V");

		if (constructor != NULL)
		{
			list_size = GetGridRow(channel,start_time,end_time,seconds,10,grid);

			result = (*env)->NewObjectArray(env,list_size,item_class,NULL);

			if (result != NULL)
			{
				for (count=0;count<list_size;count++)
				{
					string = (*env)->NewStringUTF(env,grid[count].name);
					new_item = (*env)->NewObject(env, item_class, constructor, string, grid[count].id, grid[count].index, grid[count].size);

					(*env)->SetObjectArrayElement(env,result,count,new_item);
					(*env)->DeleteLocalRef(env,string);
				}
			}
		}
	}

	return result;
}

