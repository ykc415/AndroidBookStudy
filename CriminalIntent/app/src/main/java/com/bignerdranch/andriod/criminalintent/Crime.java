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
    private String mSuspect;

    public Crime() { //이생성자 호출하면
        this(UUID.randomUUID()); // 새로 식벽번호만들어서 Crime(UUID id) 생성자를 다시호출함

    }


    public Crime(UUID id) {

        mId = id;
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

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }
}
