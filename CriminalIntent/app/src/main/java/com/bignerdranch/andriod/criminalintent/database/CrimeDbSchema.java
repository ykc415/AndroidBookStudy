package com.bignerdranch.andriod.criminalintent.database;

/**
 * Created by YKC on 2016. 9. 25..
 * 데이터베이스 관련 모든코드를 database패키지에 모아둔다.
 */

public class CrimeDbSchema {
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }
    }
}
