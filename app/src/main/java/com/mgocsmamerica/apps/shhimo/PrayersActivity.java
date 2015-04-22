package com.mgocsmamerica.apps.shhimo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class PrayersActivity extends ActionBarActivity {
    private static final String TAG = "PrayersActivity";

    public static final String PRAYER_SELECTION = "PRAYER";
    public static final String PRAYER_SELECTION_NAME = "PRAYER_NAME";

    private Context mContext;
    private static String mainSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayers);

        mContext = this;

        String currentSelection = getIntent().getStringExtra(MainActivity.MAIN_SELECTION);
        if(currentSelection != null) {
            mainSelection = currentSelection;
        }

        final List<Map<String, String>> list = StringUtils.jsonToList(getResources().getString(R.string.prayers));

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.two_line_list_item,
                new String[]{"n", "d"}, new int[]{R.id.text1, R.id.text2});

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, mainSelection);
                Map<String, String> item = list.get(position);
                Intent intent = new Intent(mContext, ContentViewActivity.class);
                intent.putExtra(MainActivity.MAIN_SELECTION, mainSelection);
                intent.putExtra(PRAYER_SELECTION, item.get("id"));
                intent.putExtra(PRAYER_SELECTION_NAME, item.get("n"));
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prayers, menu);
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
