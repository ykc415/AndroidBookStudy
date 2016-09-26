package com.bignerdranch.andriod.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.andriod.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Created by YKC on 2016. 9. 25..
 */
public class CrimeBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crimeBase.db";
    private static final int VERSION = 1;

    public CrimeBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
