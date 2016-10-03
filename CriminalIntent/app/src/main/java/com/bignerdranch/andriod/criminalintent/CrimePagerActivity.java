package com.bignerdranch.andriod.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

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

public class CrimePagerActivity extends AppCompatActivity
        implements CrimeFragment.Callbacks{
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.andriod.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;


    //CrimePagerActivity 의 실행을 요청하는 인텐트를 생성하고 반환한다.
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId); // Crime ID를 엑스트라 데이터로 인텐트에 저장
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        // 액티비티 뷰에서 ViePage를 찾는다
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        // CrimeLabㅇ에서 데이터 셋(Crime 객체들이 저장된 ArrayList) 을 얻는다.
        mCrimes = CrimeLab.get(this).getCrimes();

        // This Activity 의 FragmentManager 인스턴스를 얻는다.
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 페이저 어댑터로 FragmentStatePagerAdapter의 내부 클래스 인스턴스를 설정하고 생성한다.
        // FragmentStatePagerAdapter가 하는일 = 반환하는 프래그먼트를 액티비티에 추가함
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) { // ArrayList내 posision 위치에 있는 Crime 인스턴스를 가져온다.
                                                    // Crime의 ID를 사용해서 올바르게 구성된 CrimeFragment 객체를 생성하고 반환한다.
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());

            }

            @Override
            public int getCount() { // ArrayList 에 저장된 항목(Crime 객체)의 개수를 반환
                return mCrimes.size();
            }
        });

        // ViewPager는 자신의 PagerAdapter에 있는 첫번재 항목을 보여주므로 인텐트 엑스트라 데이터로 전해준 crimeId와 일치하는것을
        // mCrimes에서 찾아서 현재 아이템으로 설정해야한다.

        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        for (int i = 0 ;i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }

    @Override
    public void onCrimeUpdated(Crime crime) {

    }
}
