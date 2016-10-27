package com.bignerdranch.android.photogallery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by YKC on 2016. 10. 25..
 */

public class VisibleFragment extends Fragment {
    private static final String TAG = "VisibleFragment";

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(PollService.ACTION_SHOW_NOTIFICATION);

        // 브로드캐스트리시버 설정할때 프라이빗 퍼미션도 같이 설정
        getActivity().registerReceiver(mOnShowNotification, filter, PollService.PERM_PRIVATE, null);

    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mOnShowNotification);
    }

    private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 이 브로드캐스트 인텐트를 받는다는 것은 현재 프래그먼트가 화면에 보이는 것이므로 통지를 취소한다
            Log.i(TAG, "canceling notification");
            setResultCode(Activity.RESULT_CANCELED);
        }
    };


}
