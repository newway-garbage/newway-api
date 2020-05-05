package com.newway.newwayapi.util;

import java.util.Random;

public class RandomStringUtils {

    public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        } else if (chars != null && chars.length == 0) {
            throw new IllegalArgumentException("The chars array must not be empty");
        } else {
            if (start == 0 && end == 0) {
                if (chars != null) {
                    end = chars.length;
                } else if (!letters && !numbers) {
                    end = 1114111;
                } else {
                    end = 123;
                    start = 32;
                }
            } else if (end <= start) {
                throw new IllegalArgumentException("Parameter end (" + end + ") must be greater than start (" + start + ")");
            }

            if (chars == null && (numbers && end <= 48 || letters && end <= 65)) {
                throw new IllegalArgumentException("Parameter end (" + end + ") must be greater then (" + 48 + ") for generating digits or greater then (" + 65 + ") for generating letters.");
            } else {
                StringBuilder builder = new StringBuilder(count);
                int gap = end - start;

                while (true) {
                    while (count-- != 0) {
                        int codePoint;
                        if (chars == null) {
                            codePoint = random.nextInt(gap) + start;
                            switch (Character.getType(codePoint)) {
                                case 0:
                                case 18:
                                case 19:
                                    ++count;
                                    continue;
                            }
                        } else {
                            codePoint = chars[random.nextInt(gap) + start];
                        }

                        int numberOfChars = Character.charCount(codePoint);
                        if (count == 0 && numberOfChars > 1) {
                            ++count;
                        } else if ((!letters || !Character.isLetter(codePoint)) && (!numbers || !Character.isDigit(codePoint)) && (letters || numbers)) {
                            ++count;
                        } else {
                            builder.appendCodePoint(codePoint);
                            if (numberOfChars == 2) {
                                --count;
                            }
                        }
                    }

                    return builder.toString();
                }
            }
        }
    }
}
