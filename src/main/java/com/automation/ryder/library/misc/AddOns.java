package com.automation.ryder.library.misc;

import com.automation.ryder.controller.actionshub.abstracts.Action;
import com.automation.ryder.controller.actionshub.abstracts.Element;
import com.automation.ryder.controller.actionshub.service.ActionsHub;
import com.automation.ryder.controller.actionshub.service.ElementAPI;
import com.automation.ryder.library.core.JavaScript;
import com.automation.ryder.library.core.KeysHandler;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

public class AddOns {
    private JavaScript javaScript;
    private Element elementApi;
    private Action actionsHub;
    private KeysHandler key;

    public AddOns(JavaScript js, ElementAPI elementAPI, ActionsHub actionsHub,KeysHandler key) {
        elementApi = elementAPI;
        this.actionsHub = actionsHub;
        this.javaScript = js;
        this.key = key;
    }

    /**
     * generates random number
     * @return integer random number
     * @author jitendra.pisal
     */
    public int getRandomNumber() {
        return (int) getRandomNumberInRange(1000, 50000) + (int) getRandomNumberInRange(51000, 90000);
    }

    /**
     * generates random number
     * @return String random number
     * @author jitendra.pisal
     */
    public String getStringRandomNumber() {
        return Integer.toString((int) getRandomNumberInRange(1000, 50000)) + (int) getRandomNumberInRange(51000, 90000);
    }

    /**
     * Get random number within a range.
     *
     * @param minimum
     * @param maximum
     * @param <T>     Handles Double and int at the moment
     * @return random number
     * @author nauman.shahid
     */
    public synchronized  <T extends Number> Number getRandomNumberInRange(T minimum, T maximum) {
        if (minimum instanceof Double) {
            return ThreadLocalRandom.current().nextDouble(minimum.doubleValue(), maximum.doubleValue());
        } else {
            return ThreadLocalRandom.current().nextInt(minimum.intValue(), maximum.intValue());
        }
    }

    /**
     * get access to keyboard
     *
     * @return
     */
    public KeysHandler getKeyBoard() {
        return key;
    }

    /**
     * To blink color on specifc element using java script
     */
    public void flashColor(WebElement element, String color, int blinkNumber) throws InterruptedException {
        javaScript.flashColor(element, color, blinkNumber);
    }

    /**
     * get a rounded value to a single decimal
     * @param value
     * @return
     */
    public double roundDouble (double value) {
        return Math.round(value * 10)/10.0;
    }

    /**
     * Setup a universal variable that stays alive all through the JVM life
     * @param variableName
     * @param variable
     * @author nauman.shahid
     */
    public void setJvmVariable(StringSelection variableName, String variable){
        variableName = new StringSelection(variable);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(variableName,variableName);
    }

    /**
     * retrieve the value of the variable stored universally
     * @param variableName
     * @return string
     * @author nauman.shahid
     */
    public String getJvmVariable(StringSelection variableName){
        return Toolkit.getDefaultToolkit().getSystemClipboard().getContents(variableName).toString();
    }

    /**
     * get input stream of a file to read
     * @param fileNameWithExtension
     * @return
     */

    public InputStream getResources(String fileNameWithExtension) {
        return getClass().getResourceAsStream(File.separatorChar+fileNameWithExtension);
    }

    /**
     * convert a file image to buffered image
     * @param fileName
     * @return
     */
    public BufferedImage convertImageFileToBufferedImage(File fileName) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage=ImageIO.read(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    /**
     * upload on webPage , mobilePage , AngularPage
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> void uploadFile(T locator,String input) {
        uploadFile(locator,input, true);
    }

    /**
     * upload on webPage , mobilePage , AngularPage
     * @param locator Mobile , Ng , By :
     *                Mobile.
     *                Angular.
     *                By.
     * @author nauman.shahid
     */
    public <T> void uploadFile(T locator,String input, Boolean processing) {
        actionsHub.doIt(elementApi.locator(locator,processing),input);
    }

}
