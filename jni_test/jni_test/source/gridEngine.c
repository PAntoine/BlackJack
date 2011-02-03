/*-------------------------------------------------------------------------------------*
 *
 * name:  grid.c
 * proj:  Miniweb browser version 3
 *
 * desc:  
 *
 * auth:  Peter Antoine  
 * date:  26/01/11
 *
 *               Copyright (c) 2009 Miniweb Interactive.
 *                       All rights Reserved.
 *-------------------------------------------------------------------------------------*/

#include "gridEngine.h"

EPG_ITEM	channel_1[] = {	{"prog1",0,9},
							{"prog2",10,24},
							{"prog3",25,29},
							{"prog4",30,59},
							{"prog5",60,69},
							{"prog6",70,89},
							{"prog7",90,95}};

#define	NUM_PROGS ((sizeof(channel_1)/sizeof(channel_1[0])))

unsigned int	CalculateItemPos(GRID_DETAILS* details, EPG_ITEM* item, unsigned int* start_pos, unsigned int* size)
{
	unsigned int result = 0;

	if (item->start_time > details->start_time && item->start_time < details->end_time && details->end_time < item->end_time)
	{
		/* ends after end time */
		*start_pos	= (item->start_time - details->start_time) / details->size_seconds;
  		*size		= ((details->end_time - item->start_time) + details->size_seconds - 1) / details->size_seconds;
		result = 1;
	}
	else if (item->start_time <= details->start_time && item->end_time <= details->end_time && details->start_time < item->end_time)
	{
		/* starts before start time */
		*start_pos	= 0;
  		*size		= ((item->end_time - details->start_time) + details->size_seconds - 1) / details->size_seconds;
		result = 3;
	}
	else if (item->start_time > details->start_time && item->start_time < details->end_time)
	{
		/* between start and end */
		*start_pos	= (item->start_time - details->start_time) / details->size_seconds;
  		*size		= ((item->end_time - item->start_time) + details->size_seconds - 1) / details->size_seconds;
		result = 2;
	}
	return result;
}

unsigned int	GetGridRow(unsigned int channel, unsigned int start_time, unsigned int end_time, unsigned int interval_size, unsigned int max_items, GRID_ITEM* items)
{
	unsigned int	index = 0;
	unsigned int	object =  0;
	unsigned int	start;
	unsigned int	size;
	GRID_DETAILS	grid = {interval_size,start_time,end_time};

	while (index < max_items && object < NUM_PROGS)
	{
		if (CalculateItemPos(&grid,&channel_1[object++],&start,&size))
		{
			items[index].name = channel_1[object-1].name;
			items[index].id = object - 1;
			items[index].index = start;
			items[index].size = size;
			index++;
		}
		else if (index > 0)
		{
			/* this must be after the end_time - stop and save cycles */
			break;
		}
	}

	return index;
}

/* $Id$ */

