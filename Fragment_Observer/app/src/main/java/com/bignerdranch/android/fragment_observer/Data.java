package com.bignerdranch.android.fragment_observer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by YKC on 2016. 10. 7..
 */
public class Data { // 싱글톤

    ArrayList<Observer> observers = null;
    ArrayList<MusicData> datas = null;
    ContentResolver contentResolver;

    private static Data sData = null;
    private Context context;

    private int position = -1;


    public static Data createData(Context context, ContentResolver contentResolver) {
        if (sData == null) {
            sData = new Data(context, contentResolver);
        }
        return sData;
    }

    public static Data getInstance() {
        if(sData == null) {
            Log.d("Data","Data is not allocated", new NullPointerException());
        }
        return sData;
    }

    private Data(Context context, ContentResolver contentResolver) {
        this.context = context.getApplicationContext();
        // 컨텍스트 받아서 애플리케이션 컨텍스트만드는이유
        // 애플리케이션 객체는 액티비티보다 더 긴 생애를 가지기때문에 Data가 context객체 참조를 유지하는한 절대 소멸되지않음
        this.contentResolver = contentResolver;

        observers = new ArrayList<>();
        datas = new ArrayList<>();

        getMusicInfo();

        position = 0;

    }

    public ArrayList<MusicData> getDatas() {
        return datas;
    }

    public void attach(Observer o) {
        observers.add(o);
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) { // 포지션 변경되면 옵저버로 업데이트
        this.position = position;
        startUpdate();
    }

    private void startUpdate() {  //옵저버로 업데이트 호출
        for(Observer observer : observers) {
            observer.update();
        }
    }

    public interface Observer {
        public void update();
    }

    public void getMusicInfo() {

        String projections[] = {
                MediaStore.Audio.Media._ID,         // 노래아이디
                MediaStore.Audio.Media.ALBUM_ID,    // 앨범아이디
                MediaStore.Audio.Media.TITLE,       // 제목
                MediaStore.Audio.Media.ARTIST,      // 가수
        };

        //getContentResolver().query(주소, 검색해올컴럼명들, 조건절, 조건절에 매핑되는 값, 정렬)
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , projections,null,null,null);
        /*

          - uri           : content: 스키마 형태로 정해져 있는곳의 데이터를 가져온다
          - projection    : 가져올 컬럼 이름들의 배열 null 을 입력하면 모든 값을 가져온다
          - selection     : 조건절(where)에 해당하는 내용
          - selectionArgs : 조건절이 preparedstatement 형태일때 ? 에 매핑되는 값을 배열
          - sort order    : 정렬 조건

         */

        if (cursor != null) {

            while(cursor.moveToNext()) { // 커서가 다음으로 이동할수 있으면
                MusicData data = new MusicData();
                // 데이터에 가수이름을 입력
                // 1. 가수 이름 컬럼의 순서(index)를 가져온다
                int index = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                // 2. 해당 index를 가진 컬럼의 실제값을 가져온다.
                data.artist = cursor.getString(index);

                index = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                data.title = cursor.getString(index);

                index = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                data.albumId = cursor.getLong(index);

                index = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                data.musicId = cursor.getString(index);

                datas.add(data);
            }
        }

        cursor.close();

    }
}

class MusicData {
    String musicId;
    long albumId;
    String title;
    String artist;
}
