package com.bignerdranch.android.fragment_observer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.*;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private final static int REQUEST_CODE = 100;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            initData();
        } else {
            checkPermissions();
        }


        setContentView(R.layout.activity_main);


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

        Data.createData(this,getContentResolver());

    }


}
