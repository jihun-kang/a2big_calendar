package com.a2big.calendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a2big.testcalendar.R;

//import com.a2big.dragonhouse.R;

public class MonthItemView extends RelativeLayout {
	RelativeLayout mLayout;
	private MonthItem item;
	Context mContext = null;
	ImageView mImageView;
	TextView mText;
	Button mBtnLeft, mBtnRight1, mBtnRight2;

	public MonthItemView(Context context) {
		super(context);
		////init();
		initcustomview(context);
	}

	public MonthItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		/////init();
		initcustomview(context);

	}

	private void init(){
		setBackgroundColor(Color.WHITE);
	}
	
	public MonthItem getItem(){
		return item;
	}

	public boolean getisPlan(){
		return item.getisPlan();
	}

	public void setisPlan(boolean bool){
		item.setisPlan(bool);
	}

	public void setItem(MonthItem item) {
		this.item = item;
		
		int day = item.getDay();
		if(day!=0)
			mText.setText(String.valueOf(day));
		else
			mText.setText("");


	}

	public void setTextColor(int c){
		mText.setTextColor(c);
	}



	//사용자가 체크인 선택시
	public void selectDay(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			mImageView.setImageDrawable(getResources().getDrawable(R.drawable.circle));
			//mText.setBackground(getResources().getDrawable(R.drawable.circle));
		}
	}

	//오늘 날짜 표시
	public void today(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.today_circle));
			mText.setBackgroundResource(R.color.today1);
			mText.setTextColor(Color.WHITE);

		}

	}

	//이전 날짜 선택불가
	public void beforeToday(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.today_circle));
			mText.setBackgroundResource(R.color.white);
			mText.setTextColor(Color.GRAY);
			mText.setBackground(getResources().getDrawable(R.drawable.diagonal_line));
		}

	}


	public void selectCheckOut(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_c_press));
			//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.circle0));
			//mText.setGravity(Gravity.CENTER);
			mText.setBackgroundResource(R.color.select_day);
			mText.setTextColor(Color.WHITE);
		}

	}

	public void selectCheckIn(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.btn_c_press));
			//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.circle0));
			//mText.setGravity(Gravity.CENTER);
			mText.setBackgroundResource(R.color.select_day);
			mText.setTextColor(Color.WHITE);
		}

	}

	public void setImage(){
//		mImageView.setImageDrawable(getResources().getDrawable(R.drawable.drawer_circle));
		//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.drawer_empty_circle));
		mImageView.setImageDrawable(getResources().getDrawable(R.drawable.circle1));
	}
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	public void setImage2(){
//		mImageView.setImageDrawable(getResources().getDrawable(R.drawable.drawer_circle));
		//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.drawer_empty_circle));
	//	mImageView.setImageDrawable(getResources().getDrawable(R.drawable.diagonal_line));
		mImageView.setImageDrawable(getResources().getDrawable(R.drawable.circle0));
		mText.setBackground(getResources().getDrawable(R.drawable.diagonal_line));
	}

	public void setImage3(){
//		mImageView.setImageDrawable(getResources().getDrawable(R.drawable.drawer_circle));
		//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.drawer_empty_circle));
		mImageView.setImageDrawable(getResources().getDrawable(R.drawable.circle5));
	}

	public void setNone(){
//		mImageView.setImageDrawable(getResources().getDrawable(R.drawable.drawer_circle));
		//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.drawer_empty_circle));
		//mImageView.setImageBitmap(null);
		//mImageView.setImageDrawable(getResources().getDrawable(R.drawable.circle0));


	//	mText.setBackgroundResource(R.color.white);
	//	mText.setTextColor(Color.WHITE);
		mText.setBackgroundResource(R.color.color6);
		mText.setTextColor(Color.BLACK);


	}

	void initcustomview(Context context) {
		mContext = context;

		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);

		View v = li.inflate(R.layout.custom_month_item_view, this, false);
		addView(v);

		mLayout = (RelativeLayout) findViewById(R.id.layout);

		mImageView = (ImageView) findViewById(R.id.image);
		mText = (TextView)findViewById(R.id.text);

	}


}
