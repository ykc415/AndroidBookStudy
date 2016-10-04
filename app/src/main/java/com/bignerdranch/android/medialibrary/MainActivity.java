package com.bignerdranch.android.medialibrary;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_CODE = 100;

    public static ArrayList<RecyclerData> datas = null;

    RecyclerView rv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_card);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            initData();
        } else {
            checkPermissions();
        }



//        for(int i=0;i<100;i++) {
//            RecyclerData data = new RecyclerData();
//            data.title = i + " Hey Brother";
//            data.artist = "Avcii";
//
//
//            datas.add(data);
//        }




    }

    @TargetApi(Build.VERSION_CODES.M) // 이버전일때만 실행된다
    private void checkPermissions() {
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ) {
            // 쓰기 권한이 없으면 로직 처리
            // 중간에 권한 내용에 대한 알림을 처리하는 함수
            //shouldShowRequestPermissionRationale()

            String permissionArray[] =  {Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.CAMERA};

            requestPermissions(permissionArray, REQUEST_CODE); // 권한 획득을위한 호출


        } else {
            // 쓰기 권한이 있으면 로직 처리
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                }
                break;
        }
    }

    public void initData() {
        datas = getMusicInfo();
        rv = (RecyclerView) findViewById(R.id.recyclerCardView);
        RecyclerCardAdapter adapter = new RecyclerCardAdapter(this,datas,R.layout.activity_recycler_card_item);
        rv.setAdapter(adapter);


        // 수직 수평 스크롤이 가능
        //RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        // 그리드 타입
        //RecyclerView.LayoutManager manager = new GridLayoutManager(this, 3);
        // StaggerdGrid 타입  StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL)
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
    }


    public ArrayList<RecyclerData> getMusicInfo() {
        ArrayList<RecyclerData>  datas = new ArrayList<RecyclerData>();

        String projections[] = {
                MediaStore.Audio.Media._ID,         // 노래아이디
                MediaStore.Audio.Media.ALBUM_ID,    // 앨범아이디
                MediaStore.Audio.Media.TITLE,       // 제목
                MediaStore.Audio.Media.ARTIST,      // 가수
        };

        //getContentResolver().query(주소, 검색해올컴럼명들, 조건절, 조건절에 매핑되는 값, 정렬)
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
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
                RecyclerData data = new RecyclerData();
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

        return datas;
    }



}
