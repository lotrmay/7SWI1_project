package cz.osu.swi.car_service.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class ValidationUtils {

    public static Optional<String> checkGrammarRules(String registrationPlate, String typeOfCar, String yearOfProduction, LocalDate dateOfOrder, String time,
                                                     String name, String surname, String telephone, String email,
                                                     String city, String street, String streetCode, String postCode, int carService, int tireService, int otherService) {

        Optional<String> temp;

        temp = checkServices(carService,tireService,otherService);
        if (temp.isPresent()) return temp;

        temp = containsEmptyStrings(registrationPlate, typeOfCar, yearOfProduction, name, surname, telephone, email, city, street, streetCode, postCode);
        if (temp.isPresent()) return temp;

        temp = containsLettersOnly(typeOfCar, name, surname, city, street);
        if (temp.isPresent()) return temp;

        temp = containsNumbersOnly(yearOfProduction, telephone, postCode);
        if (temp.isPresent()) return temp;

        if (!TextUtils.isValidEmailAddress(email))
            return Optional.of("Špatně zadaný email!");

        temp = checkYear(Integer.parseInt(yearOfProduction));
        if (temp.isPresent()) return temp;

        if (!TextUtils.checkTelephoneFormat(telephone))
            return Optional.of("Špatně zadaný telefon!");

        temp = checkOrderDate(dateOfOrder,time);
        if (temp.isPresent()) return temp;

        //region Grammar length

        //Registration Plate
        temp = checkLengthOfText(registrationPlate, 8, 7, String.format("Maximální délka %s je %d", "SPZ", 8), String.format("Minimální délka %s je %d", "SPZ", 7));
        if (temp.isPresent()) return temp;

        //Type of car
        temp = checkLengthOfText(typeOfCar, 30, 3, String.format("Maximální délka %s je %d", "TYPU AUTA", 30), String.format("Minimální délka %s je %d", "TYPU AUTA", 3));
        if (temp.isPresent()) return temp;

        //Production year
        temp = checkLengthOfText(yearOfProduction, 4, 4, String.format("Maximální délka %s je %d", "ROKU VÝROBY", 4), String.format("Minimální délka %s je %d", "ROKU VÝROBY", 4));
        if (temp.isPresent()) return temp;

        //Customer name
        temp = checkLengthOfText(name, 15, 2, String.format("Maximální délka %s je %d", "JMÉNA", 15), String.format("Minimální délka %s je %d", "JMÉNA", 2));
        if (temp.isPresent()) return temp;

        //Customer surname
        temp = checkLengthOfText(surname, 15, 2, String.format("Maximální délka %s je %d", "PŘÍJMENÍ", 15), String.format("Minimální délka %s je %d", "PŘÍJMENÍ", 2));
        if (temp.isPresent()) return temp;

        //Telephone
        temp = checkLengthOfText(telephone, 9, 9, String.format("Maximální délka %s je %d", "TELEFONU", 9), String.format("Minimální délka %s je %d", "TELEFONU", 9));
        if (temp.isPresent()) return temp;

        //Email
        temp = checkLengthOfText(email, 40, 5, String.format("Maximální délka %s je %d", "EMAILU", 40), String.format("Minimální délka %s je %d", "EMAILU", 5));
        if (temp.isPresent()) return temp;

        //City
        temp = checkLengthOfText(city, 30, 2, String.format("Maximální délka %s je %d", "MĚSTA", 30), String.format("Minimální délka %s je %d", "MĚSTA", 2));
        if (temp.isPresent()) return temp;

        //Street
        temp = checkLengthOfText(street, 20, 2, String.format("Maximální délka %s je %d", "ULICE", 20), String.format("Minimální délka %s je %d", "ULICE", 2));
        if (temp.isPresent()) return temp;

        //Street Code
        temp = checkLengthOfText(streetCode, 15, 1, String.format("Maximální délka %s je %d", "ČÍSLA ULICE", 15), String.format("Minimální délka %s je %d", "ČÍSLA ULICE", 1));
        if (temp.isPresent()) return temp;

        //Post Code
        temp = checkLengthOfText(postCode, 15, 4, String.format("Maximální délka %s je %d", "PSČ", 15), String.format("Minimální délka %s je %d", "PSČ", 4));
        if (temp.isPresent()) return temp;
        //endregion

        return Optional.empty();
    }

    public static Optional<String> containsEmptyStrings(String... strings) {
        if (strings == null) throw new IllegalArgumentException("Parametr strings nesmí být null!");

        for (String text : strings) {
            if (TextUtils.isTextEmpty(text)) {
                return Optional.of("Vyplňte všechny údaje!");
            }
        }
        return Optional.empty();
    }

    public static Optional<String> containsLettersOnly(String... strings) {
        if (strings == null) throw new IllegalArgumentException("Parametr strings nesmí být null!");

        for (String text : strings) {
            if (!TextUtils.isLetterOnly(text)) {
                return Optional.of("Špatně vyplněné údaje!");
            }
        }
        return Optional.empty();
    }

    public static Optional<String> containsNumbersOnly(String... strings) {
        if (strings == null) throw new IllegalArgumentException("Parametr strings nesmí být null!");

        for (String text : strings) {
            if (!TextUtils.isNumberOnly(text)) {
                return Optional.of("Špatně vyplněné údaje!");
            }
        }
        return Optional.empty();
    }

    public static Optional<String> checkYear(int year) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int actualYear = localDate.getYear();

        if (year > actualYear) {
            return Optional.of("Tento rok ještě nenastal!");
        } else if (year < 1950) {
            return Optional.of("Tento rok je příliš v minulosti!");
        }
        return Optional.empty();
    }

    public static Optional<String> checkOrderDate(LocalDate datePart,String time){
        if (datePart == null) throw new IllegalArgumentException("Parametr datePart nesmí být null!");
        if (TextUtils.isTextEmpty(time)) throw new IllegalArgumentException("Parametr time nesmí být null!");

        LocalTime timePart = LocalTime.parse(time);
        LocalDateTime dt = LocalDateTime.of(datePart, timePart);

        if (dt.isBefore(LocalDateTime.now())){
            return Optional.of("Toto datum a čas již nastalo!");
        }

        return Optional.empty();
    }

    public static Optional<String> checkLengthOfText(String text, int maxLength, int minLength, String maxErrorMessage, String minErrorMessage)
    {
        if (TextUtils.isTextEmpty(maxErrorMessage)) throw new IllegalArgumentException("Parametr maxErrorMessage nesmí být null!");
        if (TextUtils.isTextEmpty(minErrorMessage)) throw new IllegalArgumentException("Parametr minErrorMessage nesmí být null!");
        if (maxLength < 0) throw new IllegalArgumentException("Parametr maxLength nesmí být záporné číslo!");
        if (minLength < 0) throw new IllegalArgumentException("Parametr minLength nesmí být záporné číslo!");
        if (maxLength < minLength) throw new IllegalArgumentException("Parametr maxLength nesmí být menší než minLength");

        if (text.length() < minLength)
            return Optional.of(minErrorMessage);
        else if (text.length() > maxLength)
            return Optional.of(maxErrorMessage);

        return Optional.empty();
    }

    public static Optional<String> checkServices(int... services){
        if (services == null) throw new IllegalArgumentException("Parametr services nesmí být null!");

        for (int item : services) {
            if (item == 1) return Optional.empty();
        }

        return Optional.of("Vyberte typ služby!");
    }
}
