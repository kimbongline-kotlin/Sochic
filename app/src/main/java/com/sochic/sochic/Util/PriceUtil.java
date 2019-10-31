package com.sochic.sochic.Util;

import java.text.DecimalFormat;

public class PriceUtil {
    public static String set(String str) {
        if (!str.toString().equals("")) {
            if (str != null) {
                if (str.toString().equals("-")) {
                    return "";
                }
                return new DecimalFormat("#,###").format((long) Integer.valueOf(str).intValue());
            }
        }
        return "";
    }
}
