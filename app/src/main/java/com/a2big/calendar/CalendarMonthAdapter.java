package com.a2big.calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CalendarMonthAdapter extends BaseAdapter{

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

	public CalendarMonthAdapter(Context context) {
		super();
		mContext = context;
		init();
	}
	
	public CalendarMonthAdapter(Context context, Calendar calendar) {
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

		//Log.e("JH", curYear+" "+curMonth);
	}

	public void setPreviousMonth(){
		mCalendar.add(Calendar.MONTH, -1);
		recalculate();
		
		resetDayNumbers();
		selectedPosition=-1;
	}


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


        if( mSelectDay.size() == 0){
            itemView.setisPlan(false);
			itemView.setBackgroundResource(R.color.white);
			itemView.setNone();
		}

		//예약불가 확인
        //오늘보다 이전인 경우
		if( day > 0 ) {
			showInfo(itemView, day);

			//체크인, 체크아웃 표시
			curSelectDay(position, itemView);

			///체크인, 체크아웃 선택한 일정 표시
			showBooking(itemView, day);
		}
		return itemView;
	}

	private void showInfo(MonthItemView itemView, int day){
        String curDay = String.format("%04d-%02d-%02d", curYear,(curMonth + 1), day);

        String today = getToday();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = sdf.parse(today);
            Date date2 = sdf.parse(curDay);
            int i = date1.compareTo(date2);
            switch (i){
            	//일반(date1<date2)
                case -1:
                    break;
                 //이전 날짜(date1>date2)
                case 1: // = 1
					itemView.beforeToday();
                    break;
                 //오늘날짜 표시(date1==date2)
				case 0:
					itemView.today();
                    break;
                default:
                    // return sDate2;
                    break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
	//체크인, 체크아웃 선택한 일정 표시
	private void showBooking(MonthItemView itemView, int day){
		String d = String.format("%04d-%02d-%02d", curYear,(curMonth + 1), day);
		Log.e("JH","Today :" + d + " " + getToday()+ " 11111111");

		for( int i = 0; i<mSelectDay.size();i++) {
			if( d.equals(mSelectDay.get(i))==true){
				itemView.setisPlan(true);
				itemView.setBackgroundResource(R.color.select_day);
				itemView.selectCheckOut();
			}
		}
	}

	//체크인, 체크아웃 선택한 일정 표시
	private void showToday(MonthItemView itemView, int day){
		String d = String.format("%04d-%02d-%02d", curYear,(curMonth + 1), day);
		Log.e("JH","Today :" + d + " " + getToday()+ " 11111111");

		if( d.equals(getToday())==true){
			Log.e("JH","Today :" + d + " " + getToday() + " 22222222222");
			itemView.today();
		}
	}



	//현재 셀을 클릭했을때..
	private void curSelectDay(int position,MonthItemView itemView){
	    Log.e("JH","curSelectDay :" + String.valueOf(position) + " "+String.valueOf(getSelectedPosition()));
		if(position == getSelectedPosition() ) {
			itemView.setisPlan(true);
			itemView.setBackgroundResource(R.color.select_day);
			itemView.selectCheckIn();
		}
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
