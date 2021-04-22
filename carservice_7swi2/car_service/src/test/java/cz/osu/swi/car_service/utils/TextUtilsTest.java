package cz.osu.swi.car_service.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextUtilsTest {

    @Test
    public void isTextEmptyForNullString() {
        Assertions.assertTrue(TextUtils.isTextEmpty(null)
                , "isTextEmpty should return true for null String");
    }

    @Test
    public void isTextEmptyForEmptyString() {
        Assertions.assertTrue(TextUtils.isTextEmpty(" ")
                , "isTextEmpty should return true for empty String");
    }

    @Test
    public void isTextEmptyForNonStandardInput() {
        Assertions.assertFalse(TextUtils.isTextEmpty("Shakespeare")
                , "isTextEmpty should return false for non standard input");
    }

    @Test
    public void isValidEmailAddressForStandardInput() {
        String text = "lol@seznam.cz";
        Assertions.assertTrue(TextUtils.isValidEmailAddress(text)
                , "isValidEmailAddress should return true for standard input");
    }

    @Test
    public void isValidEmailAddressForNonStandardInput() {
        String text = "edgar@@seznam.cz";
        Assertions.assertFalse(TextUtils.isValidEmailAddress(text)
                , "isValidEmailAddress should return false for non standard input");
    }

    @Test
    public void isNumberOnlyForStandardInput() {
        String text = "452389";
        Assertions.assertTrue(TextUtils.isNumberOnly(text)
                , "isNumberOnly should return true for standard input");
    }

    @Test
    public void isNumberOnlyForNonStandardInput() {
        String text = "4a5af238d9";
        Assertions.assertFalse(TextUtils.isNumberOnly(text)
                , "isNumberOnly should return false for non standard input");
    }

    @Test
    public void isLetterOnlyForStandardInput() {
        String text = "RomeoAndJuliet";
        Assertions.assertTrue(TextUtils.isLetterOnly(text)
                , "isLetterOnly should return true for standard input");
    }

    @Test
    public void isLetterOnlyForNonStandardInput() {
        String text = "RomeoAnd6Juliet";
        Assertions.assertFalse(TextUtils.isLetterOnly(text)
                , "isLetterOnly should return false for nonstandard input");
    }

    @Test
    public void removeAllWhiteSpacesForStandardInput() {
        String text = "auto dum okno";
        String expectedResult = "autodumokno";

        Assertions.assertEquals(expectedResult, TextUtils.removeAllWhiteSpaces(text),
                "removeAllWhiteSpaces should remove all whitespaces");
    }

    @Test
    public void firstUpperRestLowerForStandardInput() {
        String text = "auto MOTO";
        String expectedResult = "Auto moto";

        Assertions.assertEquals(expectedResult, TextUtils.firstUpperRestLower(text),
                "firstUpperRestLower should correctly format text");
    }

    @Test
    public void checkTelephoneFormatForStandardInput() {
        String text = "789456123";

        Assertions.assertTrue(TextUtils.checkTelephoneFormat(text), "checkTelephoneFormat should return true for standard input");
    }

    @Test
    public void checkTelephoneFormatForNonStandardInput() {
        String text = "38945612883";

        Assertions.assertFalse(TextUtils.checkTelephoneFormat(text),
                "checkTelephoneFormat should return false for nonstandard input");
    }

}