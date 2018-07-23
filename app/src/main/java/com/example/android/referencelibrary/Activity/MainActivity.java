package com.example.android.referencelibrary.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.android.referencelibrary.Fragments.AboutUsFragment;
import com.example.android.referencelibrary.Fragments.AccountFragment;
import com.example.android.referencelibrary.Fragments.HelpFragment;
import com.example.android.referencelibrary.Fragments.HistoryFragment;
import com.example.android.referencelibrary.Fragments.ReferenceBookFragment;
import com.example.android.referencelibrary.Fragments.ReferenceFragment;
import com.example.android.referencelibrary.R;
import com.example.android.referencelibrary.Utils.Book;
import com.example.android.referencelibrary.Utils.DataBaseHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public int temp=0;
    List<Book> catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDrawer();
        setFragment(new ReferenceFragment());
        DataBaseHandler dataBaseHandler=new DataBaseHandler(this);


    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }



    private void setDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("References");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (temp!=0)
        {
            setFragment(new ReferenceFragment());
            getSupportActionBar().setTitle("References");
                temp=0;
        }
        else
        {
            super.onBackPressed();
            this.finish();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       int id = item.getItemId();
        if (id == R.id.search) {

            return true;
        }

        else if(id==R.id.log_out){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setMessage("Do you want to Log out??");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent=new Intent(getBaseContext(),LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

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


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            temp=0;
            setFragment(new ReferenceFragment());
            getSupportActionBar().setTitle("References");

        } else if (id == R.id.nav_acc) {
            temp=2;
            setFragment(new AccountFragment());
            getSupportActionBar().setTitle("Account");

        } else if (id == R.id.nav_history) {
            temp=2;
            setFragment(new HistoryFragment());
            getSupportActionBar().setTitle("History");

        } else if (id == R.id.nav_help) {
            temp=2;
            setFragment(new HelpFragment());
            getSupportActionBar().setTitle("Help");

        } else if (id == R.id.nav_about){
            temp=2;
            setFragment(new AboutUsFragment());
            getSupportActionBar().setTitle("About us");
        }else if (id == R.id.nav_viewer){
            temp=2;
            setFragment(new ReferenceBookFragment());
            getSupportActionBar().setTitle("PDF Viewer");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}


