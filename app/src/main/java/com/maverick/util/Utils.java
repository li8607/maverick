package com.maverick.util;

/**
 * Created by Administrator on 2017/10/15.
 */
public class Utils {

    public static int getString2Int(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {

        }
        return 0;
    }

}
