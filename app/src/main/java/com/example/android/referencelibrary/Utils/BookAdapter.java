package com.example.android.referencelibrary.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.referencelibrary.Activity.PdfViewActivity;
import com.example.android.referencelibrary.R;

import java.util.List;

/**
 * Created by $hree on 28-05-2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    List<Book> bookList;
    Context context;
    int flag;
    public BookAdapter(Context context,List bookList, int flag){

        this.context=context;
        this.flag=flag;
        this.bookList=bookList;
    }



    public void notifyData(List bookList){
        this.bookList=bookList;
        notifyDataSetChanged();
    }
    public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView book;
        public BookHolder(View itemView) {
            super(itemView);
            book= (ImageView) itemView.findViewById(R.id.img_book);
            book.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int p=getAdapterPosition();
            Book book=bookList.get(p);
            if (book.getBookType()==1) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(book.getUri()));
                context.startActivity(intent);
                if (flag == 1) {
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                    dataBaseHandler.addHistory(book, book.getBookId());
                }
            }
            else if (book.getBookType()==2){
                Intent intent=new Intent(context, PdfViewActivity.class);
                intent.putExtra("uri",book.getUri());
                context.startActivity(intent);
                if (flag == 1) {
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                    dataBaseHandler.addHistory(book, book.getBookId());
                }
            }

        }

    }
    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_books, parent, false);

        return new BookHolder(itemView);

    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        Book book=bookList.get(position);
        int bookId=book.getImageId();
        holder.book.setImageResource(bookId);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


}
