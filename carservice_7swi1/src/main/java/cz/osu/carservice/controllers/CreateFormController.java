package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import cz.osu.carservice.models.entities.*;
import cz.osu.carservice.models.enums.Services;
import cz.osu.carservice.models.utils.ConversionUtils;
import cz.osu.carservice.models.utils.DatabaseUtils;
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

    //region Style constants
    private static final String HEX_COLOR_WHITE = "#ffffff";
    private static final String HEX_COLOR_BLACK = "#000000";
    private static final String HEX_COLOR_GREEN = "#8cff66";
    private static final String HEX_COLOR_RED = "#f55d42";
    private static final String BORDER_RADIUS = "60";
    private static final String BACKGROUND_RADIUS = "60";
    //endregion

    //region Variables Collections
    private List<State> states;
    private List<RegistrationTime> registrationTimes;
    private EntityManager entityManager;
    //endregion
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLBL.setText("");

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
            states = entityManager.createNamedQuery("State.findAll", State.class)
                    .getResultList();
            registrationTimes = entityManager.createNamedQuery("RegistrationTime.findAll", RegistrationTime.class)
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
                    case 0 -> setServiceBtnWhite(btn);

                    case 1 -> setServiceBtnGreen(btn);
                }
            }
            case TIRE_SERVICES -> {
                this.pneuServis = (this.pneuServis == 0) ? 1 : 0;
                switch (this.pneuServis) {
                    case 0 -> setServiceBtnWhite(btn);

                    case 1 -> setServiceBtnGreen(btn);
                }
            }
            case OTHER_SERVICES -> {
                this.otherServices = (this.otherServices == 0) ? 1 : 0;
                switch (this.otherServices) {
                    case 0 -> setServiceBtnWhite(btn);

                    case 1 -> setServiceBtnGreen(btn);
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
        //TODO Validace ostatních dat

        //region getDataFromTextFields
        String city = cityTF.getText();
        String street = streetTF.getText();
        String streetNumber = streetNumberTF.getText();
        String postCode = postcodeTF.getText();

        String name = nameTF.getText();
        String surname = surnameTF.getText();
        String phone = phoneTF.getText();
        String email = emailTF.getText();

        String carType = carTypeTF.getText();
        String carPlate = carPlateTF.getText();
        int carYearOfProduction = Integer.parseInt(carYearOfProductionTF.getText());
        LocalDate dateOfFulfillment = dateOfFulfillmentDT.getValue();
        String note = noteTA.getText();
        //endregion
        
        Time time = ConversionUtils.getTimeFromString(timeOfFulfillmentCB.getValue());

        entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        RegistrationTime timeForRegistration = null;
        try {
            timeForRegistration = DatabaseUtils.getRegistrationTimeForOrder(entityManager,time);
            Order order = DatabaseUtils.checkIfRegistrationTimeIsReserved(entityManager,timeForRegistration,dateOfFulfillmentDT.getValue());

            messageLBL.setText("Termín je zabrán objednávkou č. " + order.getId());
            messageLBL.setStyle(String.format("-fx-text-fill: %s;", HEX_COLOR_RED));
        }catch (NoResultException exception){
            Address address = DatabaseUtils.getAddressForCustomer(entityManager,city,street,streetNumber,postCode,countryCB.getValue());
            Customer customer = DatabaseUtils.getCustomerForOrder(entityManager,name,surname,phone,email,address);
            DatabaseUtils.insertOrder(entityManager,carType,carPlate,carYearOfProduction,dateOfFulfillment,note,carServis,pneuServis,otherServices,customer,timeForRegistration);

            messageLBL.setText("Objednávka byla úspěšně zaregistrována !");
            messageLBL.setStyle(String.format("-fx-text-fill: %s;", HEX_COLOR_GREEN));
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }finally {
            entityManager.close();
        }

    }

    private void setServiceBtnGreen(Button btn) {
        btn.setStyle(String.format("-fx-background-color: %s;" +
                        "-fx-border-color: %s;" +
                        "-fx-border-radius: %s;" +
                        "-fx-background-radius: %s;",
                HEX_COLOR_GREEN, HEX_COLOR_BLACK, BORDER_RADIUS, BACKGROUND_RADIUS));
    }

    private void setServiceBtnWhite(Button btn) {
        btn.setStyle(String.format("-fx-background-color: %s;" +
                        "-fx-border-color: %s;" +
                        "-fx-border-radius: %s;" +
                        "-fx-background-radius: %s;",
                HEX_COLOR_WHITE, HEX_COLOR_BLACK, BORDER_RADIUS, BACKGROUND_RADIUS));
    }
}
