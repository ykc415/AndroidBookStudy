package com.bignerdranch.andriod.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import java.util.UUID;

/**
 *
 *  1. CrimePagerAcitivty 클래스를 생성한다
 *  2. ViewPager 를 구성하는 뷰 계층구조를 정의한다.
 *  3. ViewPager와 그것의 어댑터를 CrimePagerActivity 코드에서 연결한다.
 *  4. CrimeHolder.onClick(...)에서 CrimeActivity 대신 CrimeCrimePagerActivity를 시작시키도록 함
 */
public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;

    }


    @Override
    protected Fragment createFragment() {
        UUID crimeID = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeID);

    }
}
