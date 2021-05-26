package com.example.capstone_ui_1.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.io.IOException;
import java.util.ArrayList;

import static android.widget.Toast.makeText;

public class ChosunDAO {
    protected static final String TAG = "DataAdapter";

    // TODO : TABLE 이름을 명시해야함
    protected static final String TABLE_NAME = "Chosun";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private com.example.capstone_ui_1.Data.DBHelper mDbHelper;

//    public class ChosunViewHolder extends RecyclerView.ViewHolder {
//        protected TextView building;
//        protected TextView major;
//
//        public ChosunViewHolder(View view) {
//        super(view);
//        this.building = (TextView) view.findViewById(R.id.autoText);
//        }
//    }
    public ChosunDAO(Context context) {
        this.mContext = context;
        mDbHelper = new com.example.capstone_ui_1.Data.DBHelper(mContext);
    }

    public ChosunDAO createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public ChosunDAO open() throws SQLException {
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

    public ArrayList<com.example.capstone_ui_1.Data.ChosunDTO> getTableData() {
        try {
            // Table 이름 -> antpool_bitcoin 불러오기
            String sql = "SELECT * FROM " + TABLE_NAME;

            // 모델 넣을 리스트 생성
            ArrayList<com.example.capstone_ui_1.Data.ChosunDTO> userList = new ArrayList();

            // TODO : 모델 선언
            com.example.capstone_ui_1.Data.ChosunDTO chosun = null;

            Cursor mCur = mDb.rawQuery(sql,null);
            if (mCur != null) {
                // 칼럼의 마지막까지
                while ( mCur.moveToNext() ) {

                    // TODO : 커스텀 모델 생성
                    chosun = new com.example.capstone_ui_1.Data.ChosunDTO();

                    // TODO : Record 기술
                    // id, name, account, privateKey, secretKey, Comment
                    chosun.setB_pno(mCur.getInt(0));
                    chosun.setBuilding(mCur.getString(1));
                    chosun.setMajor(mCur.getString(2));
                    chosun.setProfessor(mCur.getString(3));
                    chosun.setLatitude(mCur.getDouble(4));
                    chosun.setLongtitude(mCur.getDouble(5));

                    // 리스트에 넣기
                    userList.add(chosun);
                }

            }
            return userList;
        } catch (SQLException mSQLException) {
            Log.e(TAG, "getTestData >>" + mSQLException.toString());
            throw mSQLException;
        }
    }

    public double[] getLaLoFromP(String name) {
        double[] LaLo = new double[2];
        String building = null;
        String sql1 = "SELECT Building FROM Chosun WHERE Professor = " + name;

        Cursor mCur1 = mDb.rawQuery(sql1, null);
        if (mCur1 != null) {
            // 칼럼의 마지막까지
            while (mCur1.moveToNext()) {
                building = mCur1.getString(0);
            }
        }

        String sql2 = "SELECT Longtitude, Latitude FROM " + TABLE_NAME + "WHERE Building = " + building;

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
        String building = null;
        String sql1 = "SELECT building FROM chosun WHERE major = " + name;

        Cursor mCur1 = mDb.rawQuery(sql1, null);
        if (mCur1 != null) {
            // 칼럼의 마지막까지
            while (mCur1.moveToNext()) {
                building = mCur1.getString(0);
            }
        }

        String sql2 = "SELECT Longtitude, Latitude FROM " + TABLE_NAME + "WHERE building = " + building;

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
        String sql = "SELECT Longtitude, Latitude FROM Building WHERE Building = " + name;

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
