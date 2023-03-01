package com.automation.ryder.library.core;

import com.automation.ryder.controller.reports.LogLevel;
import org.apache.log4j.Logger;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author nauman.shahid

 * Properties file reader
 */

public final class Properties_Logs {
    private final Map<String, String> valueStore = new HashMap<>();
    String log4jConfPath = "log4j.properties";
    static final Logger log = Logger.getLogger(LogsController.class.getName());
    Properties OR;
    LogsController logsController;

    public Properties_Logs() {
        logsController = new LogsController();
    }


    /**
     * Read from property file
     */
    public String ReadPropertyFile(String fileName, String parameter) throws IOException {
        OR = new Properties();
        try {
            OR.load(new InputStreamReader(getClass().getResourceAsStream("/" + fileName), StandardCharsets.UTF_8));
        } catch (Exception e) {
            OR.load(new InputStreamReader(getClass().getResourceAsStream("/configCore/" + fileName), StandardCharsets.UTF_8));
        }
        return OR.getProperty(parameter);
    }

    /**
     * Read from property file
     *
     * @return
     */
    public Set<Map.Entry<Object, Object>> getCompleteEntrySetFromPropertyFile(File filePath, String fileName) throws IOException {
        OR = new Properties();
        Reader read = new FileReader(filePath.getAbsolutePath() + File.separatorChar + fileName + "properties");
        try {
            OR.load(read);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OR.entrySet();
    }

    /**
     * reads specified properties file and load the values to the framework save value map
     *
     * @param filePath
     * @param fileName
     * @throws IOException
     * @author nauman.shahid
     */
    public void loadCompleteEntrySetFromPropertiesFileToSaveValueMap(File filePath, String fileName) throws IOException {
        OR = new Properties();
        Reader read = new FileReader(filePath.getAbsolutePath() + File.separatorChar + fileName + "properties");
        try {
            OR.load(read);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OR.entrySet().stream().forEach(set -> valueStore.put((String) set.getKey(), (String) set.getValue()));
    }

    /**
     * Set Property
     */
    public void setProperty(String key, String value) {
        OR = new Properties();
        OR.setProperty(key, value);
    }

    /**
     * Saves a value for future use in a scenario
     *
     * @param key   the key with which to identify the saved value in the future (case-insensitive)
     * @param value the value to save for future use
     */
    public void saveValue(String key, String value) {
        valueStore.put(key.toUpperCase().trim(), value);
    }

    /**
     * Gets a value previously saved in a scenario
     *
     * @param key the key which identifies the saved value (case-insensitive)
     * @return the previously saved value
     */
    public String getValue(String key) {
        return valueStore.get(key.toUpperCase());
    }

    /**
     * get all keys of the saved values
     *
     * @return set of keys
     * @author nauman.shahid
     */
    public Set<String> getAllSavedKeys() {
        return valueStore.keySet();
    }

    /**
     * get all keys of the saved values
     *
     * @return set of keys
     * @author nauman.shahid
     */
    public Map<String, String> getSavedValuesMap() {
        return valueStore;
    }

    public LogsController generateLogs() {
        return logsController;
    }

    /**
     * Inner class to handle logs
     */
    public final class LogsController {

        /**
         * generate logs
         *
         * @param data                     Message
         * @param infoOrWarnOrErrorOrDebug info for informational message, warn for warning messages, debug for debug messages and error for errors
         * @author nauman.shahid
         */
        public void logs(String data, String infoOrWarnOrErrorOrDebug) {
            if ("warn".equalsIgnoreCase(infoOrWarnOrErrorOrDebug)) {
                log.warn(data);
            }
            if ("debug".equalsIgnoreCase(infoOrWarnOrErrorOrDebug)) {
                log.debug(data);
            }
            if ("error".equalsIgnoreCase(infoOrWarnOrErrorOrDebug)) {
                log.error(data);
            } else if ("info".equalsIgnoreCase(infoOrWarnOrErrorOrDebug)) {
                log.info(data);
            }
        }
    }


}
