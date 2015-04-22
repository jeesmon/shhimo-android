package com.mgocsmamerica.apps.shhimo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class ContentViewActivity extends ActionBarActivity {
    private static final String TAG = "ContentViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);

        final String main = getIntent().getStringExtra(MainActivity.MAIN_SELECTION);
        final String prayer = getIntent().getStringExtra(PrayersActivity.PRAYER_SELECTION);
        final String prayerName = getIntent().getStringExtra(PrayersActivity.PRAYER_SELECTION_NAME);

        Log.i(TAG, main + ":" + prayer + ":" + prayerName);

        String url = null;
        if(MainActivity.PREFACE_SELECTION.equals(main)) {
            url = "file:///android_asset/content/english/Preface.html";
            setTitle(main);
        }
        else if(MainActivity.ABOUT_SELECTION.equals(main)) {
            url = "file:///android_asset/content/english/About.html";
            setTitle(main);
        }
        else {
            url = "file:///android_asset/content/english/" + main + "-" + prayer + ".html";
            setTitle(prayerName);
        }

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
