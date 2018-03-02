package com.tyaathome.s1mpleweather.ui.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tyaathome.s1mpleweather.ui.provider.columns.CityColumns;

public class CityContentProvider extends ContentProvider {

    private static final String TABLE_NAME= "City";
    private static final String  DATABASE_NAME = "City.db";
    private static final int  DATABASE_VERSION= 1;
    private DatabaseHelper databaseHelper;
    private long accumulate = 0;

    public CityContentProvider() {
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("Create table " + TABLE_NAME + "(" +
                    CityColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CityColumns.ID + " TEXT, " +
                    CityColumns.PARENT_ID + " TEXT, " +
                    CityColumns.PROVINCE + " TEXT, " +
                    CityColumns.CITY + " TEXT, " +
                    CityColumns.COUNTY + " TEXT, " +
                    CityColumns.PINYIN + " TEXT, " +
                    CityColumns.PINYIN_SIMPLE + " TEXT);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return db.delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        long rowId = sqLiteDatabase.insert(TABLE_NAME, "", values);
        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(CityColumns.CONTENT_URI.buildUpon(), rowId).build();
            if(getContext() != null) {
                getContext().getContentResolver().notifyChange(rowUri, null);
                return rowUri;
            }
        }
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        long start = System.currentTimeMillis();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        int numInserted;
        sqLiteDatabase.beginTransaction();
        try {
            for (ContentValues cv : values) {
                long newID = sqLiteDatabase.insertOrThrow(TABLE_NAME, null, cv);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }
            sqLiteDatabase.setTransactionSuccessful();
            if(getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            numInserted = values.length;
        } finally {
            sqLiteDatabase.endTransaction();
        }
        long value = System.currentTimeMillis()-start;
        accumulate += value;
        Log.e("CityTools", value + ", " + accumulate);
        return numInserted;
    }

    @SuppressWarnings({"RedundantConditionalExpression", "ConstantConditions"})
    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return (databaseHelper == null) ? false : true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if(getContext() != null) {
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            qb.setTables(TABLE_NAME);
            Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
        }
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return db.update(TABLE_NAME, values, selection, selectionArgs);
    }
}
