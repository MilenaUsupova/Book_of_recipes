package com.example.book_of_recipes;
import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "db_recipes.db";
    private static final int DATABASE_VERSION = 6;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }
}

