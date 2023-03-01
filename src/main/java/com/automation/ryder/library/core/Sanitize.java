package com.automation.ryder.library.core;

/**
 * get a string which can be used in locators which is smart enough to change
 * the DOM to lower or upper case as we require
 *
 * @author nauman.shahid
 * Sanitize string
 */

public final class Sanitize {
    /**
     * normalize space , remove apostrophes , make case in-sensitive
     *
     * @param field
     * @param value
     * @return
     * @author john.philips
     */
    public String equals(String field, String value) {
        return translate(field) + "=" + translate(sanitizeApostrophes(value));
    }

    /**
     * normalize space , remove apostrophes , make case in-sensitive
     *
     * @param field
     * @param value
     * @return
     * @author john.philips
     */
    public String contains(String field, String value) {
        return "contains(" + translate(field) + "," + translate(sanitizeApostrophes(value)) + ")";
    }

    /**
     * Concatenate xpaths
     *
     * @param xpath1
     * @param xpath2
     * @return
     * @author john.philips
     */
    public String concat(String xpath1, String xpath2) {
        // Split the locators on "|"
        // This will allow the xpaths to be concatenated even if there are different
        // options
        String[] xpath1Parts = xpath1.split("\\|");
        String[] xpath2Parts = xpath2.split("\\|");

        StringBuilder locator = new StringBuilder();
        // for each option in the parent locator
        for (String parentLocatorPart : xpath1Parts) {
            parentLocatorPart = parentLocatorPart.trim();
            // append each option of the child locator, and add to the string builder
            for (String localLocatorPart : xpath2Parts) {
                localLocatorPart = localLocatorPart.trim();
                String stringToAppend = parentLocatorPart + localLocatorPart + " | ";
                locator.append(stringToAppend);
            }
        }
        // return the completed locator
        return locator.toString().substring(0, locator.length() - 3);
    }

    /**
     * Translates an xpath value to lowercase
     *
     * @param value
     * @return
     */
    public String translate(String value) {
        return "normalize-space(translate(" + value + ", 'ABCDEFGHIJKLMNOPQRSTUVWXYZ\u00A0' , 'abcdefghijklmnopqrstuvwxyz '))";
    }

    /**
     * Wraps an xpath value in an xpath concat function
     *
     * @param value
     * @return
     */
    private String concat(String value) {
        return "concat(' '," + value + ",' ')";
    }

    private String sanitizeApostrophes(String input) {
        String output;
        if (input.contains("'")) {
            String[] splitString = input.split("'");
            output = "concat('" + splitString[0] + "'";
            for (int i = 1; i < splitString.length; i++) {
                output = output + ",\"'\",'" + splitString[i] + "'";
            }
            output = output + ")";
        } else {
            output = "'" + input + "'";
        }

        return output;
    }

}
