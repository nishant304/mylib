/**
 * Validator.java
 * Mar 12, 2013
 * MN Mobicules Systems Pvt. Ltd.
 */
package com.wecamchat.aviddev.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 * 
 */
public class Validator {

    private static final String EMAIL_REG_EX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String NAME_REG_EX = "^(?:[A-Z][a-z]{3,9}\'x20){1,2}[A-Z]\'.\'x20[A-Z][a-z]{3,9}$";

    // private static final String PASSWORD_REG_EX = "^[a-zA-Z0-9]*$";
    // private static final String PASSWORD_REG_EX =
    // "(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]{8,}";

    // private static final String PASSWORD_REG_EX =
    // "([0-9]+[a-zA-Z][0-9a-zA-Z]*)|([a-zA-Z]+[0-9][0-9a-zA-Z]*)";

    // private static final String PASSWORD_REG_EX =
    // "[0-9a-z]*[0-9][0-9a-z]*[a-z][0-9a-z]*|[0-9a-z]*[a-z][0-9a-z]*[0-9][0-9a-z]*";

    private static final String PASSWORD_REG_EX = "^(?=[ 0-9 ]*?[ a-zA-Z ]+)(?=[ a-zA-Z ]*?[ 0-9 ]+)[ a-zA-Z + 0-9 ]{8,}+$";

    public static final char[] NOT_ALLOWED_CHARS_IN_PASSWORD = { ' ', '=', '*',
            '/', '\\', '-', ',', ';' };
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static int MAX_PASSWORD_LENGTH = 16;

    /**
     * Check if the given string is null or empty
     * 
     * @param string
     *            String to check
     * @return true if not; false otherwise.
     */
    public static boolean isNotNullOrEmpty(String string) {
        if (string != null && string.length() > 0)
            return true;
        else
            return false;
    }

    /**
     * Check for valid email id
     * 
     * @param email
     *            Email id to check
     * @return true if valid; false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (isNotNullOrEmpty(email)) {
            Pattern emailPattern = Pattern.compile(EMAIL_REG_EX);
            Matcher emailMatcher = emailPattern.matcher(email);
            return emailMatcher.find();
        } else
            return false;
    }

    // /**
    // * Check for valid password
    // *
    // * @param password
    // * Password to check
    // * @return true if valid; false otherwise
    // */
    // public static boolean isValidPassword(String password) {
    // if (isNotNullOrEmpty(password)
    // && password.length() > MIN_PASSWORD_LENGTH
    // && password.length() < MAX_PASSWORD_LENGTH) {
    // for (char invalidChar : NOT_ALLOWED_CHARS_IN_PASSWORD) {
    // if (password.indexOf(invalidChar) != -1)
    // return false;
    // }
    // return true;
    // } else
    // return false;
    //
    // }

    /**
     * Check for valid password
     * 
     * @param password
     *            Password to check
     * @return true if valid; false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (isNotNullOrEmpty(password)) {
            Pattern passwordPattern = Pattern.compile(PASSWORD_REG_EX);
            Matcher passwordMatcher = passwordPattern.matcher(password);
            return passwordMatcher.find();
        } else
            return false;
    }

    // public static boolean isValidPassword(String password) {
    // if (isNotNullOrEmpty(password)) {
    // // Pattern passwordPattern = Pattern.compile(PASSWORD_REG_EX);
    // // Matcher passwordMatcher = passwordPattern.matcher(password);
    // // return passwordMatcher.find();
    // for(int i=0; i<password.length(); i++){
    // if(password.i)
    // }
    // } else
    // return false;
    // }

    /**
     * Check for mobile number
     * 
     * @param mobileNumber
     * @return true if valid; false otherwise
     */
    public static boolean isValidMobileNumber(String mobileNumber) {
        boolean response = false;

        if (isNotNullOrEmpty(mobileNumber) && mobileNumber.length() > 2) {
            String number = mobileNumber.substring(1, mobileNumber.length());
            String pattern = "\\d+";
            if ((mobileNumber.charAt(0) == '0' || mobileNumber.charAt(0) == '+')
                    && number.matches(pattern))
                response = true;
        } else
            response = false;

        return response;
    }

    public static boolean isValidName(String name) {
        boolean response = false;

        if (isNotNullOrEmpty(name) && name.length() > 2) {
            String number = name.substring(1, name.length());
            String pattern = "^(?:[A-Z][a-z]{3,9}\'x20){1,2}[A-Z]\'.\'x20[A-Z][a-z]{3,9}$";
            if ((name.charAt(0) == '0' || name.charAt(0) == '+')
                    && number.matches(pattern))
                response = true;
        } else
            response = false;

        return response;
    }

    public static boolean isContainSpecialCharacter(String password) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(password);
        boolean b = m.find();
        return b;
    }

}
