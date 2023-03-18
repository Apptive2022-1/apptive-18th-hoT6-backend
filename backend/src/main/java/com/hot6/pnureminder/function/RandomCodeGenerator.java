package com.hot6.pnureminder.function;

import java.util.Random;
public class RandomCodeGenerator {

    public static String generate() {
        int leftLimit = 65; // letter 'a'
        int rightLimit = 90; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

}
