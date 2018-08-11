package com.a2big.testcalendar;

import android.app.Activity;
import android.os.Bundle;

import com.a2big.booking.BookingObject;

public class MainActivity extends Activity {
    private final CalendarFragment mSecondFragment = new CalendarFragment();
    private int mNavItemId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigate();
       // test2();
    }

    private void navigate() {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, mSecondFragment)
                        .commit();
    }

    private void test2(){
        BookingObject object = new BookingObject();
        boolean result =  object.onResponseListener("ee");
    }



}
