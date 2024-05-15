package com.example.exampractise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "Post", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table Post(post_id INTEGER primary key AUTOINCREMENT, author String, title String, content String, published_at String)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("Drop table if exists Post");
    }

    public boolean insertData(String author, String title, String content, String published_at) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("author", author);
        data.put("title", title);
        data.put("content", content);
        data.put("published_at", published_at);
        long result = DB.insert("Post", null, data);
        if (result == -1)
            return false;
        return true;
    }

    public boolean updateData(String post_id, String author, String title, String content, String published_at) {
        String Args[] = {post_id};
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor rows = DB.rawQuery("Select * from Post where post_id = ?", Args);
        if (rows.getCount() > 0) {
            ContentValues data = new ContentValues();
            data.put("author", author);
            data.put("title", title);
            data.put("content", content);
            data.put("published_at", published_at);
            long result = DB.update("Post", data, "post_id=?", Args);
            if (result == -1)
                return false;
            return true;
        } else
            return false;
    }

    public boolean deleteData(String post_id) {
        String Args[] = {post_id};
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor rows = DB.rawQuery("Select * from Post where post_id = ?", Args);
        if (rows.getCount() > 0) {
            long result = DB.delete("Post", "post_id=?", Args);
            if (result == -1)
                return false;
            return true;
        } else
            return false;
    }

    public Cursor viewData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor rows = DB.rawQuery("Select * from Post", null);
        return rows;
    }
}
