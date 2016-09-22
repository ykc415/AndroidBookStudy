package com.bignerdranch.andriod.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by YKC on 2016. 9. 13..
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;


    public Crime() {
        // 고유한 식별자를 생성한다.
        mId = UUID.randomUUID();
        mDate = new Date();

    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
