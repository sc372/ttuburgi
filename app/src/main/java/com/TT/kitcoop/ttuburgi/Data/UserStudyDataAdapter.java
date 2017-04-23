package com.TT.kitcoop.ttuburgi.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

/**
 * Created by sc372 on 4/20/17.
 */

public class UserStudyDataAdapter {

    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDB;
    private UserStudyDBHelper userStudyDBHelper;

    public UserStudyDataAdapter(Context context) {
        this.mContext = context;
        userStudyDBHelper = new UserStudyDBHelper(mContext);
    }

    public UserStudyDataAdapter createUserDatabase() throws SQLException {
        try {
            userStudyDBHelper.createUserDatabase();
        } catch (IOException e) {
            Log.e(TAG, e.toString() + " UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }


    public UserStudyDataAdapter openUserDatabase() throws SQLException {
        try {
            userStudyDBHelper.openUserDataBase();
            userStudyDBHelper.close();
            mDB = userStudyDBHelper.getReadableDatabase();
        } catch (SQLException e) {
            Log.e(TAG, "open >>" + e.toString());
            throw e;
        }
        return this;
    }

    // 학습 완료 리스트 업
    public void completeUserStudy(String completeSubject) {
        mDB = userStudyDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("studyname", completeSubject);
        mDB.insert("complete", null, contentValues);
    }

    // 북마크 리스트 불러오기
    public Cursor getBookmarkList() {

        Cursor bookmarkCursor = null;
        try {

            String bookmarkSql = "select studyname from bookmark";
            bookmarkCursor = mDB.rawQuery(bookmarkSql, null);
            if (bookmarkCursor != null) {
                bookmarkCursor.moveToNext();
            }
        } catch (SQLException e) {
            Log.e(TAG, "getRandomList >>" + e.toString());
            throw e;
        }
        return bookmarkCursor;
    }

    // 북마크 추가하기
    public void bookmarkInsert(String bookmarkStudyname) {
        mDB = userStudyDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("studyname", bookmarkStudyname);
        mDB.insert("bookmark", null, contentValues);
    }

    // 북마크 삭제하기
    public void bookmarkDelete(String studyname) {
        mDB = userStudyDBHelper.getWritableDatabase();
        mDB.delete("bookmark", "studyname=?", new String[]{studyname});
    }

    public void closeUserStudy() {
        userStudyDBHelper.close();
    }
}
