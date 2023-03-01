package com.automation.ryder.library.core;

import org.openqa.selenium.By;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;


public final class Convert {

    /**
     * Convert any image to byte[]
     * @param filePathToRead
     * @param imageType
     * @return
     * @author nauman.shahid
     */
    public byte[] imageToByteArray(String filePathToRead, String imageType) {
        BufferedImage bImage = null;
        try {
            bImage = ImageIO.read(new File(filePathToRead));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, imageType, bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return bos.toByteArray();
    }

    /**
     * Convert any image to Base64-encoded Strng
     * @param filePathToRead
     * @param imageType
     * @return
     * @author john.phillips
     */
    public String imageToBase64String(String filePathToRead, String imageType) {
        return Base64.getEncoder().encodeToString(imageToByteArray(filePathToRead, imageType));
    }


    /**
     * convert image to byte array
     * @param bufferedImage
     * @param imageType
     * @return
     * @author nauman.shahid
     */
    public byte[] imageToByteArray(BufferedImage bufferedImage, String imageType) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, imageType, byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Reverses a call to By.toString()
     * i.e.
     * String byToString = By.xpath("//some//xpath").toString();
     * By stringToBy = convertToBy(byToString);
     */
    public By stringToBy(String byLocator) {
        By stringConvertedToBy = null;
        if (byLocator.contains("By.xpath:")) {
            stringConvertedToBy = By.xpath(byLocator.substring("By.xpath:".length()));
        } else if (byLocator.contains("By.id:")) {
            stringConvertedToBy = By.id(byLocator.substring("By.id:".length()));
        } else if (byLocator.contains("By.className:")) {
            stringConvertedToBy = By.className(byLocator.substring("By.className:".length()));
        } else if (byLocator.contains("By.cssSelector:")) {
            stringConvertedToBy = By.cssSelector(byLocator.substring("By.cssSelector:".length()));
        } else if (byLocator.contains("By.tagName:")) {
            stringConvertedToBy = By.tagName(byLocator.substring("By.tagName:".length()));
        } else if (byLocator.contains("By.linkText:")) {
            stringConvertedToBy = By.linkText(byLocator.substring("By.linkText:".length()));
        } else if (byLocator.contains("By.partialLinkText:")) {
            stringConvertedToBy = By.partialLinkText(byLocator.substring("By.partialLinkText:".length()));
        } else if (byLocator.contains("By.name:")) {
            stringConvertedToBy = By.name(byLocator.substring("By.name:".length()));
        }
        return stringConvertedToBy;
    }
}

