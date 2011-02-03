/*-------------------------------------------------------------------------------------*
 *
 * name:  grid.h
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
#ifndef  __GRID_H__
#define  __GRID_H__

typedef struct
{
	unsigned int	size_seconds;
	unsigned int	start_time;
	unsigned int	end_time;

} GRID_DETAILS;

typedef struct
{
	char*			name;
	unsigned int	start_time;
	unsigned int	end_time;

} EPG_ITEM;

typedef struct
{
	char*			name;
	unsigned int	id;
	unsigned int	index;
	unsigned int	size;

} GRID_ITEM;

unsigned int	GetGridRow(unsigned int channel, unsigned int start_time, unsigned int end_time, unsigned int interval_size, unsigned int max_items, GRID_ITEM* items);


#endif 

/* $Id$ */

