package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="book_db"; //название бд
    private static final int SCHEMA=1; //ерсия бд
    static final String TABLE_NAME = "bookss";
    public static final String COLUMN_ID="id_book";
    public static final String COLUMN_NAME="book_name";
    public static final String COLUMN_AUTHOR="book_author";




    public dbHelper(@Nullable Context context) {
        super(context, DB_NAME,null,SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+
                "("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COLUMN_NAME + " TEXT, "
                +COLUMN_AUTHOR +" TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public long addBook(String bookName, String bookAuthor){
        //getWritableDatabase() - откроет бд для записи
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, bookName);
        contentValues.put(COLUMN_AUTHOR, bookAuthor);

        long result = db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return result;
    }
    public int deleteBook(long bookId){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME,COLUMN_ID +"=?",new String[]{String.valueOf(bookId)});
        db.close();
        return result;
    }
    public Cursor getAllBooks(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME,null,null,null,null,null,null);
    }
    public Cursor getBookById(int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(bookId)};
        return db.query(TABLE_NAME, null, COLUMN_ID + "=?", selectionArgs, null, null, null);
    }
    public int updateBook(int bookId, String newBookName, String newBookAuthor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, newBookName);
        contentValues.put(COLUMN_AUTHOR, newBookAuthor);
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};
        int result = db.update(TABLE_NAME, contentValues, selection, selectionArgs);
        db.close();
        return result;
    }
}
