package com.TT.kitcoop.ttuburgi.Data;

import android.content.Context;
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

    public void closeUserStudy() {
        userStudyDBHelper.close();
    }
}
