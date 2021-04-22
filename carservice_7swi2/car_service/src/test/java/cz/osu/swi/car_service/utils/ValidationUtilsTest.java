package cz.osu.swi.car_service.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

class ValidationUtilsTest {

    @Test
    public void checkServicesForNullInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.checkServices((int[]) null);
        }, "checkServices should throw IllegalArgumentException");
    }

    @Test
    public void checkServicesForStandardInput() {
        Optional<String> expected = Optional.empty();
        Assertions.assertEquals(expected, ValidationUtils.checkServices(0, 1, 0),
                "checkServices should return Optional.empty");
    }

    @Test
    public void checkServicesForNonStandardInput() {
        Assertions.assertFalse(ValidationUtils.checkServices(5, 2, 9).isEmpty(),
                "checkServices should return an error message");
    }

    @Test
    public void checkOrderDateForLocalDateNullInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.checkOrderDate(null, "10:00");
        }, "checkOrderDate with null date parameter should throw IllegalArgumentException");
    }

    @Test
    public void checkOrderDateForStringNullInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.checkOrderDate(LocalDate.of(2000, 5, 25), null);
        }, "checkOrderDate with null time parameter should throw IllegalArgumentException");
    }

    @Test
    public void checkOrderDateForEmptyStringInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.checkOrderDate(LocalDate.of(2000, 5, 25), " ");
        }, "checkOrderDate with empty time parameter should throw IllegalArgumentException");
    }

    @Test
    public void checkOrderDateForPastDayInput() {
        Assertions.assertFalse(ValidationUtils.checkOrderDate(LocalDate.of(2000, 5, 25), "10:00").isEmpty(),
                "checkOrderDate with past date should return an error message");
    }

    @Test
    public void checkOrderDateForStandardInput() {
        Assertions.assertTrue(ValidationUtils.checkOrderDate(LocalDate.of(2021, 10, 25), "10:00").isEmpty(),
                "checkOrderDate for standard output should return Optional.empty");
    }

    @Test
    public void checkYearForPastYear() {
        Assertions.assertFalse(ValidationUtils.checkYear(1900).isEmpty(),
                "checkYear with past year should return an error message");
    }

    @Test
    public void checkYearForFutureYear() {
        Assertions.assertFalse(ValidationUtils.checkYear(2900).isEmpty(),
                "checkYear with future year should return an error message");
    }

    @Test
    public void checkYearForStandardInput() {
        Assertions.assertTrue(ValidationUtils.checkYear(LocalDate.now().getYear()).isEmpty(),
                "checkYear current year should return Optional.empty");
    }

    @Test
    public void checkContainsNumbersOnlyForStringNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String[] params = null;
            ValidationUtils.containsNumbersOnly(params);
        }, "containsNumbersOnly with null input should throw an IllegalArgumentException");
    }

    @Test
    public void checkContainsNumbersOnlyForStandardInput() {
        Assertions.assertTrue(ValidationUtils.containsNumbersOnly("5", "9", "2").isEmpty(),
                "containsNumbersOnly with standard input should return Optional.empty");
    }

    @Test
    public void checkContainsNumbersOnlyForNonStandardInput() {
        Assertions.assertFalse(ValidationUtils.containsNumbersOnly("5", "9d", "2").isEmpty(),
                "containsNumbersOnly with nonstandard input should return an error message");
    }

    @Test
    public void checkContainsLettersOnlyForStringNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String[] params = null;
            ValidationUtils.containsLettersOnly(params);
        }, "containsLettersOnly with null input should throw an IllegalArgumentException");
    }

    @Test
    public void checkContainsLettersOnlyForStandardInput() {
        Assertions.assertTrue(ValidationUtils.containsLettersOnly("Bilbo", "Baggins", "Ring").isEmpty(),
                "containsLettersOnly with standard input should return Optional.empty");
    }

    @Test
    public void checkContainsLettersOnlyForNonStandardInput() {
        Assertions.assertFalse(ValidationUtils.containsNumbersOnly("Bilbo", "Baggins", "2").isEmpty(),
                "containsLettersOnly with nonstandard input should return an error message");
    }

    @Test
    public void checkContainsEmptyStringsForStringsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String[] params = null;
            ValidationUtils.containsEmptyStrings(params);
        }, "containsEmptyStrings with null parameter should throw an IllegalArgumentException");
    }

    @Test
    public void checkContainsEmptyStringsForStandardInput() {
        Assertions.assertFalse(ValidationUtils.containsEmptyStrings("Frodo", "Baggins", " ").isEmpty(),
                "containsEmptyStrings should return Optional.empty");
    }

    @Test
    public void checkContainsEmptyStringsForNonStandardInput() {
        Assertions.assertTrue(ValidationUtils.containsEmptyStrings("Frodo", "Baggins", "Ring").isEmpty(),
                "containsEmptyStrings should return an error message");
    }

    @Test
    public void checkGrammarRulesForStandardInput() {
        LocalDate datePart = LocalDate.parse("2029-12-27");
        String time = "15:00";

        Assertions.assertTrue(ValidationUtils.checkGrammarRules("789456as", "Sedan", "1985", datePart, time, "Petr",
                "Vomáčka", "789456123", "vomacka@seznam.cz", "Brno", "Dutá", "45", "78945",
                1, 0, 1).isEmpty(),
                "checkGrammarRules should return Optional.empty");
    }

    @Test
    public void checkGrammarRulesForNonStandardInput() {
        LocalDate datePart = LocalDate.parse("2020-12-27");
        String time = "15:00";

        Assertions.assertFalse(ValidationUtils.checkGrammarRules("789456ahhhs", "Sedan", "21985", datePart, time, "Petr",
                "Vomáčka18", "78945612h3", "vomacka@seznam.cz", "Brno", "Dut8á", "4f5", "78945",
                1, 0, 1).isEmpty(),
                "checkGrammarRules should return an error message");
    }

    @Test
    public void checkLengthOfTextForNullMaxErrorMessage() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.checkLengthOfText("Muj text pro testování.", 25, 5, null, "Text je krátký");
        }, "checkLengthOfText should throw IllegalArgumentException because maxErrorMessage is null");
    }

    @Test
    public void checkLengthOfTextForNullMinErrorMessage() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.checkLengthOfText("Muj text pro testování.", 25, 5, "Text je přílíš dlouhý", null);
        }, "checkLengthOfText should throw IllegalArgumentException because minErrorMessage is null");
    }

    @Test
    public void checkLengthOfTextForMinLength() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.checkLengthOfText("Muj text pro testování.", 25, -5, "Text je přílíš dlouhý", "Test je přílíš krátký");
        }, "checkLengthOfText should throw IllegalArgumentException because minLength is negative number");
    }

    @Test
    public void checkLengthOfTextForMaxLength() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.checkLengthOfText("Muj text pro testování.", -25, 5, "Text je přílíš dlouhý", "Test je přílíš krátký");
        }, "checkLengthOfText should throw IllegalArgumentException because maxLength is negative number");
    }

    @Test
    public void checkLengthOfTextForMaxLengthIsSmallerThanMinLength() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.checkLengthOfText("Muj text pro testování.", 5, 25, "Text je přílíš dlouhý", "Test je přílíš krátký");
        }, "checkLengthOfText should throw IllegalArgumentException because maxLength is smaller than minLength");
    }

    @Test
    public void checkLengthTextForStandard() {
        Assertions.assertTrue(ValidationUtils.checkLengthOfText("Muj text pro testování.", 35, 5, "Text je přílíš dlouhý", "Test je přílíš krátký").isEmpty(),
                "checkLengthOfText should return Optional.empty");
    }

    @Test
    public void checkLengthTextForNonStandard() {
        Assertions.assertFalse(ValidationUtils.checkLengthOfText("Muj text pro testování.", 8, 5, "Text je přílíš dlouhý", "Test je přílíš krátký").isEmpty(),
                "checkLengthOfText should return an error message");
    }
}