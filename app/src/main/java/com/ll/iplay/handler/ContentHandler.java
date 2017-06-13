package com.ll.iplay.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ll.iplay.gson.ContentDescribe;
import com.ll.iplay.gson.FoodDescribe;

import java.util.List;

/**
 * Created by ll on 2017/5/5.
 */

public class ContentHandler {


    public static List<ContentDescribe> handleContentDescribesResponse (String responseText) {
        List<ContentDescribe> contentDescribes = null;
        Gson gson = new Gson();
        contentDescribes = gson.fromJson(responseText, new TypeToken<List<ContentDescribe>>(){}.getType());
        return contentDescribes;
    }
}
