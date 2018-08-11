package com.a2big.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.a2big.booking.BookingItems;
import com.a2big.booking.BookingObject;
import com.a2big.testcalendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarMonthAdapterTest extends BaseAdapter{

	public static final String TAG="CalendarMonthAdapter";
	Context mContext;
	public static int oddColor = Color.rgb(225, 225, 225);
	public static int headColor = Color.rgb(12, 32, 158);
	private int selectedPosition =-1;
	private MonthItem[] items;
	private int countColumn=7;

	int mStartDay, startDay, curYear, curMonth, firstDay, lastDay;
	Calendar mCalendar;
	boolean recreateItems = false;
	ArrayList<BookingItems> mBookingData;
	//ArrayList<String> mSelectDay = new ArrayList<>();
	List<String> mSelectDay = new ArrayList<>();

	public CalendarMonthAdapterTest(Context context) {
		super();
		mContext = context;
		init();

		/*
		mSelectDay.add("2018-08-02");
		mSelectDay.add("2018-08-03");
		mSelectDay.add("2018-08-04");

		mSelectDay.add("2018-09-04");
		mSelectDay.add("2018-09-04");
		mSelectDay.add("2018-09-05");
		*/
	}

	public CalendarMonthAdapterTest(Context context, Calendar calendar) {
		mContext = context;
		mCalendar = calendar;
		items = new MonthItem[7*6];
		recalculate();
		//resetDayNumbers();

		setNextMonth();
	}

	public void setSelectDay(List<String> s){
		mSelectDay = s;
	}

	public void clearSelectDay(){
		mSelectDay.clear();
	}
	public Calendar getCalendar(){
		return mCalendar;
	}

	private void init(){
		items = new MonthItem[7*6];
		mCalendar = Calendar.getInstance();
		recalculate();
		resetDayNumbers();
	
	}
	
	public void setData(ArrayList<BookingItems> d)
	{
		mBookingData = d;
	}

	private void recalculate() {
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		
		int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
		firstDay = getFirstDay(dayOfWeek);
		
		mStartDay = mCalendar.getFirstDayOfWeek();
		curYear = mCalendar.get(Calendar.YEAR);
		curMonth = mCalendar.get(Calendar.MONTH);
		lastDay = getMonthLastDay(curYear,curMonth);
		
		int diff = mStartDay - Calendar.SUNDAY-1;
		startDay = getFirstDayOfWeek();

		Log.e("JH", curYear+" "+curMonth);
	}

	public void setPreviousMonth(){
		mCalendar.add(Calendar.MONTH, -1);
		recalculate();
		
		resetDayNumbers();
		selectedPosition=-1;
	}

	//public void setDate(int year, int month){
	//	mCalendar.add(Calendar.YEAR, 5); // Add 5 years to current year
	//	mCalendar.add(Calendar.MONTH, 5); // Add 5 months to current month
	//}

	public void setNextMonth(){
		mCalendar.add(Calendar.MONTH, 1);
		recalculate();
		
		resetDayNumbers();
		selectedPosition=-1;
	}
	
	public void resetDayNumbers() {
		for(int i=0; i<42; i++){
			int dayNumber = (i+1)-firstDay;
			if(dayNumber<1 || dayNumber>lastDay){
				dayNumber=0;
			}
			items[i] = new MonthItem(dayNumber);
		}
	}
	
	private int getFirstDay(int dayOfWeek) {
		int result=0;
		if(dayOfWeek == Calendar.SUNDAY)
			result=0;
		else if(dayOfWeek == Calendar.MONDAY)
			result=1;
		else if(dayOfWeek == Calendar.TUESDAY)
			result=2;
		else if(dayOfWeek == Calendar.WEDNESDAY)
			result=3;
		else if(dayOfWeek == Calendar.THURSDAY)
			result=4;
		else if(dayOfWeek == Calendar.FRIDAY)
			result=5;
		else if(dayOfWeek == Calendar.SATURDAY)
			result=6;
		return result;
	}



	public View getView(int position, View convertView, ViewGroup parent) {
		MonthItemView itemView;
		if(convertView ==null)
			itemView = new MonthItemView(mContext);
		else
			itemView = (MonthItemView) convertView;
		GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,140);

		int rowIndex = position / countColumn;
		int columnIndex = position % countColumn;

		itemView.setItem(items[position]);
		itemView.setLayoutParams(params);
		itemView.setPadding(2, 2, 2, 2);
		itemView.setGravity(Gravity.LEFT);
		
		if(columnIndex==0)
			itemView.setTextColor(Color.RED);
		else if(columnIndex == 6)
			itemView.setTextColor(Color.BLUE);
		else
			itemView.setTextColor(Color.BLACK);
		
		MonthItem curItem = (MonthItem) getItem(position);
		int day = curItem.getDay();


		BookingObject object = new BookingObject();
		String d = String.format("%04d-%02d-%02d", curYear,(curMonth + 1), day);
		BookingItems items = object.getBookingItem(d);

		Log.e("JH",d + " " + String.valueOf( getCurYear() ) + " " + String.valueOf(getCurMonth()+1) );

		/*
		if( items.day.equals(d)==true){
			itemView.setisPlan(true);
		}
		*/
		//if(items!=null){
		//	itemView.setisPlan(true);
		//}


		/*
		if(position == getSelectedPosition() ) {
		//	itemView.setBackgroundColor(Color.YELLOW);
			itemView.selectDay();
		}
		else if( d.equals(getToday())==true){
			//itemView.setBackgroundColor(Color.CYAN);
			itemView.setBackgroundColor(Color.WHITE);
			//itemView.setImage();
			itemView.today();

			if( selectedPosition == -1 ){
				itemView.setBackgroundColor(Color.YELLOW);
			}

		}

		else {                                      //default 날짜 -> White
			if( isBeforeDay(curItem,d) ){
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					itemView.setImage2();
				}
			}
			else
			{
				itemView.setBackgroundColor(Color.WHITE);
				itemView.setNone();
			}
		}
		*/
		/*
        if( mSelectDay.size() == 0){
            itemView.setBackgroundColor(Color.WHITE);
            itemView.setNone();

        }
		*/

		/*
		for( int i = 0; i<mSelectDay.size();i++) {
			Log.e("JH","S >>>>"+ mSelectDay.get(i) + " " + d);
			if( d.equals(mSelectDay.get(i))==true){
				itemView.setisPlan(true);

				itemView.setBackgroundResource(R.color.select_day);
				itemView.selectCheckOut();
			}
			else{
				if(position == getSelectedPosition() ) {
					//	itemView.setBackgroundColor(Color.YELLOW);
					itemView.selectDay();
				}
				else if( d.equals(getToday())==true){
					//itemView.setBackgroundColor(Color.CYAN);
					itemView.setBackgroundColor(Color.WHITE);
					//itemView.setImage();
					itemView.today();

					if( selectedPosition == -1 ){
						itemView.setBackgroundColor(Color.YELLOW);
					}

				}

			}
		}
		*/
		//Log.e("JH","########################################## " + String.valueOf(mSelectDay.size()));

		if( mSelectDay.size() == 0){
			//Log.e("JH","##########################################  " + String.valueOf(day) );
            itemView.setisPlan(false);

			itemView.setBackgroundResource(R.color.white);
			itemView.setNone();
		}

		for( int i = 0; i<mSelectDay.size();i++) {
		//	Log.e("JH","S >>>>"+ mSelectDay.get(i) + " " + d);
			if( d.equals(mSelectDay.get(i))==true){
               /// Log.e("JH","########################################## have date");

                itemView.setisPlan(true);

				itemView.setBackgroundResource(R.color.select_day);
				itemView.selectCheckOut();
			}

		}
		///Log.e("JH","##########################################");

		return itemView;
	}
		
	private int getFirstDayOfWeek() {
		int startDay = Calendar.getInstance().getFirstDayOfWeek();
		if(startDay == Calendar.SATURDAY)
			return Time.SATURDAY;
		else if(startDay ==Calendar.MONDAY)
			return Time.MONDAY;
		else 
			return Time.SUNDAY;
	}

	private int getMonthLastDay(int year, int month) {
		switch(month){
		case 0:
		case 2:
		case 4:
		case 6:
		case 7:
		case 9:
		case 11:
			return (31);
		case 3:
		case 5:
		case 8:
		case 10:
			return(30);
		default:
			if(((year%4==0)&&(year%100!=0))|| (year%400==0))
				return (29);
			else
				return (28);
		}
	}
	
	public int getCurYear(){
		return curYear;
	}
	
	public int getCurMonth(){
		return curMonth;
	}
	
	public int getNumColumns(){
		return 7;
	}

	public int getCount() {
		return 7*6;
	}

	public Object getItem(int position) {
		return items[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition=selectedPosition;
	} 
	
	public int getSelectedPosition() {
		return selectedPosition;
	}
int CurDay = 0;

	public String getToday(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		int month = cal.get ( cal.MONTH ) + 1 ;
		int day = cal.get ( cal.DATE ) ;
		String d = String.format("%04d-%02d-%02d", year,month, day);
		CurDay = day;
		return d;
	}



	private boolean isBeforeDay(MonthItem month, String day){
		boolean ret = false;
		if( month.getDay() > 0 ) {
			String[] array;
			array = day.split("-");
			if( array != null ) {
				if( array.length == 3) {
					String d3 = array[0] + "-" + array[1];
					String d2 = String.format("%04d-%02d", curYear, curMonth + 1);

				////////	DevLog.defaultLogging("else >>>>>>>>> getView " + d2 + " " + d3 + " " + String.valueOf(CurDay)+" "+ month.getDay());
					int item_day = Integer.valueOf(array[2]);
					if (d2.equals(d3) && (month.getDay() < CurDay)) {
						ret = true;
					}
				}
			}
		}
		return ret;
	}
}
