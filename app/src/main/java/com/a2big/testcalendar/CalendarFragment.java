package com.a2big.testcalendar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.a2big.booking.BookDialogActivity;
import com.a2big.booking.BookingItems;
import com.a2big.booking.listener.OnDataSelectionListener;
import com.a2big.calendar.CalendarMonthAdapter;
import com.a2big.calendar.CalendarMonthView;
import com.a2big.calendar.MonthItem;
import com.a2big.scrollview.StickyScrollView;
import com.a2big.calendar.CalendarManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.text.NumberFormat;
import java.util.Locale;


public class CalendarFragment extends Fragment {
	int MAX_CALENDAR = 6;
	TextView[] txtMonth = new TextView[MAX_CALENDAR];
	CalendarMonthView[] monthView = new CalendarMonthView[MAX_CALENDAR];
	CalendarMonthAdapter[] monthViewAdapter = new CalendarMonthAdapter[MAX_CALENDAR];
	CalendarManager mCalendarManager = new CalendarManager();

	String str="";
	int curYear, curMonth,day;
	StickyScrollView mScrollView;

	private View rootView;
	LinearLayout mRoomList;
	ArrayList<BookingItems> mData;
	List<Integer> mIndexArray= new ArrayList<>();

	//ReservationRowImte mRoom1,mRoom2,mRoom3, mRoom4;
	TextView mTxtCheckIn,mTxtCheckIn2,mTxtCheckOut, mTxtCheckOut2;

	TextView txtBottomPrice, txtBottomDesc, txtNum;
	Button btnMinus,btnPlus;


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.main2, container, false);
		mRoomList  = (LinearLayout)rootView.findViewById(R.id.layout_room_list);
		mScrollView = (StickyScrollView)rootView.findViewById(R.id.scrollView);

		mTxtCheckIn = (TextView)rootView.findViewById(R.id.txtCheckIn);
		mTxtCheckIn2 = (TextView)rootView.findViewById(R.id.txtCheckIn2);
		mTxtCheckOut = (TextView)rootView.findViewById(R.id.txtCheckOut);
		mTxtCheckOut2 = (TextView)rootView.findViewById(R.id.txtCheckOut2);



		initControl();
		initCaleendar();
		return rootView;
	}
	//서버 정보로 계산
	int mLimitNum = 4;

	private void initControl(){
		btnMinus = (Button)rootView.findViewById(R.id.btnMinus);
		btnPlus= (Button)rootView.findViewById(R.id.btnPlus);

		txtNum = (TextView)rootView.findViewById(R.id.txtNum);
		txtNum.setText("0");

		txtBottomPrice= (TextView)rootView.findViewById(R.id.txtBottomPrice);
		txtBottomDesc= (TextView)rootView.findViewById(R.id.txtBottomDesc);


		btnMinus.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {

				int num = Integer.valueOf(txtNum.getText().toString());
				num = num - 1;
				if( num <= 1 ) {
					num = 1;
					btnMinus.setEnabled(false);
					btnPlus.setEnabled(true);

				}
				else{
					btnMinus.setEnabled(true);
					btnPlus.setEnabled(true);
				}
				txtNum.setText(String.valueOf(num));

				refreshInfo();

			}
		}) ;


		btnPlus.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				int num = Integer.valueOf(txtNum.getText().toString());
				num = num + 1;
				if( num >= mLimitNum ) {
					num = mLimitNum;
					btnPlus.setEnabled(false);
					btnMinus.setEnabled(true);

				}
				else{
					btnPlus.setEnabled(true);
					btnMinus.setEnabled(true);
				}
				txtNum.setText(String.valueOf(num));
				refreshInfo();

			}
		}) ;

		refreshInfo();
	}

	private void refreshInfo(){
        int totalPrice = 0;
		int price  = 25000;
		int num  = Integer.valueOf(txtNum.getText().toString());;

		if( mCalendarManager.getTotalBookingDay() > 0){
            if(num <= 0 ){
            	num = 1;
				btnMinus.setEnabled(false);
				btnPlus.setEnabled(true);
			}
			else{
				btnMinus.setEnabled(true);
				btnPlus.setEnabled(true);
			}
            txtNum.setText(String.valueOf(num));

            totalPrice = price * mCalendarManager.getTotalBookingDay() *
                                        Integer.valueOf(txtNum.getText().toString());

            txtBottomDesc.setVisibility(View.VISIBLE);
        }
        else{
            txtBottomDesc.setVisibility(View.INVISIBLE);
            btnMinus.setEnabled(false);
            btnPlus.setEnabled(false);
        }

		String desc;
        if( num <= 1){
        	desc = String.format("정상가 %d박",mCalendarManager.getTotalBookingDay());
		}
		else{
			desc = String.format("정상가 %d박(%d)",mCalendarManager.getTotalBookingDay(),num);
		}

		StringBuffer strBuf = new StringBuffer();
		strBuf.append(NumberFormat.getNumberInstance(Locale.getDefault()).format(totalPrice));
		strBuf.append("원");

        txtBottomPrice.setText(strBuf.toString());
        txtBottomDesc.setText(desc);

    }

	private void initCaleendar(){
		int year = 0;
		mCalendarManager.init(mTxtCheckIn,mTxtCheckIn2,mTxtCheckOut,mTxtCheckOut2);

		for (int i = 0; i < MAX_CALENDAR; i++) {
			int index = i + 1;
			int resTxtMonthID = rootView.getResources().getIdentifier("txtMonth"+index, "id", "com.a2big.testcalendar");
			txtMonth[i]=(TextView)rootView.findViewById(resTxtMonthID);

			int resMonthViewID = rootView.getResources().getIdentifier("monthView"+index, "id", "com.a2big.testcalendar");
			monthView[i]=(CalendarMonthView)rootView.findViewById(resMonthViewID);

			if ( i == 0){
				monthViewAdapter[i] = new CalendarMonthAdapter(getActivity());
				year = monthViewAdapter[i].getCurYear();
				setCurMonth(monthViewAdapter[i].getCurMonth(), txtMonth[i]);

			}
			else{
				int before = i - 1;
				if( before <= 0) before = 0;
				monthViewAdapter[i] = new CalendarMonthAdapter(getActivity(),
						monthViewAdapter[before].getCalendar());
				setCurMonth(year,monthViewAdapter[i].getCurYear(), monthViewAdapter[i].getCurMonth(), txtMonth[i]);

			}

			monthView[i].setAdapter(monthViewAdapter[i]);
			monthView[i].setTag(i);
			monthView[i].setData(mData);
			monthView[i].setOnDataSelectionListener(new OnDataSelectionListener(){

				public void onDataSelected(AdapterView parent, View v,
										   int position, long id) {

					int index = (int) parent.getTag();
					//monthViewAdapter[index].setSelectedPosition(position);
					MonthItem curItem = (MonthItem)monthViewAdapter[index].getItem(position);
					day = curItem.getDay();

					if( day > 0 && day <= 31) {
						mCalendarManager.setDate(monthViewAdapter[index].getCurYear(),
								(monthViewAdapter[index].getCurMonth() + 1), day);


						if (mCalendarManager.isCheckIn()) {
							resetInfo();

						    if( mCalendarManager.isCheckBeforeToday() ){
                                clearCalendar();
                            }
                            else {
                                for (int i = 0; i < MAX_CALENDAR; i++) {
                                    monthViewAdapter[i].setSelectedPosition(-1);
                                    monthViewAdapter[i].clearSelectDay();
                                    monthViewAdapter[i].notifyDataSetChanged();

                                    //체크인 날짜 선택
                                    if (index == i) {
                                        monthViewAdapter[index].setSelectedPosition(position);
                                    }

                                }
                            }
						} else {
							//체크인 일자 > 체크아웃 일자
						    if( mCalendarManager.isErrorBookingDate() ){
								clearCalendar();
                            }
                            else {
                                List<String> selectDay = mCalendarManager.getSelectDay();
                                int monthIndex = 0;
                                Set<String> list = mCalendarManager.getUpdateApdaptor(selectDay);

                                if (selectDay.size() > 0) {

                                    for (int indexAdaptor = 0; indexAdaptor < MAX_CALENDAR; indexAdaptor++) {
                                        for (String selectDate : list) {
                                            String curDate = String.format("%04d-%02d",
                                                    monthViewAdapter[indexAdaptor].getCurYear(),
                                                    monthViewAdapter[indexAdaptor].getCurMonth() + 1);
                                            if (selectDate.substring(0, 7).equals(curDate)) {
                                                //monthViewAdapter[indexAdaptor].setSelectedPosition(position);
                                                monthViewAdapter[indexAdaptor].setSelectDay(selectDay);
                                                monthViewAdapter[indexAdaptor].notifyDataSetChanged();
                                            }
                                        }
                                    }

									refreshInfo();

								}
                            }
						}
					}
					//달력에서 빈셀을 클릭하면 에러
					else{
						clearCalendar();
					}

				}
			});
		}
	}

	private void resetInfo(){
		txtNum.setText("0");
		refreshInfo();

	}

	private void clearCalendar(){
		resetInfo();

		mCalendarManager.reset();
		for (int i = 0; i < MAX_CALENDAR; i++) {
			monthViewAdapter[i].setSelectedPosition(-1);
			monthViewAdapter[i].clearSelectDay();
			monthViewAdapter[i].notifyDataSetChanged();
		}
	}
	
	private void setCurMonth(int year, int curYear, int curMonth, TextView txtMonth ){
		if( year != curYear){
			txtMonth.setText(String.valueOf(curYear) + "년 " + String.valueOf(curMonth + 1) + "월");
		}
		else {
			txtMonth.setText(String.valueOf(curMonth + 1) + "월");
		}

	}

	private void setCurMonth(int curMonth, TextView txtMonth ){
		txtMonth.setText(String.valueOf(curMonth + 1) + "월");
	}


	private void goSelectDialogPage() {
		Intent intent = new Intent(getActivity(), BookDialogActivity.class);
		startActivity(intent);
	}

}