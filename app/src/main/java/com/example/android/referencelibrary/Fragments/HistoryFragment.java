package com.example.android.referencelibrary.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.referencelibrary.R;
import com.example.android.referencelibrary.Utils.Book;
import com.example.android.referencelibrary.Utils.BookAdapter;
import com.example.android.referencelibrary.Utils.DataBaseHandler;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {
    BookAdapter bookAdapter;
    List<Book> bookList=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_history, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          if(item.getItemId()==R.id.delete){
            deleteHistory();
         }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.rv_history);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DataBaseHandler dataBaseHandler=new DataBaseHandler(getContext());
        bookList=dataBaseHandler.getAllHistory();
        bookAdapter=new BookAdapter(getContext(),bookList,0);
        recyclerView.setAdapter(bookAdapter);
        return view;
    }

    public void deleteHistory(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Do you want to clear history??");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DataBaseHandler dataBaseHandler=new DataBaseHandler(getContext());
                        dataBaseHandler.deleteHistory();
                        bookList=dataBaseHandler.getAllHistory();
                        bookAdapter.notifyData(bookList);
                        Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();

                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
