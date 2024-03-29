package com.example.betterandfasteremergency.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static String mapToString(Map<String, String> viewMap) {
        String data = "";
        for (String s : viewMap.keySet()) {
            data = data + s + ":" + viewMap.get(s) + "\n";
        }
        return data;
    }

    public static Map<String, String> stringToMap(String viewMap) {
        Map<String, String> dataMap = new HashMap<>();
        for (String s : viewMap.split("\n")) {
            String[] subTokens = s.split(":");
            dataMap.put(subTokens[0], subTokens[1]);
        }
        return dataMap;
    }
}
