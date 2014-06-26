package com.breanawiggins.quizdom;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseHelper {
    private static final String DATABASE_NAME = "Quizdom.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Accounts";
    private static final String TABLE_FRIENDS = "Friends";
    private final Context context;
    private final SQLiteDatabase db;
    private final SQLiteStatement insertStmt;
    private static final String INSERT = "insert into " + TABLE_NAME
            + "(name, password) values (?, ?)";

    public DatabaseHelper(Context context) {
        this.context = context;
        QuizdomOpenHelper openHelper = new QuizdomOpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    public long insert(String name, String password) {
        this.insertStmt.bindString(1, name);
        this.insertStmt.bindString(2, password);
        return this.insertStmt.executeInsert();
    }

    public void deleteAll() {

        this.db.delete(TABLE_NAME, null, null);
    }

    public List<String> selectAll(String username, String password) {
        List<String> list = new ArrayList<String>();
        Cursor cursor = this.db.query(TABLE_NAME, new String[] { "name",
                "password" }, "name = '" + username + "' AND password= '"
                + password + "'", null, null, null, "name desc");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    private static class QuizdomOpenHelper extends SQLiteOpenHelper {
        QuizdomOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, password TEXT)");
            
            db.execSQL("CREATE TABLE " + TABLE_FRIENDS
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "friendid INTEGER,"
                    + "FOREIGN KEY(friendid) REFERENCES Accounts(id))");
            
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.w("Example",
                    "Upgrading database; this will drop and recreate the tables.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            this.onCreate(db);
        }
    }
}
