package com.automation.ryder.controller.objects;

/**
 * This class is designed to received two row values (String,int) for saving them as custom array
 *
 * @author nauman.shahid
 */
public final class TwoColumnSorting {

    public String key;
    public int value;

    public TwoColumnSorting(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getPair() {
        return "Column 1: " + key + "| Column 2: " + value;
    }

    public String getKey() {
        return this.key;
    }

}
