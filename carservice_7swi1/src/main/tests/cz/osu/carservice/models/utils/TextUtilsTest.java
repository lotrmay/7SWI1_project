package cz.osu.carservice.models.utils;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.control.Label;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class TextUtilsTest {
    @Test
    public void isTextEmptyTestStringEmpty() {
        String text = "             ";
        assertTrue(TextUtils.isTextEmpty(text));
    }

    @Test
    public void isTextEmptyTestStringNull() {
        String text = null;
        assertTrue(TextUtils.isTextEmpty(text));
    }

    @Test
    public void isTextEmptyForNonStandardInput() {
        String text = "hhhhh";
        assertFalse(TextUtils.isTextEmpty(text));
    }


    @Test
    public void isValidEmailAddressForStandardInput() {
        String text = "vk1999@seznam.cz";
        assertTrue(TextUtils.isValidEmailAddress(text));
    }

    @Test
    public void isValidEmailAddressForNonStandardInput() {
        String text = "vasdasd@@seznam.cz";
        assertFalse(TextUtils.isValidEmailAddress(text));
    }


    @Test
    public void isNumberOnlyForStandardInput() {
        String text = "456789";
        assertTrue(TextUtils.isNumberOnly(text));
    }

    @Test
    public void isNumberOnlyForNonStandardInput() {
        String text = "45a67s89";
        assertFalse(TextUtils.isNumberOnly(text));
    }

    @Test
    public void isLetterOnlyForStandardInput() {
        String text = "asdasdasdfgkjuz";
        assertTrue(TextUtils.isLetterOnly(text));
    }

    @Test
    public void isLetterOnlyForNonStandardInput() {
        String text = "asdf152asd2";
        assertFalse(TextUtils.isLetterOnly(text));
    }

    @Test
    public void removeAllWhiteSpacesForStandardInput() {
        String text = "auto dum okno ";
        String expectedResult = "autodumokno";

        assertEquals(expectedResult, TextUtils.removeAllWhiteSpaces(text));
    }


    @Test
    public void firstUpperRestLowerForStandardInput() {
        String text = "auto MOTO";
        String expectedResult = "Auto moto";

        assertEquals(expectedResult, TextUtils.firstUpperRestLower(text));
    }


    @Test
    public void checkTelephoneFormatForStandardInput() {
        String text = "789456123";

        assertTrue(TextUtils.checkTelephoneFormat(text));
    }

    @Test
    public void checkTelephoneFormatForNonStandardInput() {
        String text = "38945612883";

        assertFalse(TextUtils.checkTelephoneFormat(text));
    }

    @Test
    public void checkLengthOfTextForLabelNull() {
        Label label = null;
        try {
            TextUtils.checkLengthOfText(label, "Auto", 8, 2, "Chyba max", "Chyba min");
            fail("CheckLengthOfText should throw an exception because infoLbl is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("infoLbl"));
        }
    }


    @Test
    public void checkLengthOfTextForEmptyMaxTextParameter() {
        Label label = new Label("Test label");
        String text = "";
        try {
            TextUtils.checkLengthOfText(label, "Auto", 8, 2, text, "Chyba min");
            fail("CheckLengthOfText should throw an exception because maxErrorMessage is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("maxErrorMessage"));
        }
    }

    @Test
    public void checkLengthOfTextForEmptyMinTextParameter() {
        Label label = new Label("Test label");
        String text = "";
        try {
            TextUtils.checkLengthOfText(label, "Auto", 8, 2, "Chyba max", text);
            fail("CheckLengthOfText should throw an exception because minErrorMessage is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("minErrorMessage"));
        }
    }

    @Test
    public void checkLengthOfTextForNegativeMaxParameter() {
        Label label = new Label("Test label");
        int max = -50;
        try {
            TextUtils.checkLengthOfText(label, "Auto", max, 2, "Chyba max", "Chyba min");
            fail("CheckLengthOfText should throw an exception because maxLength is below zero!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("maxLength"));
        }
    }

    @Test
    public void checkLengthOfTextForNegativeMinParameter() {
        Label label = new Label("Test label");
        int min = -50;
        try {
            TextUtils.checkLengthOfText(label, "Auto", 8, min, "Chyba max", "Chyba min");
            fail("CheckLengthOfText should throw an exception because minLength is below zero!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("minLength"));
        }
    }

    @Test
    public void checkLengthOfTextForMinBiggerThanMaxParameter() {
        Label label = new Label("Test label");
        int min = 50;
        int max = 5;

        try {
            TextUtils.checkLengthOfText(label, "Auto", max, min, "Chyba max", "Chyba min");
            fail("CheckLengthOfText should throw an exception because minLength is higher than maxLength!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("maxLength"));
        }
    }

    @Test
    public void checkLengthOfTextForStandardInput() {
        Label label = new Label("Test label");

        assertTrue(TextUtils.checkLengthOfText(label, "Auto", 20, 2, "Chyba max", "Chyba min"));
    }

    @Test
    public void checkLengthOfTextForNonStandardInput() {
        Label label = new Label("Test label");

        assertFalse(TextUtils.checkLengthOfText(label, "AutoAutoAutoAutoAutoAutoAuto", 20, 2, "Chyba max", "Chyba min"));
    }
}