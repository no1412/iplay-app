package com.ll.iplay.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ll.iplay.gson.EntertainmentDescribe;
import com.ll.iplay.gson.User;

import java.util.List;

/**
 * Created by ll on 2017/5/5.
 */

public class UserHandler {


    public static User handleUserLoginResponse (String responseText) {
        User user = null;
        Gson gson = new Gson();
        user = gson.fromJson(responseText, new TypeToken<User>(){}.getType());
        return user;
    }
}
