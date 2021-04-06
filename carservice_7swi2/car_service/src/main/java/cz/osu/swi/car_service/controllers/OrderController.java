package cz.osu.swi.car_service.controllers;

import cz.osu.swi.car_service.utils.ConversionUtils;
import cz.osu.swi.car_service.utils.TextUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping(path = "/OrderCreation")
@CrossOrigin("*")
public class OrderController {

    @PostMapping("/post")
    public void createOrder(@RequestBody Map<String, Object> payLoad) {

        String city = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderCityTitle")));
        String street = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderStreetTitle")));
        String streetNumber = TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderStreetCodeTitle"));
        String postCode = TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderPostCodeTitle"));

        String name = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderNameTitle")));
        String surname = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderSurnameTitle")));
        String phone = TextUtils.removeAllWhiteSpaces((String) payLoad.get("telephoneInputTitle"));
        String email = TextUtils.removeAllWhiteSpaces((String) payLoad.get("orderEmailTitle"));

        String carType = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces((String) payLoad.get("carTypeTitle")));
        String carPlate = TextUtils.removeAllWhiteSpaces((String) payLoad.get("carTimeTitle"));

        int carServis = (Integer) payLoad.get("carServicesTitle");
        int pneuServis = (Integer) payLoad.get("pneuServisTitle");
        int otherServices = (Integer) payLoad.get("otherServicesTitle");

        Time time = ConversionUtils.getTimeFromString((String) payLoad.get("note"));
        String note = (String) payLoad.get("note");

        LocalDate dateOfFulfillment = null;
        int carYearOfProduction = 0;
        try {
            carYearOfProduction = Integer.parseInt(TextUtils.removeAllWhiteSpaces((String) payLoad.get("carYearTitle")));
            //dateOfFulfillment = dateOfFulfillmentDT.getValue();
        } catch (Exception e) {
            //FormUtils.setTextAndColorToLabel(messageLBL,"Data jsou ve špatném formátu!",Colors.HEX_COLOR_RED);
            return;
        }
/*
        if(!ValidationUtils.checkGrammarRules(messageLBL,carPlate,carType,String.valueOf(carYearOfProduction),
                dateOfFulfillment,String.valueOf(time),
                name,surname,phone,email,city,street,streetNumber,postCode,carServis,pneuServis,otherServices))
            return;


        entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        RegistrationTime timeForRegistration = null;
        try {
            timeForRegistration = DatabaseUtils.getRegistrationTimeForOrder(entityManager,time);
            Order order = DatabaseUtils.checkIfRegistrationTimeIsReserved(entityManager,timeForRegistration,dateOfFulfillmentDT.getValue());

            FormUtils.setTextAndColorToLabel(messageLBL,"Termín je zabrán objednávkou č. " + order.getId(),Colors.HEX_COLOR_RED);
        }catch (NoResultException exception){
            Address address = DatabaseUtils.getAddressForCustomer(entityManager,city,street,streetNumber,postCode,countryCB.getValue());
            Customer customer = DatabaseUtils.getCustomerForOrder(entityManager,name,surname,phone,email,address);

            Order order = new Order(carPlate,carType,dateOfFulfillment,carYearOfProduction,carServis,pneuServis,otherServices,note,customer,timeForRegistration);

            entityManager.getTransaction().begin();
            entityManager.persist(order);
            entityManager.getTransaction().commit();

            FormUtils.setTextAndColorToLabel(messageLBL,"Objednávka byla úspěšně zaregistrována !",Colors.HEX_COLOR_GREEN_LIGHT);
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }finally {
            entityManager.close();
        }

*/
    }
}
