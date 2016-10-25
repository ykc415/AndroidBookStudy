package com.bignerdranch.android.photogallery;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by YKC on 2016. 10. 20..
 */

public class QueryPreferences {

    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_LAST_RESULT_ID = "lastResultId";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";  // 알람이 켜져있느닞 여부를 프레퍼런스테 저장

    // 디폴트 공유 프레퍼런스에 저장된 쿼리 문자열을 반환하는 메서드
    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context) //컨텍스트의 디폴트 SharedPreference를 얻는다
                .getString(PREF_SEARCH_QUERY, null); // 키값으로 공유프레퍼런스에 저장했던 값을 읽는다.
                           //       키값    ,  디폴트값
    }


    // 컨텍스트의 디폴트 공유 프레퍼런스테 쿼리 문자열을 저장한다.
    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

    public static String getLastResultId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LAST_RESULT_ID, null);
    }

    // 가장최근 가져온 쿼리 결과의 사진ID를 저장
    public static void setLastResultId(Context context, String lastResultId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_RESULT_ID, lastResultId)
                .apply();
    }

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_ALARM_ON, false);
    }

    public static void setAlarmOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON, isOn)
                .apply();
    }
}
