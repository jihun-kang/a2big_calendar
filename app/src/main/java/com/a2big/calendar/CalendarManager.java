package com.a2big.calendar;

import android.util.Log;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CalendarManager {
    private int mCurState;
    private String mCheckIn;
    private String mCheckOut;
    List<String> mSelectDay = new ArrayList<>();

    private TextView mTxtCheckIn,mTxtCheckIn2,mTxtCheckOut,mTxtCheckOut2;
    public CalendarManager() {
        mCurState = 0; //ready for check_in
        mCheckIn = "";
    }

    public void init(TextView t1, TextView t2,TextView t3,TextView t4){
        mTxtCheckIn  = t1;
        mTxtCheckIn2 = t2;
        mTxtCheckOut = t3;
        mTxtCheckOut2 = t4;

        mTxtCheckIn.setText("체크인");
        mTxtCheckIn2.setText("");
        mTxtCheckOut.setText("체크아웃");
        mTxtCheckOut2.setText("");

    }

    public void reset(){
        mCurState = 0;
        mTxtCheckIn.setText("체크인");
        mTxtCheckIn2.setText("");
        mTxtCheckOut.setText("체크아웃");
        mTxtCheckOut2.setText("");
        mSelectDay.clear();
    }

    public boolean isCheckIn(){
       // Log.e("JH","isCheckIn:"+ mTxtCheckOut.getText());
       if ( mTxtCheckOut.getText().equals("체크아웃") == true) { return true; }
       else { return false;}
    }

    public int getTotalBookingDay(){
        return mSelectDay.size();
    }

    public List<String> getSelectDay(){
        return mSelectDay;
    }

    public void setDate(int year, int month, int day){
        switch (mCurState){
            case 0:
                try {
                    mCheckIn = String.format("%04d-%02d-%02d", year,month, day);
                    mTxtCheckIn.setText(String.format("%d",  month) + "월");
                    mTxtCheckIn2.setText(String.format("%d", day) + "일" + " " + getDateDay(mCheckIn));

                    mTxtCheckOut.setText("체크아웃");
                    mTxtCheckOut2.setText("");
                    mCurState = 1;
                    mSelectDay.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case 1:

                try {
                    mCheckOut = String.format("%04d-%02d-%02d", year,month, day);
                    mTxtCheckOut.setText(String.format("%d",  month) + "월");
                    mTxtCheckOut2.setText(String.format("%d", day) + "일"+ " " + getDateDay(mCheckOut));


                    mSelectDay = getAllDatesBetweenTwoDates(mCheckIn,mCheckOut,"yyyy-MM-dd","yyyy-MM-dd",false);
                   // for (int i = 0; i < mSelectDay.size(); i++){
                   //     Log.e("JH","Date>>" + mSelectDay.get(i));
                   // }


                    mCurState = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }



                break;
            default:
                mCheckIn = String.format("%04d-%02d-%02d", year,month, day);
                mCurState = 0;
                break;
        }

    }


    public Set<String> getUpdateApdaptor(List<String> selectDay)
    {
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < selectDay.size(); i++) {
            String d = selectDay.get(i).substring(0, 7);
            list.add(d);
        }
        return findDuplicates(list);
    }



    public static Set<String> findDuplicates(List<String> listContainingDuplicates) {

        final Set<String> setToReturn = new HashSet<String>();
        final Set<String> set1 = new HashSet<String>();
        for (String yourInt : listContainingDuplicates) {
            if (!set1.contains(yourInt)) {
                setToReturn.add(yourInt);
            }
        }
        return setToReturn;
    }


    /*
      사용자가 입력한 체크인, 체크아웃 날짜로 숙박하는 날짜를 케런더에 표시 하기 위해 날자 계산
     */
    public static List<String> getAllDatesBetweenTwoDates(String stdate,String enddate,String givenformat,String resultformat,boolean onlybunessdays) throws ParseException{
        DateFormat sdf;
        DateFormat sdf1;
        List<Date> dates = new ArrayList<Date>();
        List<String> dateList = new ArrayList<String>();
        SimpleDateFormat checkformat = new SimpleDateFormat(resultformat);
        checkformat.applyPattern("EEE");  // to get Day of week
        try{
            sdf = new SimpleDateFormat(givenformat);
            sdf1 = new SimpleDateFormat(resultformat);
            stdate=sdf1.format(sdf.parse(stdate));
            enddate=sdf1.format(sdf.parse(enddate));

            Date  startDate = (Date)sdf1.parse( stdate);
            Date  endDate = (Date)sdf1.parse( enddate);
            long interval = 24*1000 * 60 * 60; // 1 hour in millis
            long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
            long curTime = startDate.getTime();
            while (curTime <= endTime) {
                dates.add(new Date(curTime));
                curTime += interval;
            }
            for(int i=0;i<dates.size();i++){
                Date lDate =(Date)dates.get(i);
                String ds = sdf1.format(lDate);
                if(onlybunessdays){
                    String day= checkformat.format(lDate);
                    if(!day.equalsIgnoreCase("Sat") && !day.equalsIgnoreCase("Sun")){
                        dateList.add(ds);
                    }
                }else{
                    dateList.add(ds);
                }
            }

        }catch(ParseException e){
            e.printStackTrace();
            throw e;
        }finally{
            sdf=null;
            sdf1=null;
        }
        return dateList;
    }

    //요일구하기
    private   String getDateDay(String date) throws Exception {

        String day = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nDate = dateFormat.parse(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayNum) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;

        }

        return day;
    }



    public boolean isErrorBookingDate( ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = sdf.parse(mCheckIn);
            Date date2 = sdf.parse(mCheckOut);
            int i = date1.compareTo(date2);
            switch (i){
                case -1: //date1<date2 = -1
                    //  return sDate2;
                    Log.e("JH","showBeforeTodoay {"+mCheckIn + " " + mCheckOut +"}" + "OK");
                    return false;
                case 1: //date1>date2 = 1
                    Log.e("JH","showBeforeTodoay {"+mCheckIn + " " + mCheckOut +"}" + "Error");
                    return true;
                case 0: //date1==date2= 0
                    return false;
                default:
                    return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getToday(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        int month = cal.get ( cal.MONTH ) + 1 ;
        int day = cal.get ( cal.DATE ) ;
        String d = String.format("%04d-%02d-%02d", year,month, day);
        //CurDay = day;
        return d;
    }


    public boolean isCheckBeforeToday(){
        String curDay = mCheckIn;
        String today = getToday();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = sdf.parse(today);
            Date date2 = sdf.parse(curDay);
            int i = date1.compareTo(date2);
            switch (i){
                case -1: //date1<date2 = -1
                    return false;
                case 1: //date1>date2 = 1
                    return true;
                case 0: //date1==date2= 0
                    return false;
                default:
                    return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
