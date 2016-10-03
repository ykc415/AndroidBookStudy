package com.sunghyun.andriod.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by YKC on 2016. 10. 1..
 */
public class NoteDatas {

    private static NoteDatas sNoteDatas;


    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static NoteDatas get(Context context) {
        if (sNoteDatas == null) {
            sNoteDatas = new NoteDatas(context);
        }
        return sNoteDatas;
    }

    private NoteDatas(Context context) {
        mContext = context.getApplicationContext();
        // 컨텍스트 받아서 애플리케이션 컨텍스트만드는이유
        // 애플리케이션 객체는 액티비티보다 더 긴 생애를 가지기때문에 CrimeLab이 mContext객체 참조를 유지하는한 절대 소멸되지않음

    }

    


}
