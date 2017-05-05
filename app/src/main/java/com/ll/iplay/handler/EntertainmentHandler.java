package com.ll.iplay.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ll.iplay.gson.EntertainmentDescribe;
import com.ll.iplay.gson.FoodDescribe;

import java.util.List;

/**
 * Created by ll on 2017/5/5.
 */

public class EntertainmentHandler {


    public static List<EntertainmentDescribe> handleEntertainmentDescribesResponse (String responseText) {
        List<EntertainmentDescribe> entertainmentDescribes = null;
        Gson gson = new Gson();
        entertainmentDescribes = gson.fromJson(responseText, new TypeToken<List<EntertainmentDescribe>>(){}.getType());
        return entertainmentDescribes;
    }
}
