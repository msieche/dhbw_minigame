package com.example.marce.dhbw_minigame.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.marce.dhbw_minigame.Highscore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marce on 01.09.2015.
 */
public class GameDBHelper extends SQLiteOpenHelper{

    public GameDBHelper(Context context) {
        super(context, "REFLEX_GAME_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + GameContract.HighscoreContract.TABLE_NAME
                + " ( " +
                " " + GameContract.HighscoreContract.ID + " INTEGER PRIMARY_KEY," +
                " " + GameContract.HighscoreContract.COLUMN_PLAYERNAME + " NVARCHAR(200)," +
                " " + GameContract.HighscoreContract.COLUMN_LEVEL + " INTEGER," +
                " " + GameContract.HighscoreContract.COLUMN_POINTS + " INTEGER" +
                ");");
    }

    public long addHighscore(String player, int level, int points) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GameContract.HighscoreContract.COLUMN_PLAYERNAME, player);
        values.put(GameContract.HighscoreContract.COLUMN_LEVEL, level);
        values.put(GameContract.HighscoreContract.COLUMN_POINTS, points);

        long newRowId = db.insert(GameContract.HighscoreContract.TABLE_NAME, null, values);
        return newRowId;
    }

    public void clearHighscores() {
        SQLiteDatabase db = getWritableDatabase();
        String dbClearStatement =   "DELETE FROM " + GameContract.HighscoreContract.TABLE_NAME + ";" +
                                    "VACUUM;";
        db.execSQL(dbClearStatement);
    }

    public int getHighscoreForPlayer(String playername) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                GameContract.HighscoreContract.TABLE_NAME,
                new String[] {
                    GameContract.HighscoreContract.COLUMN_POINTS },
                GameContract.HighscoreContract.COLUMN_PLAYERNAME + "=?",
                new String[] { playername },
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        return Integer.parseInt(cursor.getString(0));
    }

    public Highscore getHighscoreForPlayerAsHighscore(String playername) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                GameContract.HighscoreContract.TABLE_NAME,
                new String[] {
                        GameContract.HighscoreContract.COLUMN_PLAYERNAME,
                        GameContract.HighscoreContract.COLUMN_LEVEL,
                        GameContract.HighscoreContract.COLUMN_POINTS },
                GameContract.HighscoreContract.COLUMN_PLAYERNAME + "=?",
                new String[] { playername },
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        Highscore score = new Highscore(cursor.getString(0), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)));
        cursor.close();
        return score;
    }

    public void getAllHighscoreEntries() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                GameContract.HighscoreContract.TABLE_NAME,
                new String[] {
                        GameContract.HighscoreContract.COLUMN_PLAYERNAME,
                        GameContract.HighscoreContract.COLUMN_LEVEL,
                        GameContract.HighscoreContract.COLUMN_POINTS },
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();
    }

    public ArrayList<Highscore> getAllHighscoreEntriesAsList() {
        ArrayList<Highscore> highscoreList = new ArrayList<Highscore>();

        String selectQuery = "SELECT  * FROM " + GameContract.HighscoreContract.TABLE_NAME;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Highscore highscore = new Highscore();
                highscore.setPlayername(cursor.getString(1));
                highscore.setLevel(Integer.parseInt(cursor.getString(2)));
                highscore.setPoints(Integer.parseInt(cursor.getString(3)));

                highscoreList.add(highscore);
            } while (cursor.moveToNext());
        }

        return highscoreList;
    }

    public int getHighscoresCount() {
        String countQuery = "SELECT * FROM " + GameContract.HighscoreContract.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
