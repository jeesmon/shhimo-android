package com.mgocsmamerica.apps.shhimo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeesmon on 3/7/15.
 */
public class StringUtils {
    private static List<Map<String, String>> prayersList = null;

    public static List<Map<String, String>> jsonToList(String json) {
        if(prayersList != null) {
            return prayersList;
        }

        List<Map<String, String>> list = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            int len = jsonArray.length();
            for(int i=0; i<len; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Iterator keys = jsonObject.keys();

                Map<String, String> map = new HashMap<>();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    map.put(key, jsonObject.getString(key));
                }

                list.add(map);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        prayersList = list;

        return list;
    }
}
