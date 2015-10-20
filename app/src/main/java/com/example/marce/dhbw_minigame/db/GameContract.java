package com.example.marce.dhbw_minigame.db;

import android.provider.BaseColumns;

/**
 * Created by marce on 01.09.2015.
 */
public class GameContract {

    public static abstract class HighscoreContract implements BaseColumns {
        public static final String TABLE_NAME           = "REFLEX_GAME_DB";
        public static final String ID                   = "ID";
        public static final String COLUMN_PLAYERNAME    = "player";
        public static final String COLUMN_LEVEL         = "level";
        public static final String COLUMN_POINTS        = "points";
    }

}
