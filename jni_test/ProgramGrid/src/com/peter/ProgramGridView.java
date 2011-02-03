package com.peter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.Toast;

import android.util.Log;

import com.antoine.jni.*;

public class ProgramGridView extends View
{
	private Paint	paint;
	private Paint	box_paint;
	private String	text;
	private int		start_x = 0;
	private int		start_y = 0;
	private float	lastX;
	private float	lastY;
	private float	startX;
	private float	startY;
	private float	width;
	private float	height;
	private float	num_rows;
	private float	num_cols;
	private float	old_grid_x;
	private float	old_grid_y;
	private float	grid_offset_x;
	private float	grid_offset_y;
	final private	float unit_width = 40;
	final private	float half_unit_width = 20;
	final private	float unit_height = 20;
	final private	float half_unit_height = 10;

	Context	my_context;

	java_adapter		hello;
	HNCP_JNI_GridItem	item[];

	public ProgramGridView(Context context) 
	{ 
		super(context);

		my_context = context;

		paint = new Paint();
		// set's the paint's colour
		paint.setColor(Color.GREEN);
		// set's paint's text size
		paint.setTextSize(10);
		// smooth's out the edges of what is being drawn
		paint.setAntiAlias(true);

		hello = new java_adapter();

		if (item == null)
			Log.v("BLAG","the item is null");
		else
			Log.v("BLAG","the item returned " + item.length);
	} 
      
	protected void  onSizeChanged  (int w, int h, int oldw, int oldh)
	{ 
		width = w;
		height = h;
		num_rows = h/unit_height;
		num_cols = w/unit_width;
	}

	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		RectF	box = new RectF();
		 
		item = hello.HNCP_JNI_getChannelGridRow(1,(start_x*5),(start_x*5)+90,5);

		Log.v("BLAG","offset" + Integer.toString((int)grid_offset_x) + " start: " + Integer.toString(start_x));

		if (item != null)
		{
			for (int count=0; count < item.length; count++)
			{
				box.top = grid_offset_y - (unit_height * start_y);
				box.left = (item[count].getIndex() * unit_width) + grid_offset_x + 1;
				box.bottom = box.top + unit_height;
				box.right = box.left + (unit_width * item[count].getSize());

				paint.setColor(Color.GRAY);
				canvas.drawRoundRect(box,3,3,paint);
				
				if (item[count].getName() != null)
				{
					paint.setColor(Color.BLUE);
					canvas.drawText(item[count].getName(),box.left,box.bottom, paint);
				}
			}
		}

		for (float row=0;row<num_rows+2;row++)
		{
			paint.setColor(Color.YELLOW);
			text = Integer.toString(start_y + (int)row);
			canvas.drawText(text, 1,((row)*unit_height)+grid_offset_y, paint);
		
			paint.setColor(Color.GRAY);
			canvas.drawLine((float)0,(row*unit_height)+grid_offset_y,width,(row*unit_height)+grid_offset_y,paint);
		}

		for (float col=0;col<num_cols+1;col++)
		{
			paint.setColor(Color.YELLOW);
			text = Integer.toString(start_x + (int)col);
			canvas.drawText(text, ((col)*unit_width)+grid_offset_x,10, paint);
			
			paint.setColor(Color.GRAY);
			canvas.drawLine((col*unit_width)+grid_offset_x,(float)0,(col*unit_width)+grid_offset_x,height,paint);
		}

	}

	public boolean onTouchEvent(MotionEvent event) 
	{
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN: 
			{
				startY = event.getY();
				startX = event.getX();
				lastY = event.getY();
				lastX = event.getX();
			}
			break;

			case MotionEvent.ACTION_MOVE:
			{
				/* handle movement in X */
				float	change_x = event.getX() - lastX;
				float	direction_x = ( change_x / ((float) Math.abs(change_x)));
				int		change_size_x = (int)(Math.abs((change_x / unit_width)));

				grid_offset_x = (grid_offset_x + change_x + unit_width) % unit_width;

				if ((change_size_x > 0) || (direction_x < 0 && old_grid_x < grid_offset_x) || (direction_x > 0 && old_grid_x > grid_offset_x))
				{
					/* ok, we have crossed a line */
					start_x += 0 - (direction_x + (change_size_x * direction_x));
				}

				old_grid_x = grid_offset_x;

				/* handle movement in Y */
				float	change_y = event.getY() - lastY;
				float	direction_y = ( change_y / ((float) Math.abs(change_y)));
				int		change_size_y = (int)(Math.abs((change_y / unit_height)));

				grid_offset_y = (grid_offset_y + change_y + unit_height) % unit_height;

				if ((change_size_y > 0) || (direction_y < 0 && old_grid_y < grid_offset_y) || (direction_y > 0 && old_grid_y > grid_offset_y))
				{
					/* ok, we have crossed a line */
					start_y += 0 - (direction_y + (change_size_y * direction_y));
				}

				old_grid_y = grid_offset_y;

				/* remember the last move */
				lastY = event.getY();
				lastX = event.getX();
			}
			break;

			case MotionEvent.ACTION_UP:
			{
				/* round it */
				if ((event.getEventTime() - event.getDownTime()) < 300)
				{
					RectF	box = new RectF();
					
					for (int count=0; count < item.length; count++)
					{
						box.top = grid_offset_y - (unit_height * start_y);
						box.left = (item[count].getIndex() * unit_width) + grid_offset_x + 1;
						box.bottom = box.top + unit_height;
						box.right = box.left + (unit_width * item[count].getSize());

						if (box.contains(event.getX(),event.getY()))
						{
							Toast msg = Toast.makeText(my_context, item[count].getName(), Toast.LENGTH_SHORT);
							msg.show();
							break;
						}
					}
				}
			}
			break;
		}
	
		invalidate();
	
		return true;
	}
}

