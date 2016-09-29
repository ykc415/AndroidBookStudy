package com.bignerdranch.andriod.criminalintent;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.andriod.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.andriod.criminalintent.database.CrimeCursorWrapper;
import com.bignerdranch.andriod.criminalintent.database.CrimeDbSchema;
import com.bignerdranch.andriod.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by YKC on 2016. 9. 18..
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        // 컨텍스트 받아서 애플리케이션 컨텍스트만드는이유
        // 애플리케이션 객체는 액티비티보다 더 긴 생애를 가지기때문에 CrimeLab이 mContext객체 참조를 유지하는한 절대 소멸되지않음
        mDatabase = new CrimeBaseHelper((mContext)).getWritableDatabase();

//        for (int i = 0; i < 3; i++) {
//            Crime crime = new Crime();
//            crime.setTitle("범죄 #" + i);
//            crime.setSolved(i % 2 == 0); // 짝수번째 요소에는 true를 임의 설정한다.
//            mCrimes.add(crime);
//        }
    }

    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public List<Crime> getCrimes() { // 모든 Crime 데이터를 쿼리하여 그 결과를 Crime 리스트에 채운다.
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return crimes;
    }



    public Crime getCrime(UUID id) {

        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }

    }

    private static ContentValues getContentValues(Crime crime) { // 데이터베이스에 데이터를 추가하거나 갱신하기위해 사용하는 클래
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return values;

    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        //
        mDatabase.update(CrimeTable.NAME, // 갱신할 테이블의 이름
                values, // 각행에 지정할 ContentValues
                CrimeTable.Cols.UUID + " = ?", // SQL의 where절을 만들어 전달
                new String[]{ uuidString }); // where절에 지정할 값을 String 배열로 전달


    }

    // CrimeTable에 대하여 query를 수행하는 매서드
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query( // Cursor는 결과 데이터를 가져오는데 사용된다.
                CrimeTable.NAME,
                null,  // 테이블 열(Columns) - null인 경우 테이블의 모든 열을 의미
                whereClause,
                whereArgs,
                null, // SQL Select 명령문의 groupBy
                null, // SQL Select 명령문의 having
                null // SQL Select 명령문의 orderby
        );
        return new CrimeCursorWrapper(cursor);
    }

}
