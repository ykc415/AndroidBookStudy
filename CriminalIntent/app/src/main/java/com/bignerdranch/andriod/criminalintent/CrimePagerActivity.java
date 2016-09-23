package com.bignerdranch.andriod.criminalintent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by YKC on 2016. 9. 22..
 *
 *
 *  1. CrimePagerAcitivty 클래스를 생성한다
 *  2. ViewPager 를 구성하는 뷰 계층구조를 정의한다.
 *  3. ViewPager와 그것의 어댑터를 CrimePagerActivity 코드에서 연결한다.
 *  4. CrimeHolder.onClick(...)에서 CrimeActivity 대신 CrimeCrimePagerActivity를 시작시키도록 함
 *
 */

public class CrimePagerActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
    }
}
