package com.infoshare.util;

public class DataValidator {


    public static boolean isDataValid(String data, ValidatorEnum regex) {
        return data.matches(regex.getRegex());
    }
}

