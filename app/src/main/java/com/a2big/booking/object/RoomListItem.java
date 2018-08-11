package com.a2big.booking.object;

public class RoomListItem {
	private int mRoomID;
	private String mRoomType;
	private String mSex;
	private String imageURL;
	private boolean mFlag;

	public RoomListItem() {

	}

	public RoomListItem(int roomID,String roomType, String sex, String imageURL, boolean flag) {
		this.mRoomID = roomID;
		this.mRoomType = roomType;
		this.mSex = sex;
		this.imageURL = imageURL;
		this.mFlag = flag;

	}
	public int getRoomID() {
		return mRoomID;
	}

	public String getRoomType() {
		return mRoomType;
	}

	public String getSex() {
		return mSex;
	}

	public String getImageURL() {
		return imageURL;
	}

	public boolean getFlag() {
		return mFlag;
	}


}
