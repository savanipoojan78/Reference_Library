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


public class ReferenceFragment extends Fragment {
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
                        List catList = dataBaseHandler.searchByCategory(query,1);
                        bookAdapter.notifyData(catList);

                    }


                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    DataBaseHandler dataBaseHandler = new DataBaseHandler(getContext());
                    List catList = dataBaseHandler.searchByCategory(query,1);
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

        View view= inflater.inflate(R.layout.fragment_reference, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.rv_books);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DataBaseHandler dataBaseHandler=new DataBaseHandler(getContext());
        createBookData();
        bookList=dataBaseHandler.getAllBooksWithUri();
        bookAdapter=new BookAdapter(getContext(),bookList,1);
        recyclerView.setAdapter(bookAdapter);
        return view;
    }
    private void createBookData(){
        DataBaseHandler dataBaseHandler=new DataBaseHandler(getContext());
        dataBaseHandler.addBooks(new Book(1,"java",R.drawable.java,1,"http://ebook-dl.com/book/2914"),1);
        dataBaseHandler.addBooks(new Book(2,"Artificial Intelligence",R.drawable.ai,1,"https://i4iam.files.wordpress.com/2013/08/artificial-intelligence-by-rich-and-knight.pdf"),2);
        dataBaseHandler.addBooks(new Book(4,"EPE",R.drawable.epe,1,"http://www.google.co.in/url?sa=t&rct=j&q=&esrc=s&source=web&cd=2&ved=0ahUKEwiSjPy6ipDUAhXKLI8KHd5CDucQFggpMAE&url=http%3A%2F%2Fwww.electricalengineering-book.com%2Febook%2Fagrawal-13.pdf&usg=AFQjCNGcsSxECJ8OkDVVklkMSyUfdyX6mg&sig2=WkDr04Ko0z1aOA0RUd8NtA&cad=rja"),4);
        dataBaseHandler.addBooks(new Book(5,"Python",R.drawable.py,1,"http://ebook-dl.com/book/271"),5);
        dataBaseHandler.addBooks(new Book(6,"Linux",R.drawable.linux,1,"https://www.google.co.in/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&ved=0ahUKEwih2sOIjJDUAhVMt48KHeAjDmUQFggjMAA&url=https%3A%2F%2Fdoc.lagout.org%2Foperating%2520system%2520%2Flinux%2FBeginning%2520Linux%2520Programming%252C%25204%2520Ed.pdf&usg=AFQjCNFELB73Ce8UCUIN7Io28aA61GUMig&sig2=A9Zitv0XLmWd0bzvINuVqQ&cad=rja"),6);
        dataBaseHandler.addBooks(new Book(7,"Data Structure",R.drawable.ds,1,"http://ebook-dl.com/book/2215"),7);


        dataBaseHandler.addBooks(new Book(8,"internet of things",R.drawable.iot,1,"http://ebook-dl.com/book/7099"),8);
        dataBaseHandler.addBooks(new Book(9,"java script",R.drawable.js,1,"http://ebook-dl.com/book/6218"),9);
        dataBaseHandler.addBooks(new Book(10,"cpp",R.drawable.cpp,1,"http://ebook-dl.com/book/49"),10);
        dataBaseHandler.addBooks(new Book(11,"c",R.drawable.c,1,"https://docs.google.com/viewer?a=v&pid=sites&srcid=ZGVmYXVsdGRvbWFpbnxhc2hpa2FtYXRpbnxneDo2YmM0ZWYyOTJkMjBlYWNi"),11);
        dataBaseHandler.addBooks(new Book(12,"html",R.drawable.h5c3,1,"http://ebook-dl.com/book/10066"),12);


        dataBaseHandler.addBooks(new Book(13,"android",R.drawable.androidd,1,"https://www.tutorialspoint.com/android/android_tutorial.pdf"),13);
        dataBaseHandler.addBooks(new Book(14,"auto",R.drawable.aee_auto,1,"https://www.google.co.in/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&ved=0ahUKEwikvdDDqKvUAhWBro8KHXTZDtYQFggnMAA&url=http%3A%2F%2Ffmcet.in%2FAUTO%2FAT6502_uw.pdf&usg=AFQjCNEo3RJtzgqdwWnlKluMFZmHI0FvoQ&sig2=ndAXXhNhFnZy3ItLBzlbkA&cad=rja"),14);
        dataBaseHandler.addBooks(new Book(15,"concrete",R.drawable.ct_civil,1,"https://drive.google.com/file/d/0B-IbNSAhk4D2aVRGd1h4Z0QtbU0/view"),15);
        dataBaseHandler.addBooks(new Book(16,"digitalcir",R.drawable.fdc_ee,1,"https://docs.google.com/document/d/1Th2RpPrmGMjarnxxEEEEjMDvep91qlzsh7N41m8yDm4/edit"),16);
        dataBaseHandler.addBooks(new Book(17,"powerele",R.drawable.pe_ee,1,"http://vit.edu.in/sites/default/files/AET_2016.pdf"),17);
        dataBaseHandler.addBooks(new Book(18,"theorymach",R.drawable.tm_me,1,"https://doc-04-2s-docs.googleusercontent.com/docs/securesc/kj2hd6cbeojss9f0ta5ls30khc04g57e/g44iladmlh985ak05pv9nso1tn6lg04o/1496815200000/12816252768330446996/16405587962428452201/0B7KA3NC3P6tNVVpFbmJDa3F1Z2c?e=download&nonce=o0qhtst5im7ok&user=16405587962428452201&hash=cne60j9ib4feb3q3kd7fi8p0nmsputom -- http://www.mechanicalgeek.com/theory-of-machine-pdf-rs-khurmi-book-download/"),18);
        dataBaseHandler.addBooks(new Book(19,"thermodynamics",R.drawable.td_me,1,"http://download2194.mediafire.com/58a60pf2jucg/wx8hsr5494rirto/Thermodynamics-P-K-Nag.pdf -- http://www.mediafire.com/file/wx8hsr5494rirto/Thermodynamics-P-K-Nag.pdf"),19);

        dataBaseHandler.addBooks(new Book(20,"java",R.drawable.java,2,"http://iiti.ac.in/people/~tanimad/JavaTheCompleteReference.pdf"),20);
        dataBaseHandler.addBooks(new Book(21,"android",R.drawable.androidd,2,"https://www.tutorialspoint.com/android/android_tutorial.pdf"),21);

        dataBaseHandler.addBooks(new Book(22,"Artificial Intelligence",R.drawable.ai,2,"http://itechnocrates.weebly.com/uploads/5/5/2/7/55270269/artificial_intelligence.pdf"),22);
        dataBaseHandler.addBooks(new Book(23,"EPE",R.drawable.epe,2,"http://s1.downloadmienphi.net/file/downloadfile6/192/1385054.pdf"),23);
        dataBaseHandler.addBooks(new Book(24,"Python",R.drawable.py,2,"http://www.bogotobogo.com/python/files/pytut/Python%20Essential%20Reference,%20Fourth%20Edition%20(2009).pdf"),24);
        dataBaseHandler.addBooks(new Book(25,"Linux",R.drawable.linux,2,"https://doc.lagout.org/operating%20system%20/linux/Beginning%20Linux%20Programming%2C%203%20Ed.pdf"),25);
        dataBaseHandler.addBooks(new Book(26,"Data Structure",R.drawable.ds,2,"https://doc.lagout.org/programmation/C/Data%20Structures%20Using%20C%20%5BISRD%20Group%202006%5D.pdf"),26);


        dataBaseHandler.addBooks(new Book(27,"internet of things",R.drawable.iot,2,"http://www85.zippyshare.com/d/ZtM8a9ZA/38420/Packt.Internet.of.Things.Programming.with.JavaScript.1785888560.rar"),27);
        dataBaseHandler.addBooks(new Book(28,"cpp",R.drawable.cpp,2,"http://amarblog.yolasite.com/resources/pdf/c%2B%2B.pdf"),28);
        dataBaseHandler.addBooks(new Book(29,"c",R.drawable.c,2,"https://dspace.lboro.ac.uk/dspace-jspui/bitstream/2134/10054/6/Programming-in-ANSI-C.pdf"),29);
    }




}
