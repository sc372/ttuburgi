package com.TT.kitcoop.ttuburgi.Data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sc372 on 4/20/17.
 */

public class UserStudyDBHelper extends SQLiteOpenHelper {
    private static String TAG = "UserStudyDBHelper"; // Lpgcat에 출력할 태그 이름
    private static String DB_PATH = ""; // 디바이스 장치에서 데이터베이스의 경로
    private static String DB_NAME = "userstudy.db"; // 데이터베이스 이름
    private SQLiteDatabase mDatabase;
    private final Context mContext;

    public UserStudyDBHelper(Context context) {
        super(context, DB_NAME, null, 1); // 1은 데이터베이스 버전
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/;";
        }
        this.mContext = context;
    }

    public void createUserDatabase() throws IOException {
        // 데이터베이스가 없으면 assets 폴더에서 복사해온다
        boolean mDataBaseExist = checkUSerDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                copyUserDataBase();
                Log.e(TAG, "createUserDatabase database created");
            } catch (Exception e) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    // /data/data/com.TT.kitcoop.ttuburgi/databases/userstudy.db 가 있는지 확인
    private boolean checkUSerDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        System.out.println(dbFile);
        return dbFile.exists();
    }

    // assets 폴더에서 데이터베이스를 복사한다.
    private void copyUserDataBase() throws IOException {
        InputStream mInputStream = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutputStream = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;

        while ((mLength = mInputStream.read(mBuffer)) > 0) {
            mOutputStream.write(mBuffer, 0, mLength);
        }
        mOutputStream.flush();
        mOutputStream.close();
        mInputStream.close();
    }

    // 데이터베이스를 열어서 쿼리를 쓸 수 있게 만든다
    public boolean openUserDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDatabase != null;
    }


    public synchronized void close() {
        if (mDatabase != null)
            mDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
