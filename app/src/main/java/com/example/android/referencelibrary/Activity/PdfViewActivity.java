package com.example.android.referencelibrary.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.referencelibrary.R;

public class PdfViewActivity extends AppCompatActivity  {

    private String PDF_URL ;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("References");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PDF_URL="https://docs.google.com/viewer?url="+getIntent().getStringExtra("uri");
       webView= (WebView) findViewById(R.id.tp);
       webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new AppWebViewClient());
       webView.loadUrl(PDF_URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private class AppWebViewClient extends WebViewClient {
        /*@Override*/
       /* public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            view.loadUrl(url);
            return(false);
        }*/
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
