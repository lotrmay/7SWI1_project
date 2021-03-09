package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import cz.osu.carservice.models.entities.*;
import cz.osu.carservice.models.enums.Services;
import cz.osu.carservice.models.utils.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.net.URL;
import java.security.spec.ECField;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EditFormController extends MainController implements Initializable{
    //region ElementsVariable
    @FXML
    private TextField carPlateTF;
    @FXML
    private TextField carTypeTF;
    @FXML
    private TextField carYearOfProductionTF;
    @FXML
    private DatePicker dateOfFulfillmentDT;
    @FXML
    private ComboBox<String> timeOfFulfillmentCB;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField surnameTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField phoneCodeTF;
    @FXML
    private TextField emailTF;
    @FXML
    private ComboBox<String> countryCB;
    @FXML
    private TextField cityTF;
    @FXML
    private TextField streetTF;
    @FXML
    private TextField streetNumberTF;
    @FXML
    private TextField postcodeTF;
    @FXML
    private TextArea noteTA;
    @FXML
    private Button carServisBtn;
    @FXML
    private Button pneuServisBtn;
    @FXML
    private Button otherServicesBtn;
    @FXML
    private Label messageLBL;
    @FXML
    private Label orderTitleLabel;
    //endregion

    //region Type of Services
    private int carServis;
    private int pneuServis;
    private int otherServices;
    //endregion

    private EntityManager entityManager;
    private Order order;

    public void initData(Order order) {
        this.order = order;

        //region configuration of services buttons
        this.carServis = 0;
        this.pneuServis = 0;
        this.otherServices = 0;

        this.carServisBtn.setUserData(Services.CAR_SERVICES);
        this.pneuServisBtn.setUserData(Services.TIRE_SERVICES);
        this.otherServicesBtn.setUserData(Services.OTHER_SERVICES);
        //endregion

        orderTitleLabel.setText("Změnit objednávku č." + order.getId());

        carPlateTF.setText(order.getRegistration_plate());
        carTypeTF.setText(order.getType_of_car());
        carYearOfProductionTF.setText(String.valueOf(order.getYear_of_production()));
        nameTF.setText(order.getCustomer().getFirstName());
        surnameTF.setText(order.getCustomer().getSurname());
        phoneTF.setText(order.getCustomer().getTelephoneNumber());
        phoneCodeTF.setText(order.getCustomer().getAddress().getState().getTelephone_code());
        emailTF.setText(order.getCustomer().getEmail());
        cityTF.setText(order.getCustomer().getAddress().getCity());
        streetTF.setText(order.getCustomer().getAddress().getStreet());
        streetNumberTF.setText(order.getCustomer().getAddress().getStreetNumber());
        postcodeTF.setText(order.getCustomer().getAddress().getPostCode());
        noteTA.setText(order.getNote());

        countryCB.setValue(order.getCustomer().getAddress().getState().getState_short());
        timeOfFulfillmentCB.setValue(order.getTime().getTime().toString());
        dateOfFulfillmentDT.setValue(order.getDate_of_fulfillment());

        if(order.getCar_service() == 1){
            this.carServis = 1;
            FormUtils.setServiceBtnGreen(carServisBtn);
        }
        if(order.getTire_service() == 1){
            this.pneuServis = 1;
            FormUtils.setServiceBtnGreen(pneuServisBtn);
        }
        if(order.getOther_service() == 1){
            this.otherServices = 1;
            FormUtils.setServiceBtnGreen(otherServicesBtn);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLBL.setText("");

        entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            List<State> states = entityManager.createNamedQuery("State.findAll", State.class)
                    .getResultList();
            List<RegistrationTime> registrationTimes = entityManager.createNamedQuery("RegistrationTime.findAll", RegistrationTime.class)
                    .getResultList();

            states.forEach(param -> {
                countryCB.getItems().add(param.getState_short());
            });
            registrationTimes.forEach(param -> {
                timeOfFulfillmentCB.getItems().add(String.valueOf(param.getTime()));
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    @FXML
    private void onChangeItem() {
        entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            State statePhoneCode = entityManager.createNamedQuery("State.findByShortCut", State.class)
                    .setParameter("state_shortcut", countryCB.getSelectionModel().getSelectedItem())
                    .getSingleResult();
            phoneCodeTF.setText(statePhoneCode.getTelephone_code());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    @FXML
    private void onClickValidateServicesBtn(MouseEvent event) {
        Button btn = (Button) event.getSource();
        Services service = (Services) ((Button) event.getSource()).getUserData();

        switch (service) {
            case CAR_SERVICES -> {
                this.carServis = (this.carServis == 0) ? 1 : 0;
                switch (this.carServis) {
                    case 0 -> FormUtils.setServiceBtnWhite(btn);

                    case 1 -> FormUtils.setServiceBtnGreen(btn);
                }
            }
            case TIRE_SERVICES -> {
                this.pneuServis = (this.pneuServis == 0) ? 1 : 0;
                switch (this.pneuServis) {
                    case 0 -> FormUtils.setServiceBtnWhite(btn);

                    case 1 -> FormUtils.setServiceBtnGreen(btn);
                }
            }
            case OTHER_SERVICES -> {
                this.otherServices = (this.otherServices == 0) ? 1 : 0;
                switch (this.otherServices) {
                    case 0 -> FormUtils.setServiceBtnWhite(btn);

                    case 1 -> FormUtils.setServiceBtnGreen(btn);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + service);
        }
    }
    @FXML
    private void returnToMainScene(MouseEvent event){
        super.setNewFormScene("mainForm",event);
    }
    @FXML
    private void closeApplication(MouseEvent event){
        super.closeApplication();
    }
    @FXML
    private void updateOrder(){
        //region getDataFromTextFields
        String city = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces(cityTF.getText()));
        String street = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces(streetTF.getText()));
        String streetNumber = TextUtils.removeAllWhiteSpaces(streetNumberTF.getText());
        String postCode = TextUtils.removeAllWhiteSpaces(postcodeTF.getText());

        String name = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces(nameTF.getText()));
        String surname = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces(surnameTF.getText()));
        String phone = TextUtils.removeAllWhiteSpaces(phoneTF.getText());
        String email = TextUtils.removeAllWhiteSpaces(emailTF.getText());

        String carType = TextUtils.firstUpperRestLower(TextUtils.removeAllWhiteSpaces(carTypeTF.getText()));
        String carPlate = TextUtils.removeAllWhiteSpaces(carPlateTF.getText());
        Time time = ConversionUtils.getTimeFromString(timeOfFulfillmentCB.getValue());
        String note = noteTA.getText();

        LocalDate dateOfFulfillment = null;
        int carYearOfProduction = 0;
        try {
            carYearOfProduction = Integer.parseInt(TextUtils.removeAllWhiteSpaces(carYearOfProductionTF.getText()));
            dateOfFulfillment = dateOfFulfillmentDT.getValue();
        }catch (Exception e){
            FormUtils.setTextAndRedColorToLabel(messageLBL,"Data jsou ve špatném formátu!");
            return;
        }
        //endregion

        if(!ValidationUtils.checkGrammarRules(messageLBL,carPlate,carType,String.valueOf(carYearOfProduction),
                dateOfFulfillment,String.valueOf(time),
                name,surname,phone,email,city,street,streetNumber,postCode,carServis,pneuServis,otherServices))
            return;


        entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        RegistrationTime timeForRegistration = null;
        try {
            timeForRegistration = DatabaseUtils.getRegistrationTimeForOrder(entityManager,time);
            Order order = DatabaseUtils.checkIfRegistrationTimeIsReserved(entityManager,timeForRegistration,dateOfFulfillmentDT.getValue());

            if (order.getId() == this.order.getId())
                throw new NoResultException();

            FormUtils.setTextAndRedColorToLabel(messageLBL,"Termín je zabrán objednávkou č. " + order.getId());
        }catch (NoResultException exception){
            Address address = DatabaseUtils.getAddressForCustomer(entityManager,city,street,streetNumber,postCode,countryCB.getValue());
            Customer customer = DatabaseUtils.getCustomerForOrder(entityManager,name,surname,phone,email,address);
            orderUpdate(entityManager,carType,carPlate,carYearOfProduction,dateOfFulfillment,note,carServis,pneuServis,otherServices,customer,timeForRegistration);

            FormUtils.setTextAndGreenColorToLabel(messageLBL,"Objednávka byla úspěšně změněna !");
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }finally {
            entityManager.close();
        }

    }

    private void orderUpdate(EntityManager entityManager, String carType, String carPlate, int carYearOfProduction, LocalDate dateOfFulfillment, String note, int carServis, int pneuServis, int otherServices, Customer customer, RegistrationTime timeForRegistration) {

        try {
            entityManager.getTransaction().begin();

            order.setType_of_car(carType);
            order.setRegistration_plate(carPlate);
            order.setYear_of_production(carYearOfProduction);
            order.setDate_of_fulfillment(dateOfFulfillment);
            order.setNote(note);
            order.setCar_service(carServis);
            order.setTire_service(pneuServis);
            order.setOther_service(otherServices);
            order.setCustomer(customer);
            order.setTime(timeForRegistration);

            entityManager.merge(order);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
