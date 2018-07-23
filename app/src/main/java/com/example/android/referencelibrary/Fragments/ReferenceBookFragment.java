package com.example.android.referencelibrary.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
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


public class ReferenceBookFragment extends Fragment {

    private List<Book> bookList=new ArrayList<>();
    private BookAdapter bookAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu, menu);

        SearchView.OnQueryTextListener queryTextListener;
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView;

        searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String query) {
                    Log.i("onQueryTextChange", query);
                    if (query!=null){
                        DataBaseHandler dataBaseHandler = new DataBaseHandler(getContext());
                        List catList = dataBaseHandler.searchByCategory(query,2);
                        bookAdapter.notifyData(catList);

                    }


                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(getContext());
                    List catList = dataBaseHandler.searchByCategory(query,2);
                    bookAdapter.notifyData(catList);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

            MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(getContext());
                    List bList=dataBaseHandler.getAllBooksWithUri();
                    if (bookList.size()==0){
                        Toast.makeText(getContext(), "No Books Found", Toast.LENGTH_SHORT).show();
                    }
                    bookAdapter.notifyData(bList);
                    return true;
                }
            });


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.search){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_reference_book, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.rv_books_pdf);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DataBaseHandler dataBaseHandler=new DataBaseHandler(getContext());
        bookList=dataBaseHandler.getAllBooksWithPdf();
        bookAdapter=new BookAdapter(getContext(),bookList,1);
        recyclerView.setAdapter(bookAdapter);
        return view;
    }
    private void createBookData(){
        DataBaseHandler dataBaseHandler=new DataBaseHandler(getContext());
        int index=dataBaseHandler.getBooksCount();

    }



}
