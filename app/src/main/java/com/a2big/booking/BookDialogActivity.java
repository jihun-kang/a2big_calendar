package com.a2big.booking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.a2big.testcalendar.R;


public class BookDialogActivity extends Activity {
    private int mLookId;
    private int mPosition;
    private int mCategory;
    private int mType;
    private String mSearchKeyword;
    private RadioGroup radioGroup1;
    RelativeLayout mCameraLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Dialog 사이즈 조절 하기
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);


        setContentView(R.layout.book_dialog);
        Button btnCancle = (Button)findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
               // DevLog.defaultLogging("Click cancle....");
                finish();
            }
        });

        Button btnBook = (Button)findViewById(R.id.btnBook);
        btnBook.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
              //  DevLog.defaultLogging("Click btnBook....");
                //  finish();
            }
        });


    }

}

