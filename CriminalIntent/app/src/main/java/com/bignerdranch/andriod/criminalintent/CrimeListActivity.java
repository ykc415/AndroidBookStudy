package com.bignerdranch.andriod.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by YKC on 2016. 9. 19..
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
