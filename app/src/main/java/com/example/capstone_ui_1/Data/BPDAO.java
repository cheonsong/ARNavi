package com.example.capstone_ui_1.Data;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;


public class BPDAO {
    protected static final String TAG = "DataAdapter";

    // TODO : TABLE 이름을 명시해야함
    protected static final String TABLE_NAME = "B_P";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DBHelper mDbHelper;

    public BPDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
    }

    public BPDAO createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public BPDAO open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public double[] getLaLoFromP(String name) {
        double[] LaLo = new double[2];
        String bname = null;
        String sql1 = "SELECT Bname FROM B_P WHERE PNAME = " + name;

        Cursor mCur1 = mDb.rawQuery(sql1, null);
        if (mCur1 != null) {
            // 칼럼의 마지막까지
            while (mCur1.moveToNext()) {
                bname = mCur1.getString(0);
            }
        }

        String sql2 = "SELECT Longtitude, Latitude FROM " + TABLE_NAME + "WHERE Bname = " + bname;

        Cursor mCur2 = mDb.rawQuery(sql2, null);
        if (mCur2 != null) {
            // 칼럼의 마지막까지
            while (mCur2.moveToNext()) {
                LaLo[0] = mCur2.getDouble(0);
                LaLo[1] = mCur2.getDouble(1);
            }
        }

        return LaLo;
    }

    public double[] getLaLoFromM(String name) {
        double[] LaLo = new double[2];
        String bname = null;
        String sql1 = "SELECT Bname FROM B_P WHERE MNAME = " + name;

        Cursor mCur1 = mDb.rawQuery(sql1, null);
        if (mCur1 != null) {
            // 칼럼의 마지막까지
            while (mCur1.moveToNext()) {
                bname = mCur1.getString(0);
            }
        }

        String sql2 = "SELECT Longtitude, Latitude FROM " + TABLE_NAME + "WHERE Bname = " + bname;

        Cursor mCur2 = mDb.rawQuery(sql2, null);
        if (mCur2 != null) {
            // 칼럼의 마지막까지
            while (mCur2.moveToNext()) {
                LaLo[0] = mCur2.getDouble(0);
                LaLo[1] = mCur2.getDouble(1);
            }
        }

        return LaLo;
    }

    public double[] getLaLoFromB(String name){
        double[] LaLo = new double[2];
        String sql = "SELECT Longtitude, Latitude FROM Building WHERE Bname = " + name;

        Cursor mCur = mDb.rawQuery(sql, null);
        if(mCur != null){
            while (mCur.moveToNext()){
                LaLo[0] = mCur.getDouble(0);
                LaLo[1] = mCur.getDouble(1);
            }
        }

        return LaLo;
    }
}
