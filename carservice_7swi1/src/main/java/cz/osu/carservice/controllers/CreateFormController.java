package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import cz.osu.carservice.models.entities.RegistrationTime;
import cz.osu.carservice.models.entities.States;
import cz.osu.carservice.models.enums.Services;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.persistence.*;
import java.net.URL;
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
    private static final String BORDER_RADIUS = "60";
    private static final String BACKGROUND_RADIUS = "60";
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //region configuration of services buttons
        this.carServis = 0;
        this.pneuServis = 0;
        this.otherServices = 0;

        this.carServisBtn.setUserData(Services.CAR_SERVICES);
        this.pneuServisBtn.setUserData(Services.TIRE_SERVICES);
        this.otherServicesBtn.setUserData(Services.OTHER_SERVICES);
        //endregion

        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            List<States> statesNames = entityManager.createNamedQuery("States.findAll", States.class)
                    .getResultList();
            List<RegistrationTime> registrationTimes = entityManager.createNamedQuery("RegistrationTimes.findAll", RegistrationTime.class)
                    .getResultList();

            statesNames.forEach(param -> {
                countryCB.getItems().add(param.getState_short());
            });
            registrationTimes.forEach(param -> {
                timeOfFulfillmentCB.getItems().add(String.valueOf(param.getTime()));
            });

            phoneCodeTF.setText(statesNames.get(0).getTelephone_code());
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
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            States statesPhoneCode = entityManager.createNamedQuery("States.findByShortCut", States.class)
                    .setParameter("state_shortcut", countryCB.getSelectionModel().getSelectedItem())
                    .getSingleResult();
            phoneCodeTF.setText(statesPhoneCode.getTelephone_code());
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
        //TODO Column/SIZE anotace...

        //TODO validation

        //TODO check if records exists
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
