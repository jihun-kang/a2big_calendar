package com.a2big.calendar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

//import com.a2big.dragonhouse.models.BookingItems;

import com.a2big.booking.BookingItems;
import com.a2big.booking.listener.OnDataSelectionListener;

import java.util.ArrayList;

public class CalendarMonthView extends GridView {

	private OnDataSelectionListener selectionListener;
	
	CalendarMonthAdapter adapter;
	ArrayList<BookingItems> mBookingData;

	public CalendarMonthView(Context context) {
		super(context);
		init();
	}
	
	public CalendarMonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		setBackgroundColor(Color.WHITE);
		//setVerticalSpacing(1);
		//setHorizontalSpacing(1);
		setVerticalSpacing(0);
		setHorizontalSpacing(0);
		setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		
		setNumColumns(7);
		
		setOnItemClickListener(new OnItemClickAdapter());
	}

	public void setData(ArrayList<BookingItems> d){
		mBookingData = d;
	}

	public void setAdapter(BaseAdapter adapter){
		super.setAdapter(adapter);
		this.adapter = (CalendarMonthAdapter)adapter;
		if(mBookingData!=null){
			this.adapter.setData(mBookingData);
		}
	}
	
	public BaseAdapter getAdapter(){
		return (BaseAdapter)super.getAdapter();
	}
	
	public void setOnDataSelectionListener(OnDataSelectionListener listener){
		this.selectionListener= listener;
	}
	
	public OnDataSelectionListener getOnDataSelectionListener(){
		return selectionListener;
	}
	
	class OnItemClickAdapter implements OnItemClickListener{

		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			if(adapter!=null){
				adapter.setSelectedPosition(position);
				adapter.notifyDataSetInvalidated();
			}
			
			if(selectionListener != null){
				selectionListener.onDataSelected(parent, v, position, id);
			}
		}
		
	}

}
