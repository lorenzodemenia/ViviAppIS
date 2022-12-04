package com.example.viviappis.data.model;

import java.lang.annotation.Retention;
import java.util.Random;

public class Utilities {
    public static String generateRandomId() {
        String rndId;
        int leftlimit = 48;
        int rightlimit = 122;
        int length = 10;
        Random rnd = new Random();
        rndId = rnd.ints(leftlimit, rightlimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return rndId;
    }
}
