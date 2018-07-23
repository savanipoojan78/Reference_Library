package com.example.android.referencelibrary.Utils;

/**
 * Created by $hree on 28-05-2017.
 */

public class Book {
    private int bookId;
    private int imageId;
    private String category;
    private String uri;
    private int bookType;

    public Book(){

    }
    public Book(int i,String cat,int aa,int bookType, String s) {
        bookId=i;
        category=cat;
        imageId=aa;
        this.bookType=bookType;
        uri=s;
    }
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBookType() {

        return bookType;
    }
    public void setBookType(int bookType) {
        this.bookType = bookType;
    }
}
