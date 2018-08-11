package com.a2big.booking.object;

/**
 * Created by a2big on 15. 6. 8..
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a2big.testcalendar.R;
import com.a2big.booking.listener.OnRoomSelectionListener;

/*
import com.a2big.android.library.init.A2BigApp;
import com.a2big.android.library.utils.DNetworkImageView;
import com.a2big.android.library.utils.DevLog;
import com.a2big.dragonhouse.calendar.OnRoomSelectionListener;
import com.a2big.dragonhouse.calendar.RoomListItem;
import com.android.volley.toolbox.ImageLoader;
*/
public class ReservationRowImte extends LinearLayout {
    Context mContext = null;
   // DNetworkImageView mImageView;
    TextView mText1,mText2;
    Button button;
   // ImageLoader imageLoader;
    private OnRoomSelectionListener selectionListener;

    private static final String androidns = "http://schemas.android.com/apk/res/android";

    /** Called when the activity is first created. */
    public ReservationRowImte(Context context, AttributeSet attrs) {
        super(context, attrs);

        String titlename1  = attrs.getAttributeValue(androidns, "text1");
        String titlename2  = attrs.getAttributeValue(androidns, "text2");

     //   DevLog.defaultLogging("ReservationRowImte...." + titlename1 + " " + titlename2);
        RoomListItem root = new RoomListItem(0,"4인실 도미토리","남자", "http://www.dragon-house.co.kr/image/domitory.png",true);
        initcustomview(context,root);
    }
    public void setOnRoomSelectionListener(OnRoomSelectionListener listener){
        this.selectionListener= listener;
    }

    public ReservationRowImte(Context context, RoomListItem t) {
        super(context);
       // imageLoader = A2BigApp.getApplication().getImageLoader();

        initcustomview(context, t);
    }

    public void init(Integer id, String name, String sex, String url, boolean t){
        button.setTag(id);
        mText1.setText(name);
        mText2.setText(sex);
     //   mImageView.setImageUrl(url, imageLoader);
        if( t == true) {
            button.setText("예약가능");
        }
        else{
            button.setText("매진");
        }


    }

    public void setRoomId(Integer n){
        button.setTag(n);
    }

    public void setRoomName(String n){
        mText1.setText(n);
    }

    public void setSex(String n){
        mText2.setText(n);
    }

  //  public void setRoomImage(String url){
    //    mImageView.setImageUrl(url, imageLoader);
   // }

    public void setButtonName(Boolean t){
        if( t == true) {
            button.setText("예약가능");
        }
        else{
            button.setText("매진");
        }
    }


    void initcustomview(Context context,RoomListItem t) {
        mContext = context;
        //if(imageLoader == null){
       //     imageLoader = A2BigApp.getApplication().getImageLoader();
       // }

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);

        View v = li.inflate(R.layout.room_list_item, this, false);
        addView(v);

      //  mImageView = (DNetworkImageView) findViewById(R.id.networkImageView);
     //   mImageView.setImageUrl(t.getImageURL(), imageLoader);

        mText1 = (TextView)findViewById(R.id.text1);
        mText1.setText(t.getRoomType());

        mText2 = (TextView)findViewById(R.id.text2);
        mText2.setText(t.getSex());

        button = (Button)findViewById(R.id.btnMakeBook);
        button.setTag(t.getRoomID());
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
              //  DevLog.defaultLogging("Click button....");
                if(selectionListener != null){
                    selectionListener.onDataSelected((int)button.getTag());
                }

            }
        });


    }


}