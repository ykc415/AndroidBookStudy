package com.bignerdranch.android.photogallery;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import java.util.List;

/**
 * Created by YKC on 2016. 10. 20..
 */



// 검색 결과를 백그라운드로 받는데 사용하는 서비스
public class PollService extends IntentService {

    private static final String TAG = "PollService";

    private static final int POLL_INTERVAL = 1000 * 60; // 60초

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i , 0);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PollService() {
        super(TAG);
    }

    /*  @ 서비스가 하는일
     *
     *  1. 디폴트 SharedPreferences에 저장되어 있는 가장 최근의 쿼리와 결과 데이터 ID를 읽는다
     *  2. FlickrFetchr를 사용해서 플리커로부터 가장 최근의 결과를 쿼리하여 가져온다.
     *  3. 만일 쿼리 결과가 있으면 첫 번째 것의 ID를 알아낸다
     *  4. 그것이 1번의 가장 최근 쿼리의 결과 ID와 다른지 확인한다.
     *  5. 3번의 첫 번째 결과 ID를 가장 최근 결과 ID로 SharedPreferences에 다시 저장한다.
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isNetworkAvailableAndConnected()) {
            return;
        }

        String query = QueryPreferences.getStoredQuery(this);
        String lastResultId = QueryPreferences.getLastResultId(this);
        List<GalleryItem> items;

        if (query == null) {
            items = new FlickrFetchr().fetchRecentPhotos();
        } else {
            items = new FlickrFetchr().searchPhotos(query);
        }

        if (items.size() == 0) {
            return ;
        }

        String resultId = items.get(0).getId();
        if (resultId.equals(lastResultId)) {
            Log.i(TAG, "Got an old result: " + resultId);
        } else {
            Log.i(TAG, "Got a new result: " + resultId);
        }

        QueryPreferences.setLastResultId(this, resultId);


    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }
}
