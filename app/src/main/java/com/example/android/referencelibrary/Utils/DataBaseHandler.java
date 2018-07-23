package com.example.android.referencelibrary.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umang on 30/5/17.
 */


public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RegfereneceManager";
    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_HISTORY = "history";
    private static final String KEY_ID="id";
    private static final String KEY_CATEGORY= "category";
    private static final String KEY_PHOTO_ID="image_id";
    private static final String KEY_BOOK_TYPE="book_type";
    private static final String KEY_URI="uri";
    List<Book> bookList = new ArrayList<Book>();
    private Context context;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + KEY_ID+" INTEGER, "
                + KEY_CATEGORY+" TEXT, "
                + KEY_PHOTO_ID+" INTEGER, "+KEY_BOOK_TYPE+" INTEGER, "+KEY_URI + " TEXT" + ")";
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID+" INTEGER, " + KEY_CATEGORY+" TEXT, "+ KEY_PHOTO_ID+" INTEGER, "+KEY_BOOK_TYPE+" INTEGER, "
                +KEY_URI + " TEXT" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.e("s","Successfull!!");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }


    public void addBooks(Book book,int bookId) {
        if (checkForDuplicate(bookId,TABLE_BOOKS)) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, book.getBookId());
            values.put(KEY_CATEGORY, book.getCategory());
            values.put(KEY_PHOTO_ID, book.getImageId());
            values.put(KEY_BOOK_TYPE,book.getBookType());
            values.put(KEY_URI, book.getUri());
            db.insert(TABLE_BOOKS, null, values);
            Log.d("Book", "ADDed");
        }
    }

    public void addHistory(Book book,int bookId) {
        if (checkForDuplicate(bookId,TABLE_HISTORY)) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, book.getBookId());
            values.put(KEY_CATEGORY, book.getCategory());
            values.put(KEY_PHOTO_ID, book.getImageId());
            values.put(KEY_BOOK_TYPE,book.getBookType());
            values.put(KEY_URI, book.getUri());
            db.insert(TABLE_HISTORY, null, values);
            Log.d("History", "ADDed");
        }
    }

    private boolean checkForDuplicate(int bookId, String tableName){
        SQLiteDatabase db=getReadableDatabase();
        String checkQuery="SELECT * FROM "+tableName+" WHERE "+KEY_ID+"="+bookId;
        Cursor cursor=db.rawQuery(checkQuery,null);
        if (cursor.getCount()==0){
            return true;
        }
        else {
            return false;
        }
    }

    public List<Book> getAllBooksWithUri() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS + " WHERE "+KEY_BOOK_TYPE+" = 1";

        SQLiteDatabase db =getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Book book=new Book();
                book.setBookId(cursor.getInt(0));
                book.setCategory(cursor.getString(1));
                book.setImageId(cursor.getInt(2));
                book.setBookType(cursor.getInt(3));
                book.setUri(cursor.getString(4));

                bookList.add(book);
            } while (cursor.moveToNext());
        }        // return contact list

        return bookList;
    }
    public List<Book> getAllBooksWithPdf() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS + " WHERE "+KEY_BOOK_TYPE+" = 2";
        SQLiteDatabase db =getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Book book=new Book();
                book.setBookId(cursor.getInt(0));
                book.setCategory(cursor.getString(1));
                book.setImageId(cursor.getInt(2));
                book.setBookType(cursor.getInt(3));
                book.setUri(cursor.getString(4));

                bookList.add(book);
            } while (cursor.moveToNext());
        }        // return contact list

        return bookList;
    }

    public List<Book> getAllHistory() {
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;
        SQLiteDatabase db =getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Book book=new Book();
                book.setBookId(cursor.getInt(0));
                book.setCategory(cursor.getString(1));
                book.setImageId(cursor.getInt(2));
                book.setBookType(cursor.getInt(3));
                book.setUri(cursor.getString(4));

                bookList.add(book);
            } while (cursor.moveToNext());
        }
        return bookList;
    }

    public List<Book> searchByCategory(String inputText,int bookType){
        SQLiteDatabase db =getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_BOOKS+ " WHERE "+KEY_BOOK_TYPE +" = "+bookType +" AND "
                +KEY_CATEGORY + " like '%" + inputText + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Book book=new Book();
                book.setBookId(cursor.getInt(0));
                book.setCategory(cursor.getString(1));
                book.setImageId(cursor.getInt(2));
                book.setBookType(cursor.getInt(3));
                book.setUri(cursor.getString(4));
                bookList.add(book);
            } while (cursor.moveToNext());
        }        // return contact list

        return bookList;

    }
    public List<String> categoryAll(){
        SQLiteDatabase db =getWritableDatabase();
        String selectQuery = "SELECT "+KEY_CATEGORY + " FROM " + TABLE_BOOKS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<String> catList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String cat=cursor.getString(0);
                catList.add(cat);

            } while (cursor.moveToNext());
        }        // return contact list

        return catList;

    }


    // code to update the single book
    public int updateBook(Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, book.getBookId());
        values.put(KEY_CATEGORY, book.getCategory());
        values.put(KEY_PHOTO_ID, book.getImageId()); // Contact Name
        values.put(KEY_URI, book.getUri());
        db.insert(TABLE_BOOKS, null, values);

        return db.update(TABLE_BOOKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(book.getBookId()) });
    }

    public void deleteHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
/*        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);*/
        db.execSQL("delete from "+ TABLE_HISTORY);
        db.close();
    }
    public void deleteBook() {
        SQLiteDatabase db = this.getWritableDatabase();
/*        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);*/
        db.execSQL("delete from "+ TABLE_BOOKS);
        db.close();
    }
    public void deleteSingleBook(int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
/*        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);*/
        db.execSQL("delete from "+ TABLE_BOOKS+" WHERE "+KEY_ID+" = "+bookId);
        Log.e("Deleted:","Bookno:"+bookId);
        db.close();
    }
    // Getting contacts Count
    public int getBooksCount() {  SQLiteDatabase db;
        String countQuery = "SELECT  * FROM " + TABLE_BOOKS;
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        return cursor.getCount();
    }


}