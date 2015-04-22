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

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    public static final String MAIN_SELECTION = "MAIN";
    public static final String PREFACE_SELECTION = "Preface";
    public static final String ABOUT_SELECTION = "About";

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.main));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list.get(position);
                Log.i(TAG, item);
                Intent intent  = null;

                if(PREFACE_SELECTION.equals(item)) {
                    intent = new Intent(mContext, ContentViewActivity.class);
                    intent.putExtra(MAIN_SELECTION, item);
                }
                else {
                    intent = new Intent(mContext, PrayersActivity.class);
                    intent.putExtra(MAIN_SELECTION, item);
                }
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 1) {
            getMenuInflater().inflate(R.menu.menu_main_sunday, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_now) {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            String prayer = null;
            int index = -1;
            if (hour >= 18 && hour < 21) {
                prayer = "Ramsho";
                index = 0;
            }
            else if (hour >= 21 && hour < 24) {
                prayer = "Soutoro";
                index = 1;
            }
            else if (hour >= 0 & hour < 6) {
                prayer = "Lilio";
                index = 2;
            }
            else if (hour >= 6 && hour < 9) {
                prayer = "Sapro";
                index = 3;
            }
            else if (hour >= 9 && hour < 12) {
                prayer = "Thloth";
                index = 4;
            }
            else if (hour >= 12 && hour < 15) {
                prayer = "Phalgeh";
                index = 5;
            }
            else if (hour >= 15 && hour < 18) {
                prayer = "Thsha";
                index = 6;
            }

            String day = null;
            switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    day = "Sunday";
                    break;
                case 2:
                    day = "Monday";
                    break;
                case 3:
                    day = "Tuesday";
                    break;
                case 4:
                    day = "Wednesday";
                    break;
                case 5:
                    day = "Thursday";
                    break;
                case 6:
                    day = "Friday";
                    break;
                case 7:
                    day = "Saturday";
                    break;
            }

            final List<Map<String, String>> list = StringUtils.jsonToList(getResources().getString(R.string.prayers));
            Map<String, String> map = list.get(index);

            Log.i(TAG, prayer);

            Intent intent = new Intent(this, ContentViewActivity.class);
            intent.putExtra(MainActivity.MAIN_SELECTION, day);
            intent.putExtra(PrayersActivity.PRAYER_SELECTION, map.get("id"));
            intent.putExtra(PrayersActivity.PRAYER_SELECTION_NAME, map.get("n"));
            startActivity(intent);

            return true;
        }
        else if(id == R.id.action_info) {
            Intent intent = new Intent(this, ContentViewActivity.class);
            intent.putExtra(MainActivity.MAIN_SELECTION, ABOUT_SELECTION);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
