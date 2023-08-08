package com.example.btm_menu_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    // 테이블 및 컬럼 이름 상수 정의
    public static final String COLUMN_AVERAGE_SMOKING_AMOUNT = "평균_흡연량";
    public static final String COLUMN_CIGARETTE_COUNT = "담배_개수";
    public static final String COLUMN_CIGARETTE_PRICE = "담배_가격";
    public static final String COLUMN_SMOKING_START_DATE = "흡연_시작_일자";
    public static final String COLUMN_AVERAGE_SMOKING_TIME = "평균_흡연_시간";
    private static final String DATABASE_NAME = "smoking.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SMOKING = "smoking";
    private static final String COLUMN_ID = "id";

    private static final String CREATE_TABLE_SMOKING = "CREATE TABLE " + TABLE_SMOKING + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_AVERAGE_SMOKING_AMOUNT + " INTEGER,"
            + COLUMN_CIGARETTE_COUNT + " INTEGER,"
            + COLUMN_CIGARETTE_PRICE + " INTEGER,"
            + COLUMN_SMOKING_START_DATE + " TEXT,"
            + COLUMN_AVERAGE_SMOKING_TIME + " INTEGER"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // smoking 테이블 생성
        db.execSQL(CREATE_TABLE_SMOKING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // smoking 테이블 삭제 후 재생성
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMOKING);
        onCreate(db);
    }

    // 흡연 데이터 삽입
    public void insertSmokingData(SQLiteDatabase db, int averageSmokingAmount, int cigaretteCount, int cigarettePrice,
                                  String smokingStartDate, int averageSmokingTime) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AVERAGE_SMOKING_AMOUNT, averageSmokingAmount);
        values.put(COLUMN_CIGARETTE_COUNT, cigaretteCount);
        values.put(COLUMN_CIGARETTE_PRICE, cigarettePrice);
        values.put(COLUMN_SMOKING_START_DATE, smokingStartDate);
        values.put(COLUMN_AVERAGE_SMOKING_TIME, averageSmokingTime);
        // smoking 테이블에 데이터 삽입
        db.insert(TABLE_SMOKING, null, values);
        db.close();
    }

    // 저장된 데이터 삭제
    public void clearSavedData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // smoking 테이블의 모든 데이터 삭제
        db.execSQL("delete from " + TABLE_SMOKING);
        db.close();
    }

    // smoking 테이블에 저장된 데이터 개수 반환
    public int getSmokeDataCount(SQLiteDatabase db) {
        String query = "SELECT COUNT(*) FROM " + TABLE_SMOKING;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

}
