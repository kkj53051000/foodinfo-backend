package com.kp.foodinfo.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    public static Map ConverObjectToMap(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        Map resultMap = new HashMap();
        for (int i = 0; i <= fields.length - 1; i++) {
            fields[i].setAccessible(true);
            resultMap.put(fields[i].getName(), fields[i].get(obj));
        }
        return resultMap;
    }
}
