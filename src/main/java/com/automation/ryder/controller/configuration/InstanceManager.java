package com.automation.ryder.controller.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InstanceManager {
    private static Map<Class<?>, Object> instanceRecorder = new HashMap<>();

    /**
     * The instance management is based on Singleton Approach
     *
     * @param type ClassName.class
     * @return
     */
    public static <T> T getInstance(Class<T> type) {
        return type.cast(instanceRecorder.get(type));
    }

    /**
     * Record the instance
     *
     * @param type
     * @param object
     */
    public static <T> void recordInstance(Class<T> type, T object) {
        instanceRecorder.put(Objects.requireNonNull(type), Objects.requireNonNull(object));
    }

    /**
     * Flush the instance
     */
    public static <T> void flushInstance() {
        instanceRecorder.clear();
    }


}
