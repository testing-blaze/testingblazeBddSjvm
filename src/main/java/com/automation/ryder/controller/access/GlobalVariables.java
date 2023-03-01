package com.automation.ryder.controller.access;

import java.util.LinkedHashMap;
import java.util.Map;

public class GlobalVariables {
    private static Map<String, Object> globalVariable = new LinkedHashMap<>();

    public static Object getVariable(String key) {
        return (globalVariable.get(key));
    }

    public static void deleteRecord(String key) {
        globalVariable.remove(key);
    }

    public static void setVariable(String key, Object value) {
        globalVariable.put(key, value);
    }
    public static Boolean hasVariable(String key) {
        return (globalVariable.containsKey(key));
    }

}
