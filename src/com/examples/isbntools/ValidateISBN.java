package com.examples.isbntools;

public class ValidateISBN {

    public static final int LONG_ISBN_LENGTH = 13;
    public static final int SHORT_ISBN_LENGTH = 10;
    public static final int LONG_ISBN_MULTIPLIER = 11;
    public static final int SHORT_ISBN_MULTIPLIER = 10;

    public boolean checkISBN(String isbn) {
        int total = 0;

        if (isbn.length() == SHORT_ISBN_LENGTH) {
            return isValidShortDigitISBN(isbn, total);
        } else if (isbn.length() == LONG_ISBN_LENGTH) {
            return isValidLongDigitISBN(isbn, total);
        }

        throw new NumberFormatException("ISBN Number must be 10 or 13 digit long.");
    }

    private boolean isValidShortDigitISBN(String isbn, int total) {
        for (int i = 0; i < SHORT_ISBN_LENGTH; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                if (i == 9 && isbn.charAt(i) == 'X') {
                    total += 10;
                } else {
                    throw new NumberFormatException("ISBN Must be a number");
                }
            } else {
                total += Character.getNumericValue(isbn.charAt(i)) * (SHORT_ISBN_LENGTH - i);
            }
        }

        return total % LONG_ISBN_MULTIPLIER == 0;
    }

    private boolean isValidLongDigitISBN(String isbn, int total) {
        for (int i = 0; i < LONG_ISBN_LENGTH; i++) {
            if (i % 2 == 0) {
                total += Character.getNumericValue(isbn.charAt(i));
            } else {
                total += Character.getNumericValue(isbn.charAt(i)) * 3;
            }
        }

        return total % SHORT_ISBN_MULTIPLIER == 0;
    }

}
