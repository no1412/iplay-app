package com.ll.iplay.util;

/**
 * Created by ll on 2017/6/4.
 */

public class StringUtil {

    public static boolean isEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str) {
        if (str != null && !str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

}
