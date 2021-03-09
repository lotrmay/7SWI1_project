package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import cz.osu.carservice.models.entities.Order;
import cz.osu.carservice.models.utils.FormUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class ShowFormController extends MainController {

    //region ElementsVariable
    @FXML
    private Label titleShow;
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

    public void initData(Order order) {
        titleShow.setText("Objednávka číslo " + order.getId());

        setTextAndDisable(carPlateTF,order.getRegistration_plate());
        setTextAndDisable(carTypeTF,order.getType_of_car());
        setTextAndDisable(carYearOfProductionTF,String.valueOf(order.getYear_of_production()));
        setTextAndDisable(nameTF,order.getCustomer().getFirstName());
        setTextAndDisable(surnameTF,order.getCustomer().getSurname());
        setTextAndDisable(phoneTF,order.getCustomer().getTelephoneNumber());
        setTextAndDisable(phoneCodeTF,order.getCustomer().getAddress().getState().getTelephone_code());
        setTextAndDisable(emailTF,order.getCustomer().getEmail());
        setTextAndDisable(cityTF,order.getCustomer().getAddress().getCity());
        setTextAndDisable(streetTF,order.getCustomer().getAddress().getStreet());
        setTextAndDisable(streetNumberTF,order.getCustomer().getAddress().getStreetNumber());
        setTextAndDisable(postcodeTF,order.getCustomer().getAddress().getPostCode());
        setTextAndDisable(noteTA,order.getNote());

        countryCB.setValue(order.getCustomer().getAddress().getState().getState_short());
        countryCB.setDisable(true);
        timeOfFulfillmentCB.setValue(order.getTime().getTime().toString());
        timeOfFulfillmentCB.setDisable(true);
        dateOfFulfillmentDT.setValue(order.getDate_of_fulfillment());
        dateOfFulfillmentDT.setDisable(true);

        if(order.getCar_service() == 1) FormUtils.setServiceBtnGreen(carServisBtn);
        if(order.getTire_service() == 1) FormUtils.setServiceBtnGreen(pneuServisBtn);
        if(order.getOther_service() == 1) FormUtils.setServiceBtnGreen(otherServicesBtn);
    }

    @FXML
    private void returnToMainScene(MouseEvent event){
       super.setNewFormScene("orderListForm",event);
    }
    @FXML
    private void closeApplication(MouseEvent event){
        super.closeApplication();
    }

    private void setTextAndDisable(TextInputControl control,String text){
        control.setEditable(false);
        control.setText(text);
    }

}
