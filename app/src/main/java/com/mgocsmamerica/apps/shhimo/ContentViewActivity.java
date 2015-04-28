package com.mgocsmamerica.apps.shhimo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ContentViewActivity extends ActionBarActivity {
    private static final String TAG = "ContentViewActivity";

    private WebView webView;
    private String asset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_view);

        final String main = getIntent().getStringExtra(MainActivity.MAIN_SELECTION);
        final String prayer = getIntent().getStringExtra(PrayersActivity.PRAYER_SELECTION);
        final String prayerName = getIntent().getStringExtra(PrayersActivity.PRAYER_SELECTION_NAME);

        Log.i(TAG, main + ":" + prayer + ":" + prayerName);

        asset = null;
        String url = null;
        if(MainActivity.PREFACE_SELECTION.equals(main)) {
            asset = "content/english/Preface.html";
            setTitle(main);
        }
        else if(MainActivity.ABOUT_SELECTION.equals(main)) {
            asset = "content/english/About.html";
            setTitle(main);
        }
        else {
            asset = "content/english/" + main + "-" + prayer + ".html";
            setTitle(prayerName);
        }

        url = "file:///android_asset/" + asset;

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_view, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_text_plus) {
            Log.d(TAG, "Text Size +");
            onTextSizeAction(1);
        }
        else if (id == R.id.action_text_minus) {
            Log.d(TAG, "Text Size -");
            onTextSizeAction(-1);
        }
        else if (id == R.id.action_share) {
            Log.i(TAG, "Share Action");
            onShareAction();
        }

        return super.onOptionsItemSelected(item);
    }

    private void onTextSizeAction(int multiplier) {
        webView.loadUrl("JavaScript: resizeText(" + multiplier + ")");
    }

    private void onShareAction(){
        boolean shared = false;
        if(asset != null) {
            String content = null;
            try {
                content = getStringFromFile(asset);
                Log.d(TAG, content);
            }
            catch(Exception e) {
                Log.d(TAG, e.getMessage());
            }

            if(content != null) {
                Intent shareIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getTitle());
                shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(content));

                if(shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                    shared = true;
                }
            }
        }

        if(!shared) {
            Toast.makeText(this, "No applications available to share", Toast.LENGTH_LONG).show();
        }
    }

    private String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private String getStringFromFile (String asset) throws Exception {
        InputStream in = this.getAssets().open(asset);
        String ret = convertStreamToString(in);
        in.close();
        return ret;
    }
}
