package com.example.translate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "language.db";
    private static final String TABLE_NAME = "content_table";
    private static final String COL_1 = "id_pk";
    private static final String COL_2 = "phraseEn";
    private static final String COL_3 = "phraseCn";
    private static final String COL_4 = "category";
    private static final String COL_5 = "learned";
    private static final String COL_6 = "saved";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PHRASEEN TEXT, " +
                "PHRASECN TEXT, " +
                "CATEGORY TEXT, " +
                "LEARNED BOOLEAN, " +
                "SAVED BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String phraseEn, String phraseCn, String category, Boolean learned, Boolean saved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, phraseEn);
        contentValues.put(COL_3, phraseCn);
        contentValues.put(COL_4, category);
        contentValues.put(COL_5, learned);
        contentValues.put(COL_6, saved);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;


    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;

    }

    public Cursor getCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE category = '" + category + "'", null);
        return res;
    }


    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void updateSave (String phraseEn, Boolean saved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        System.out.println(phraseEn);
        contentValues.put(COL_6, saved);
        db.update(TABLE_NAME, contentValues, "phraseEn = ?", new String[] { phraseEn });
    }

    public Cursor getSave(String phrase){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE phraseEn = '" + phrase + "'", null);
        return res;
    }

}
