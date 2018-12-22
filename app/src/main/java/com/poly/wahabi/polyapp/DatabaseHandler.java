package com.poly.wahabi.polyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DatabaseHandler extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        protected static final String DATABASE_NAME = "StudentDatabase";

        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String sql = "CREATE TABLE mydata " +
                    "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "mdate TEXT, " +
                    "posx Double,"+
                    "posy Double,"+
                    "D1 Double,"+
                    "D2 Double,"+
                    "D3 Double,"+
                    "D4 Double,"+
                    "D5 Double,"+
                    "D6 Double)";

            db.execSQL(sql);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            String sql = "DROP TABLE IF EXISTS mydata";
            db.execSQL(sql);

            onCreate(db);
        }

    }


