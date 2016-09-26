package com.bignerdranch.andriod.criminalintent;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.andriod.criminalintent.database.CrimeBaseHelper;
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

    public List<Crime> getCrimes() {
        return new ArrayList<>();
    }

    public Crime getCrime(UUID id) {

        return null;
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;

    }

}
