package com.example.translate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "language.db";

    private static final String TABLE_NAME = "content_table";

    private static final String COL_1 = "id_pk";
    private static final String COL_2 = "phrase";
    private static final String COL_3 = "definition";
    private static final String COL_4 = "category";
    private static final String COL_5 = "learned";
    private static final String COL_6 = "saved";

    private static final String A_TABLE_NAME = "achievement_table";
    private static final String ATT_1 = "id_pk";
    private static final String ATT_2 = "name";
    private static final String ATT_3 = "description";
    private static final String ATT_4 = "currentProgress";
    private static final String ATT_5 = "totalProgress";
    private static final String ATT_6 = "complete";

	private static final String SCORE_TABLE_NAME = "score_table";
	private static final String VAR_1 = "id_pk";
	private static final String VAR_2 = "name";
	private static final String VAR_3 = "score";

	private static final String SENTENCE_TABLE_NAME = "sentence_table";
	private static final String TYP_1 = "id_pk";
	private static final String TYP_2 = "category";
	private static final String TYP_3 = "pageNumber";
	private static final String TYP_4 = "pageTitle";
	private static final String TYP_5 = "content";

	private static final String STORY_TABLE_NAME = "story_table";
	private static final String CHA_1 = "id_pk";
	private static final String CHA_2 = "storyId";
	private static final String CHA_3 = "pageId";
	private static final String CHA_4 = "pageContent";
	private static final String CHA_5 = "answerOne";
	private static final String CHA_6 = "answerTwo";
	private static final String CHA_7 = "answerThree";
	private static final String CHA_8 = "answerFour";


	public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PHRASE TEXT, " +
                "DEFINITION TEXT, " +
                "CATEGORY TEXT, " +
                "LEARNED BOOLEAN, " +
                "SAVED BOOLEAN)");

        db.execSQL("CREATE TABLE " + A_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "DESCRIPTION TEXT, " +
                "CURRENTPROGRESS TEXT, " +
                "TOTALPROGRESS TEXT, " +
                "COMPLETE BOOLEAN)");

		db.execSQL("CREATE TABLE " + SCORE_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"NAME TEXT, " +
				"SCORE INTEGER)");

		db.execSQL("CREATE TABLE " + SENTENCE_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"CATEGORY TEXT, " +
				"PAGENUMBER INTEGER, " +
				"PAGETITLE TEXT, " +
				"CONTENT TEXT)");

		db.execSQL("CREATE TABLE " + STORY_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"STORYID INTEGER, " +
				"PAGEID INTEGER, " +
				"PAGECONTENT TEXT, " +
				"ANSWERONE TEXT, " +
				"ANSWERTWO TEXT, " +
				"ANSWERTHREE TEXT, " +
				"ANSWERFOUR TEXT)");
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + A_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SCORE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SENTENCE_TABLE_NAME);

        onCreate(db);
    }

    public boolean insertData(String phrase, String definition, String category, boolean learned, boolean saved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, phrase);
        contentValues.put(COL_3, definition);
        contentValues.put(COL_4, category);
        contentValues.put(COL_5, learned);
        contentValues.put(COL_6, saved);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertAchievementData(String name, String description, int currentProgress, int totalProgress, boolean complete) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ATT_2, name);
        contentValues.put(ATT_3, description);
        contentValues.put(ATT_4, currentProgress);
        contentValues.put(ATT_5, totalProgress);
        contentValues.put(ATT_6, complete);
        long result = db.insert(A_TABLE_NAME, null, contentValues);
        return result != -1;
    }

	public boolean insertScoreData(String name, int count) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(VAR_2, name);
		contentValues.put(VAR_3, count);
		long result = db.insert(SCORE_TABLE_NAME, null, contentValues);
		return result != -1;
	}

	public boolean insertContentData(String category, int pageNumber, String pageTitle, String content) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(TYP_2, category);
		contentValues.put(TYP_3, pageNumber);
		contentValues.put(TYP_4, pageTitle);
		contentValues.put(TYP_5, content);
		long result = db.insert(SENTENCE_TABLE_NAME, null, contentValues);
		return result != -1;
	}

	public boolean insertStoryData(int storyId, int pageId, String pageContent, String answerOne, String answerTwo, String answerThree, String answerFour) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(CHA_2, storyId);
		contentValues.put(CHA_3, pageId);
		contentValues.put(CHA_4, pageContent);
		contentValues.put(CHA_5, answerOne);
		contentValues.put(CHA_6, answerTwo);
		contentValues.put(CHA_7, answerThree);
		contentValues.put(CHA_8, answerFour);
		long result = db.insert(STORY_TABLE_NAME, null, contentValues);
		return result != -1;
	}

	public Cursor getAllData() {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
	}

    public Cursor getAchievements() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + A_TABLE_NAME, null);
    }

	public Cursor getScores() {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery("SELECT * FROM " + SCORE_TABLE_NAME, null);
	}

	public Cursor getContentCategory(String category, int pageNumber) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery("SELECT * FROM " + SENTENCE_TABLE_NAME + " WHERE category = '" + category + "' AND pagenumber = '" + pageNumber +"'" , null);
	}

	public int getContentCategoryPageCount(String category, int pageNumber) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT MAX(pagenumber) FROM " + SENTENCE_TABLE_NAME + " WHERE category = '" + category + "'", null);
		c.moveToFirst();
		return c.getInt(0);
	}



    ////////////////////////////////////////////

    public Cursor getCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE category = '" + category + "'", null);
    }

    public Cursor getSaved() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE saved = 1", null);
    }

    public Cursor getLearned() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE learned = 1", null);
    }

    public Cursor getStory(int storyId) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery("SELECT * FROM " + STORY_TABLE_NAME + " WHERE storyId = " + storyId + "", null);
	}

	public Cursor getSaveStatus(String phrase) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE phrase = '" + phrase + "'", null);
	}

	public Cursor getLearnedStatus(String phrase) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE phrase = '" + phrase + "'", null);
	}

	public void updateSave(String phrase, boolean saved) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_6, saved);
		db.update(TABLE_NAME, contentValues, "phrase = ?", new String[]{phrase});
	}

	public void updateLearned(String phrase, boolean learned) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(COL_5, learned);
		db.update(TABLE_NAME, contentValues, "phrase = ?", new String[]{phrase});
	}

	public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
    }

	////

    public void clearMyList() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " WHERE category = 'custom'");
    }

    public void deletePhrase(String phraseEn) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " WHERE category = 'custom' AND phraseEn = '" + phraseEn + "' ");
    }

	////

	public String returnAchievementDescription(String achievementName) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("SELECT description FROM " + A_TABLE_NAME + " WHERE name = '" + achievementName + "' ", null);
		res.moveToFirst();
		return res.getString(0);

	}

    public boolean progressAchievement(String achievementName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor res = db.rawQuery("SELECT currentProgress, totalProgress, complete FROM " + A_TABLE_NAME + " WHERE name = '" + achievementName + "' ", null);
        res.moveToFirst();
        int curPro = Integer.valueOf(res.getString(0));
        int totPro = Integer.valueOf(res.getString(1));
        String com = res.getString(2);
        res.close();
        if (com.equals("0")) {                   //if has not been achieved, add 1 to progress
            curPro++;
            contentValues.put(ATT_4, curPro);
            db.update(A_TABLE_NAME, contentValues, "name = ?", new String[]{achievementName});

            if (curPro == totPro) {
                contentValues.put(ATT_6, "1");
                db.update(A_TABLE_NAME, contentValues, "name = ?", new String[]{achievementName});
                updateScore(achievementName);
                addTokens();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean checkAchievementStatus(String achievementName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT complete FROM " + A_TABLE_NAME + " WHERE name = '" + achievementName + "' ", null);
        res.moveToFirst();
        return res.getString(0).equals("1");
    }

    public Cursor getAchieved() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + A_TABLE_NAME + " WHERE complete = '1'", null);
    }

	////

    public void updateScore(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + SCORE_TABLE_NAME + " SET score = score + 1 WHERE name = '" + name + "'");

	}

	public int getTokens() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor res = db.rawQuery("SELECT * FROM " + SCORE_TABLE_NAME + " WHERE name = 'Tokens'", null);
		res.moveToFirst();
		int tokens = res.getInt(2);
		return tokens;
	}

	public void addTokens() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE " + SCORE_TABLE_NAME + " SET score = score + 200 WHERE name = 'Tokens'");

	}

	public void spendTokens() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("UPDATE " + SCORE_TABLE_NAME + " SET score = score - 1000 WHERE name = 'Tokens'");

	}

}
