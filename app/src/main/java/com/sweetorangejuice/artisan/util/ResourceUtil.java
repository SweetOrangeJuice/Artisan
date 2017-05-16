package com.sweetorangejuice.artisan.util;

import java.lang.reflect.Field;

/**
 * Created by fortuneliu on 2017/5/16.
 */

public class ResourceUtil {

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
