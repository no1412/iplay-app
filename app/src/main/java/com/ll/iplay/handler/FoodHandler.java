package com.ll.iplay.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ll.iplay.gson.FoodDescribe;

import java.util.List;

/**
 * Created by ll on 2017/5/5.
 */

public class FoodHandler {


    public static List<FoodDescribe> handleFoodDescribesResponse (String responseText) {
        List<FoodDescribe> foodDescribes = null;
        Gson gson = new Gson();
        foodDescribes = gson.fromJson(responseText, new TypeToken<List<FoodDescribe>>(){}.getType());
        return foodDescribes;
    }
}
