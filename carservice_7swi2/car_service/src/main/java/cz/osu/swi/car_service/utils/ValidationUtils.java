package cz.osu.swi.car_service.utils;

import cz.osu.swi.car_service.models.Order;
import cz.osu.swi.car_service.models.RegistrationTime;
import cz.osu.swi.car_service.services.OrderService;
import cz.osu.swi.car_service.services.RegistrationTimeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class ValidationUtils {


    /**
     * Does the overall validation of the inputs by calling other ValidationUtils methods.
     * Uses {@link #checkServices(int...)} to check services.
     * Uses {@link #containsEmptyStrings(String...)} to check for empty strings.
     * Uses {@link #containsLettersOnly(String...)} to check that strings contains only letters.
     * Uses {@link #containsNumbersOnly(String...)} to check strings if contains only numbers.
     * Uses {@link TextUtils#isValidEmailAddress(String)} to check if email is valid.
     * Uses {@link #checkYear(int)} to check if year is valid.
     * Uses {@link TextUtils#checkTelephoneFormat(String)} to check if telephone format is valid.
     * Uses {@link #checkOrderDate(LocalDate, String)} to check if order date is valid.
     * Uses {@link #checkLengthOfText(String, int, int, String, String)} to check if text fulfills length constraints.
     *
     * @param registrationPlate String that represents registration plate of a car
     * @param typeOfCar String that represents type of a car
     * @param yearOfProduction String that represents year of production of a car
     * @param dateOfOrder String that represents order fulfillment date
     * @param time String that represents time for order creation
     * @param name String that represents name of customer
     * @param surname String that represents surname of customer
     * @param telephone String that represents telephone of customer
     * @param email String that represents email of customer
     * @param city String that represents city of customer
     * @param street String that represents street of customer
     * @param streetCode String that represents streetCode of customer
     * @param postCode String that represents postCode of customer
     * @param carService int that represents service that customer picked
     * @param tireService int that represents service that customer picked
     * @param otherService int that represents service that customer picked
     * @param registrationTimeService service that is used
     * @param orderService service that is used
     *
     * @return Optional.empty if all inputs are valid, otherwise it returns Optional.of(error message)
     */
    public static Optional<String> checkOrderData(String registrationPlate, String typeOfCar, String yearOfProduction, String dateOfOrder, String time,
                                                  String name, String surname, String telephone, String email,
                                                  String city, String street, String streetCode, String postCode, int carService, int tireService, int otherService,
                                                  RegistrationTimeService registrationTimeService, OrderService orderService) {

        Optional<String> temp;

        temp = checkServices(carService,tireService,otherService);
        if (temp.isPresent()) return temp;

        temp = containsEmptyStrings(registrationPlate, typeOfCar, yearOfProduction, dateOfOrder, name, surname, telephone, email, city, street, streetCode, postCode);
        if (temp.isPresent()) return temp;

        temp = containsLettersOnly(typeOfCar, name, surname, city, street);
        if (temp.isPresent()) return temp;

        temp = containsNumbersOnly(yearOfProduction, telephone, postCode);
        if (temp.isPresent()) return temp;

        LocalDate dateOfFulfillment = null;
        int carYearOfProduction = 0;

        try {
            carYearOfProduction = Integer.parseInt(TextUtils.removeAllWhiteSpaces(yearOfProduction));
            dateOfFulfillment = ConversionUtils.convertStringToLocalDate(dateOfOrder);
        } catch (Exception e) {
            return Optional.of("Data jsou ve špatném formátu!");
        }

        if (!TextUtils.isValidEmailAddress(email))
            return Optional.of("Špatně zadaný email!");

        temp = checkYear(carYearOfProduction);
        if (temp.isPresent()) return temp;

        if (!TextUtils.checkTelephoneFormat(telephone))
            return Optional.of("Špatně zadaný telefon!");

        temp = checkOrderDate(dateOfFulfillment,time);
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

        RegistrationTime timeForRegistration = registrationTimeService.getRegistrationTimeForOrder(ConversionUtils.getTimeFromString(time));
        Order order = orderService.checkIfRegistrationTimeIsReserved(dateOfFulfillment, timeForRegistration);

        if (order != null)
            return Optional.of("Termín je zabrán objednávkou č. " + order.getId());

        return Optional.empty();
    }

    /**
     * Checks if every value in an array of strings is not empty.
     * Uses {@link TextUtils#isTextEmpty(String)} to check if String is not empty.
     *
     * @param strings array of strings to be checked
     * @return Optional.empty if the array contains only strings that are not empty, otherwise it returns Optional.of(error message)
     */
    public static Optional<String> containsEmptyStrings(String... strings) {
        if (strings == null) throw new IllegalArgumentException("Parametr strings nesmí být null!");

        for (String text : strings) {
            if (TextUtils.isTextEmpty(text)) {
                return Optional.of("Vyplňte všechny údaje!");
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if every value in an array of strings contains only letters.
     * Uses {@link TextUtils#isLetterOnly(String)} to check if String contains only letters.
     *
     * @param strings array of strings to be checked
     * @return Optional.empty if the array contains only strings filled with letters, otherwise it returns Optional.of(error message)
     */
    public static Optional<String> containsLettersOnly(String... strings) {
        if (strings == null) throw new IllegalArgumentException("Parametr strings nesmí být null!");

        for (String text : strings) {
            if (!TextUtils.isLetterOnly(text)) {
                return Optional.of("Špatně vyplněné údaje!");
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if every value in an array of strings contains only numbers.
     * Uses {@link TextUtils#isNumberOnly(String)} to check if String contains only numbers.
     *
     * @param strings array of strings to be checked
     * @return Optional.empty if the array contains only strings filled with numbers, otherwise it returns Optional.of(error message)
     */
    public static Optional<String> containsNumbersOnly(String... strings) {
        if (strings == null) throw new IllegalArgumentException("Parametr strings nesmí být null!");

        for (String text : strings) {
            if (!TextUtils.isNumberOnly(text)) {
                return Optional.of("Špatně vyplněné údaje!");
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if a year is not in the future or before 1950.
     *
     * @param year int to be checked
     * @return Optional.empty if the year is not in the future or before 1950, otherwise it returns Optional.of(error message)
     */
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

    /**
     * Checks if a date already happened.
     * Uses {@link TextUtils#isTextEmpty(String)} to check if time is not empty.
     *
     * @param datePart LocalDate to be combined with time for a check
     * @param time String to be combined with datePart for a check
     * @return Optional.empty if the date is in the future, otherwise it returns Optional.of(error message)
     */
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

    /**
     * Checks text for minimum and maximum length.
     * Uses {@link TextUtils#isTextEmpty(String)} to check if error messages are not empty.
     *
     * @param text String to be checked for length constraints
     * @param maxLength sets maximum length constraint for text
     * @param minLength sets minimum length constraint for text
     * @param maxErrorMessage error message to be displayed if text exceeds max length constraint
     * @param minErrorMessage error message to be displayed if text does not exceed min length constraint
     * @return Optional.empty if text fulfills length constraints, otherwise it returns Optional.of(ErrorMessage)
     */
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

    /**
     * Checks if at least one service was picked.
     * A service is picked when it has value equal to one.
     *
     * @param services array of integers that represents services
     * @return Optional.empty if at least one service was picked, otherwise it returns Optional.of(error message)
     */
    public static Optional<String> checkServices(int... services){
        if (services == null) throw new IllegalArgumentException("Parametr services nesmí být null!");

        for (int item : services) {
            if (item == 1) return Optional.empty();
        }

        return Optional.of("Vyberte typ služby!");
    }


}
