package cz.osu.carservice.models.utils;

import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class ValidationUtils {

    public static boolean checkGrammarRules(Label infoLbl, String registrationPlate, String typeOfCar, String yearOfProduction, LocalDate dateOfOrder, String time,
                                            String name, String surname, String telephone, String email,
                                            String city, String street, String streetCode, String postCode, int carService,int tireService, int otherService) {
        if (infoLbl == null) throw new IllegalArgumentException("Parametr infoLbl nesmí být null!");

        if(checkServices(infoLbl,carService,tireService,otherService))
            return false;

        if (containsEmptyStrings(infoLbl, registrationPlate, typeOfCar, yearOfProduction, name, surname, telephone, email, city, street, streetCode, postCode))
            return false;

        if (containsLettersOnly(infoLbl, typeOfCar, name, surname, city, street))
            return false;

        if (containsNumbersOnly(infoLbl, yearOfProduction, telephone, postCode))
            return false;

        if (!TextUtils.isValidEmailAddress(email)) {
            FormUtils.setTextAndRedColorToLabel(infoLbl, "Špatně zadaný email!");
            return false;
        }

        if (!checkYear(infoLbl, Integer.parseInt(yearOfProduction)))
            return false;

        if (!TextUtils.checkTelephoneFormat(telephone))
            return false;

        if (checkOrderDate(infoLbl,dateOfOrder,time))
            return false;

        if (TextUtils.checkLengthOfText(infoLbl, registrationPlate, 8, 7, String.format("Maximální délka %s je %d", "SPZ", 8), String.format("Minimální délka %s je %d", "SPZ", 7)) ||
                TextUtils.checkLengthOfText(infoLbl, typeOfCar, 30, 3, String.format("Maximální délka %s je %d", "TYPU AUTA", 30), String.format("Minimální délka %s je %d", "TYPU AUTA", 3)) ||
                TextUtils.checkLengthOfText(infoLbl, yearOfProduction, 4, 4, String.format("Maximální délka %s je %d", "ROKU VÝROBY", 4), String.format("Minimální délka %s je %d", "ROKU VÝROBY", 4)) ||
                TextUtils.checkLengthOfText(infoLbl, name, 15, 2, String.format("Maximální délka %s je %d", "JMÉNA", 15), String.format("Minimální délka %s je %d", "JMÉNA", 2)) ||
                TextUtils.checkLengthOfText(infoLbl, surname, 15, 2, String.format("Maximální délka %s je %d", "PŘÍJMENÍ", 15), String.format("Minimální délka %s je %d", "PŘÍJMENÍ", 2)) ||
                TextUtils.checkLengthOfText(infoLbl, telephone, 9, 9, String.format("Maximální délka %s je %d", "TELEFONU", 9), String.format("Minimální délka %s je %d", "TELEFONU", 9)) ||
                TextUtils.checkLengthOfText(infoLbl, email, 40, 5, String.format("Maximální délka %s je %d", "EMAILU", 40), String.format("Minimální délka %s je %d", "EMAILU", 5)) ||
                TextUtils.checkLengthOfText(infoLbl, city, 30, 2, String.format("Maximální délka %s je %d", "MĚSTA", 30), String.format("Minimální délka %s je %d", "MĚSTA", 2)) ||
                TextUtils.checkLengthOfText(infoLbl, street, 20, 2, String.format("Maximální délka %s je %d", "ULICE", 20), String.format("Minimální délka %s je %d", "ULICE", 2)) ||
                TextUtils.checkLengthOfText(infoLbl, streetCode, 15, 1, String.format("Maximální délka %s je %d", "ČÍSLA ULICE", 15), String.format("Minimální délka %s je %d", "ČÍSLA ULICE", 1)) ||
                TextUtils.checkLengthOfText(infoLbl, postCode, 15, 4, String.format("Maximální délka %s je %d", "PSČ", 15), String.format("Minimální délka %s je %d", "PSČ", 4))) {
            return false;
        }

        return true;
    }

    public static boolean containsEmptyStrings(Label infoLbl, String... strings) {
        if (infoLbl == null) throw new IllegalArgumentException("Parametr infoLbl nesmí být null!");
        if (strings == null) throw new IllegalArgumentException("Parametr strings nesmí být null!");

        for (String text : strings) {
            if (TextUtils.isTextEmpty(text)) {
                FormUtils.setTextAndRedColorToLabel(infoLbl, "Vyplňte všechny údaje!");
                return true;
            }
        }
        return false;
    }

    public static boolean containsLettersOnly(Label infoLbl, String... strings) {
        if (infoLbl == null) throw new IllegalArgumentException("Parametr infoLbl nesmí být null!");
        if (strings == null) throw new IllegalArgumentException("Parametr strings nesmí být null!");

        for (String text : strings) {
            if (!TextUtils.isLetterOnly(text)) {
                FormUtils.setTextAndRedColorToLabel(infoLbl, "Špatně vyplněné údaje!");
                return true;
            }
        }
        return false;
    }

    public static boolean containsNumbersOnly(Label infoLbl, String... strings) {
        if (infoLbl == null) throw new IllegalArgumentException("Parametr infoLbl nesmí být null!");
        if (strings == null) throw new IllegalArgumentException("Parametr strings nesmí být null!");

        for (String text : strings) {
            if (!TextUtils.isNumberOnly(text)) {
                FormUtils.setTextAndRedColorToLabel(infoLbl, "Špatně vyplněné údaje!");
                return true;
            }
        }
        return false;
    }

    public static boolean checkYear(Label infoLbl, int year) {
        if (infoLbl == null) throw new IllegalArgumentException("Parametr infoLbl nesmí být null!");

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int actualYear = localDate.getYear();

        if (year > actualYear) {
            FormUtils.setTextAndRedColorToLabel(infoLbl, "Tento rok ještě nenastal!");
            return false;
        } else if (year < 1950) {
            FormUtils.setTextAndRedColorToLabel(infoLbl, "Tento rok je příliš v minulosti!");
            return false;
        }
        return true;
    }

    public static boolean checkOrderDate(Label infoLbl,LocalDate datePart,String time){
        if (infoLbl == null) throw new IllegalArgumentException("Parametr infoLbl nesmí být null!");
        if (datePart == null) throw new IllegalArgumentException("Parametr datePart nesmí být null!");
        if (TextUtils.isTextEmpty(time)) throw new IllegalArgumentException("Parametr time nesmí být null!");

        LocalTime timePart = LocalTime.parse(time);
        LocalDateTime dt = LocalDateTime.of(datePart, timePart);

        if (dt.isBefore(LocalDateTime.now())){
            FormUtils.setTextAndRedColorToLabel(infoLbl, "Toto datum a čas již nastalo!");
            return true;
        }

        return false;
    }

    public static boolean checkServices(Label infoLbl,int... services){
        if (infoLbl == null) throw new IllegalArgumentException("Parametr infoLbl nesmí být null!");
        if (services == null) throw new IllegalArgumentException("Parametr services nesmí být null!");

        for (int item : services) {
            if (item == 1) return false;
        }

        FormUtils.setTextAndRedColorToLabel(infoLbl, "Vyberte typ služby!");
        return true;
    }
}
