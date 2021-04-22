package cz.osu.carservice.models.utils;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.control.Label;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class ValidationUtilsTest {

    @Test
    public void checkServicesForLabelNull() {
        Label lbl = null;
        int firstService = 1;
        int secondService = 1;

        try {
            ValidationUtils.checkServices(lbl, firstService, secondService);
            fail("CheckServices should throw an exception because label is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("infoLbl"));
        }
    }

    @Test
    public void checkServicesForServicesNull() {
        Label lbl = new Label("Test label");

        try {
            ValidationUtils.checkServices(lbl, null);
            fail("CheckServices should throw an exception because services are null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("services"));
        }
    }

    @Test
    public void checkServicesForStandardInput() {
        Label lbl = new Label("Test label");
        int[] services = new int[]{0, 1, 0, 0};

        assertFalse(ValidationUtils.checkServices(lbl, services));
    }

    @Test
    public void checkServicesForNonStandardInput() {
        Label lbl = new Label("Test label");
        int[] services = new int[]{0, 5, 0, 0};

        assertTrue(ValidationUtils.checkServices(lbl, services));
    }

    @Test
    public void checkOrderDateForLabelNull() {
        Label lbl = null;
        LocalDate datePart = LocalDate.parse("2021-12-27");
        String time = "18:00";

        try {
            ValidationUtils.checkOrderDate(lbl, datePart, time);
            fail("CheckOrderDate should throw an exception because label is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("infoLbl"));
        }
    }

    @Test
    public void checkOrderDateForLocalDateNull() {
        Label lbl = new Label("Test label");
        LocalDate datePart = null;
        String time = "18:00";

        try {
            ValidationUtils.checkOrderDate(lbl, datePart, time);
            fail("CheckOrderDate should throw an exception because LocalDate is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("datePart"));
        }
    }

    @Test
    public void checkOrderDateForStringNull() {
        Label lbl = new Label("Test label");
        LocalDate datePart = LocalDate.parse("2021-12-27");
        String time = null;

        try {
            ValidationUtils.checkOrderDate(lbl, datePart, time);
            fail("CheckOrderDate should throw an exception because time is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("time"));
        }
    }

    @Test
    public void checkOrderDateForStringEmpty() {
        Label lbl = new Label("Test label");
        LocalDate datePart = LocalDate.parse("2021-12-27");
        String time = "        ";

        try {
            ValidationUtils.checkOrderDate(lbl, datePart, time);
            fail("CheckOrderDate should throw an exception because time is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("time"));
        }
    }

    @Test
    public void checkOrderDateForPastDate() {
        Label lbl = new Label("Test label");
        LocalDate datePart = LocalDate.parse("1020-12-27");
        String time = "18:00";

        assertTrue(ValidationUtils.checkOrderDate(lbl, datePart, time));
    }

    @Test
    public void checkOrderDateForFutureDay() {
        Label lbl = new Label("Test label");
        LocalDate datePart = LocalDate.parse("2190-12-27");
        String time = "18:00";

        assertFalse(ValidationUtils.checkOrderDate(lbl, datePart, time));
    }

    @Test
    public void checkOrderDateForStandardInput() {
        Label lbl = new Label("Test label");
        LocalDate datePart = LocalDate.parse("2020-12-27");
        String time = "18:00";

        assertTrue(ValidationUtils.checkOrderDate(lbl, datePart, time));
    }

    @Test
    public void checkYearForLabelNull() {
        Label lbl = null;
        int year = 2005;

        try {
            ValidationUtils.checkYear(lbl, year);
            fail("CheckYear should throw an exception because label is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("infoLbl"));
        }
    }

    @Test
    public void checkYearForPastYear() {
        Label lbl = new Label("Test label");
        int year = 1900;

        assertFalse(ValidationUtils.checkYear(lbl, year));
    }

    @Test
    public void checkYearForFutureYear() {
        Label lbl = new Label("Test label");
        int year = 5000;

        assertFalse(ValidationUtils.checkYear(lbl, year));
    }

    @Test
    public void checkYearForStandardInput() {
        Label lbl = new Label("Test label");
        int year = 2021;

        assertTrue(ValidationUtils.checkYear(lbl, year));
    }

    @Test
    public void checkContainsNumbersOnlyForLabelNull() {
        Label lbl = null;
        String numbers = "9875";

        try {
            ValidationUtils.containsNumbersOnly(lbl, numbers);
            fail("ContainsNumbersOnly should throw an exception because label is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("infoLbl"));
        }
    }

    @Test
    public void checkContainsNumbersOnlyForStringNull() {
        Label lbl = new Label("Test label");
        String[] numbers = null;

        try {
            ValidationUtils.containsNumbersOnly(lbl, numbers);
            fail("ContainsNumbersOnly should throw an exception because strings is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("strings"));
        }
    }

    @Test
    public void checkContainsNumbersOnlyForStandardInput() {
        Label lbl = new Label("Test label");

        assertTrue(ValidationUtils.containsNumbersOnly(lbl, "78569", "785698878"));
    }

    @Test
    public void checkContainsNumbersOnlyForNonStandardInput() {
        Label lbl = new Label("Test label");

        assertFalse(ValidationUtils.containsNumbersOnly(lbl, "78569", "7856g98878"));
    }

    @Test
    public void checkContainsLettersOnlyForLabelNull() {
        Label lbl = null;
        String letters = "absed";

        try {
            ValidationUtils.containsLettersOnly(lbl, letters);
            fail("ContainsLettersOnly should throw an exception because label is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("infoLbl"));
        }
    }

    @Test
    public void checkContainsLettersOnlyForStringNull() {
        Label lbl = new Label("Test label");
        String[] letters = null;

        try {
            ValidationUtils.containsLettersOnly(lbl, letters);
            fail("ContainsLettersOnly should throw an exception because strings are null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("strings"));
        }
    }

    @Test
    public void checkContainsLettersOnlyForStandardInput() {
        Label lbl = new Label("Test label");

        assertTrue(ValidationUtils.containsLettersOnly(lbl, "letters", "ascfdsdSD"));
    }

    @Test
    public void checkContainsLettersOnlyForNonStandardInput() {
        Label lbl = new Label("Test label");

        assertFalse(ValidationUtils.containsLettersOnly(lbl, "lett74ers", "ascfdsdSD"));
    }

    @Test
    public void checkContainsEmptyStringsForLabelNull() {
        Label lbl = null;
        String letters = "";

        try {
            ValidationUtils.containsEmptyStrings(lbl, letters);
            fail("ContainsEmptyStrings should throw an exception because label is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("infoLbl"));
        }
    }

    @Test
    public void checkContainsEmptyStringsForStringsNull() {
        Label lbl = new Label("Test label");
        String[] letters = null;

        try {
            ValidationUtils.containsEmptyStrings(lbl, letters);
            fail("ContainsEmptyStrings should throw an exception because strings is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("strings"));
        }
    }

    @Test
    public void checkContainsEmptyStringsForStandardInput() {
        Label lbl = new Label("Test label");
        String[] letters = new String[]{"     ", "", "asads"};

        assertTrue(ValidationUtils.containsEmptyStrings(lbl, letters));
    }

    @Test
    public void checkContainsEmptyStringsForNonStandardInput() {
        Label lbl = new Label("Test label");
        String[] letters = new String[]{"65asdsa", "asda", "asads"};

        assertFalse(ValidationUtils.containsEmptyStrings(lbl, letters));
    }

    @Test
    public void checkGrammarRulesForLabelNull() {
        Label lbl = null;
        LocalDate datePart = LocalDate.parse("2020-12-27");
        String time = "15:00";

        try {
            ValidationUtils.checkGrammarRules(lbl, "789456as", "Sedan", "1985", datePart, time, "Petr",
                    "Vomáčka", "789456123", "vomacka@seznam.cz", "Brno", "Dutá", "45", "78945",
                    1, 0, 1);
            fail("CheckGrammarRules should throw an exception because label is null!");
        } catch (IllegalArgumentException exception) {
            assertTrue(exception.getMessage().contains("infoLbl"));
        }
    }

    @Test
    public void checkGrammarRulesForStandardInput() {
        Label lbl = new Label("Test label");
        LocalDate datePart = LocalDate.parse("2029-12-27");
        String time = "15:00";


        assertTrue(ValidationUtils.checkGrammarRules(lbl, "789456as", "Sedan", "1985", datePart, time, "Petr",
                "Vomáčka", "789456123", "vomacka@seznam.cz", "Brno", "Dutá", "45", "78945",
                1, 0, 1));

    }

    @Test
    public void checkGrammarRulesForNonStandardInput() {
        Label lbl = new Label("Test label");
        LocalDate datePart = LocalDate.parse("2020-12-27");
        String time = "15:00";


        assertFalse(ValidationUtils.checkGrammarRules(lbl, "789456ahhhs", "Sedan", "21985", datePart, time, "Petr",
                "Vomáčka18", "78945612h3", "vomacka@seznam.cz", "Brno", "Dut8á", "4f5", "78945",
                1, 0, 1));

    }
}