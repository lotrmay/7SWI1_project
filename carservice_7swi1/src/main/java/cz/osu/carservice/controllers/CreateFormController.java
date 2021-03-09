package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import cz.osu.carservice.models.entities.*;
import cz.osu.carservice.models.enums.Services;
import cz.osu.carservice.models.utils.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.persistence.*;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CreateFormController extends MainController implements Initializable {
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
    //endregion

    //region Type of Services
    private int carServis;
    private int pneuServis;
    private int otherServices;
    //endregion

    private EntityManager entityManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLBL.setText("");
        dateOfFulfillmentDT.setValue(LocalDate.now());

        //region configuration of services buttons
        this.carServis = 0;
        this.pneuServis = 0;
        this.otherServices = 0;

        this.carServisBtn.setUserData(Services.CAR_SERVICES);
        this.pneuServisBtn.setUserData(Services.TIRE_SERVICES);
        this.otherServicesBtn.setUserData(Services.OTHER_SERVICES);
        //endregion

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

            phoneCodeTF.setText(states.get(0).getTelephone_code());
            timeOfFulfillmentCB.getSelectionModel().select(0);
            countryCB.getSelectionModel().select(0);
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
    private void closeApplication(MouseEvent event) {
        super.closeApplication();
    }

    @FXML
    private void returnToMainScene(MouseEvent event) {
        super.setNewFormScene("mainForm", event);
    }

    @FXML
    private void insertOrder(MouseEvent event) {

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

            FormUtils.setTextAndRedColorToLabel(messageLBL,"Termín je zabrán objednávkou č. " + order.getId());
        }catch (NoResultException exception){
            Address address = DatabaseUtils.getAddressForCustomer(entityManager,city,street,streetNumber,postCode,countryCB.getValue());
            Customer customer = DatabaseUtils.getCustomerForOrder(entityManager,name,surname,phone,email,address);
            addOrder(entityManager,carType,carPlate,carYearOfProduction,dateOfFulfillment,note,carServis,pneuServis,otherServices,customer,timeForRegistration);

            FormUtils.setTextAndGreenColorToLabel(messageLBL,"Objednávka byla úspěšně zaregistrována !");
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }finally {
            entityManager.close();
        }

    }

    private void addOrder(EntityManager entityManager,String carType, String carPlate, int carYearOfProduction,LocalDate dateOfFulfilment, String note,int carServis,int pneuServis, int otherServices, Customer customer, RegistrationTime time){

        try {
            Order order = new Order(carPlate,carType,dateOfFulfilment,carYearOfProduction,carServis,pneuServis,otherServices,note,customer,time);

            entityManager.getTransaction().begin();
            entityManager.persist(order);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
