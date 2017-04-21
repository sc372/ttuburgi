package com.TT.kitcoop.ttuburgi.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

/**
 * Created by sc372 on 4/20/17.
 */

public class StudyDataAdapter {

    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDB;
    private StudyDBHelper studyDbHelper;

    public StudyDataAdapter(Context context) {
        this.mContext = context;
        studyDbHelper = new StudyDBHelper(mContext);
    }

    public StudyDataAdapter createStudyDataBase() throws SQLException {
        try {
            studyDbHelper.createStudyDatabase();
        } catch (IOException e) {
            Log.e(TAG, e.toString() + " UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public StudyDataAdapter openStudyDatabase() throws SQLException {
        try {
            studyDbHelper.openStudyDataBase();
            studyDbHelper.close();
            mDB = studyDbHelper.getReadableDatabase();
        } catch (SQLException e) {
            Log.e(TAG, "open >>" + e.toString());
            throw e;
        }
        return this;
    }

    public void closeStudy() {
        studyDbHelper.close();
    }

    // 랜덤한 학습창을 보여주기 위한 메서드
    public Cursor getRandomList(String subject) {

        Cursor randomCursor = null;
        try {
            // 넘어온 값을 인자로 테이블행 갯수 메서드를 count로 담는다
            int count = getCount(subject);
            System.out.println(subject);

            System.out.println("getRandomList()");
            // 인덱스 수 안에서 랜덤 수 추출
            Random random = new Random();
            int randomId = random.nextInt(count);

            String randomSql = "select * from study where id=" + randomId;
            randomCursor = mDB.rawQuery(randomSql, null);
            if (randomCursor != null) {
                randomCursor.moveToNext();
                System.out.println(randomCursor.getCount());
            }
        } catch (SQLException e) {
            Log.e(TAG, "getRandomList >>" + e.toString());
            throw e;
        }
        return randomCursor;
    }


    // 테이블 행 갯수를 구하기 위한 메서드
    public int getCount(String subject) {
        // 테이블 행 갯수 저장을 위한 변수 선언
        int count = 0;

        // 받아온 값으로 테이블 전체 조회
        try {

            System.out.println("getCount()");
            System.out.println(subject);
            String allSql = "select * from study where subject = '" + subject + "'";
            Cursor allCursor = mDB.rawQuery(allSql, null);
            if (allCursor != null) {
                allCursor.moveToNext();
                count = allCursor.getCount();
                System.out.println(allCursor.getCount());
            }
        } catch (SQLException e) {
            Log.e(TAG, "getCount >>" + e.toString());
            throw e;
        }

        return count;
    }
}
