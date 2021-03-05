package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    //endregion

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> strings = new ArrayList<>();
        strings.add("CZ");
        strings.add("SK");
        countryCB.setItems(FXCollections.observableArrayList(strings));
        countryCB.getSelectionModel().selectFirst();

        List<String> times = new ArrayList<>();
        times.add("13:00:00");
        times.add("14:00:00");
        times.add("15:00:00");
        timeOfFulfillmentCB.setItems(FXCollections.observableArrayList(times));
        timeOfFulfillmentCB.getSelectionModel().selectFirst();
    }

    @FXML
    private void closeApplication(MouseEvent event){
        super.closeApplication();
    }

    @FXML
    private void returnToMainScene(MouseEvent event){
      super.setNewFormScene("mainForm",event);
    }

    @FXML
    private void insertOrder(MouseEvent event){
        //TODO validation

        //TODO check if records exists

        insertIntoAddress();
        insertIntoCustomer(1);
        insertIntoOrders(1);
    }

    private void insertIntoCustomer(int idAddress){
        String sqlInsertCustomer = "INSERT INTO `customer` " +
                "(`id`, `id_address`, `firstName`, `surname`, `telephoneNumber`, `email`) " +
                "VALUES (NULL, ?, ?, ?, ?, ?); ";

        ArrayList<String> data = new ArrayList<>();

        data.add(String.valueOf(idAddress));
        data.add(nameTF.getText());
        data.add(surnameTF.getText());
        data.add(phoneTF.getText());
        data.add(emailTF.getText());

        databaseManager.insertIntoDatabase(sqlInsertCustomer,data);
    }

    private void insertIntoAddress(){
        String sqlInsertCustomer = "INSERT INTO `address` " +
                "(`id`, `state`, `city`, `street`, `street_number`, `post_code`) " +
                "VALUES (NULL, ?, ?, ?, ?, ?); ";

        ArrayList<String> data = new ArrayList<>();

        data.add(String.valueOf(countryCB.getValue()));
        data.add(cityTF.getText());
        data.add(streetTF.getText());
        data.add(streetNumberTF.getText());
        data.add(postcodeTF.getText());

        databaseManager.insertIntoDatabase(sqlInsertCustomer,data);
    }

    private void insertIntoOrders(int idCustomer) {

        String sqlInsertCustomer = "INSERT INTO `orders` " +
                "(`id`, `id_customer`, `registration_plate`, `type_of_car`, `date_of_fulfillment`, `year_of_production`, `car_service`, `tire_service`, `other_service`, `note`) " +
                "VALUES (NULL, ?, ?, ?, ?, ?,?,?,?,?); ";

        try {
            ArrayList<String> data = new ArrayList<>();

            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            String dateTime = ft.format(ft.parse(Objects.requireNonNull(this.dateOfFulfillmentDT.getValue()).toString()))+ " " +timeOfFulfillmentCB.getValue();

            data.add(String.valueOf(idCustomer));
            data.add(carPlateTF.getText());
            data.add(carTypeTF.getText());
            data.add(dateTime);
            data.add(carYearOfProductionTF.getText());
            data.add("1");
            data.add("1");
            data.add("1");
            data.add(noteTA.getText());

            databaseManager.insertIntoDatabase(sqlInsertCustomer,data);
        } catch (ParseException e) {
            System.err.println("Došlo k chybě při převodu datumu!");
            System.err.println(e.getMessage());
        }

    }
}
