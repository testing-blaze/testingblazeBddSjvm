package com.automation.ryder.library.core;

import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.actionshub.service.ElementAPI;

import com.automation.ryder.controller.configuration.DeviceDriver;
import com.automation.ryder.controller.reports.LogLevel;
import com.automation.ryder.controller.reports.ReportController;
import org.openqa.selenium.WebDriver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Dates {

    private WebDriver driver;
    private JavaScript javaScript;
    private KeysHandler key;
    private Element elementApi;
    private ReportController reportController;

    public Dates(ElementAPI elementApi, DeviceDriver device, JavaScript javaScript, KeysHandler key, ReportController reportController) {
        this.elementApi = elementApi;
        this.driver = device.getDriver();
        this.javaScript = javaScript;
        this.key = key;
        this.reportController=reportController;
    }

    /**
     * get current date in standard format
     *
     * @author nauman.shahid
     */
    public String currentDate() {
        return currentDateWithOffsetInDesiredFormat(0, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    /**
     * get current date in desired format
     *
     * @param formatter
     */
    public String currentDateInDesiredFormat(DateTimeFormatter formatter) {
        return currentDateWithOffsetInDesiredFormat(0, formatter);
    }

    /**
     * get current date with given offset in format "MM/dd/yyyy"
     *
     * @param offset
     */
    public String currentDateWithOffset(int offset) {
        return currentDateWithOffsetInDesiredFormat(offset, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    /**
     * get current date in desired format with offset
     *
     * @param offset    The number of days since the current day (Negative number for days in the past)
     * @param formatter DateTimeFormatter in the desired format (created with DateTimeFormatter.ofPattern(String))
     * @return current date after offset
     */
    public String currentDateWithOffsetInDesiredFormat(int offset, DateTimeFormatter formatter) {
        return givenDateWithOffsetFromGivenDate(LocalDateTime.now().format(formatter), offset, formatter);
    }

    /**
     * get current date in desired format with offset
     *
     * @param date      The date from which the offset should come
     * @param offset    The number of days since the current day (Negative number for days in the past)
     * @param formatter DateTimeFormatter in the desired format (created with DateTimeFormatter.ofPattern(String))
     * @return date after off set
     */
    public String givenDateWithOffsetFromGivenDate(String date, int offset, DateTimeFormatter formatter) {
        String receivedDate = "";
        try {
            receivedDate = LocalDate.parse(date, formatter).plusDays(offset).format(formatter);
        } catch (Exception e) {
            try {
                receivedDate = LocalDateTime.parse(date, formatter).plusDays(offset).format(formatter);
            } catch (Exception f) {
                 reportController.write(LogLevel.ERROR,
                         "Could not get date for the following reasons: \n" + e.getMessage() + "\n" + f.getMessage());
            }
        }
        return receivedDate;
    }
}
