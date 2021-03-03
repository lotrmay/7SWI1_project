package cz.osu.carservice.controllers;

import cz.osu.carservice.models.managers.DatabaseManager;
import cz.osu.carservice.models.utils.DragWindowUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderListFormController implements Initializable {
    @FXML
    private TableView viewOrdersTbv;
    @FXML
    private TableColumn<Map, Object> orderIDColumn;

    @FXML
    private TableColumn<Map,Object> dateColumn;
    @FXML
    private TableColumn<Map, Object> timeColumn;
    @FXML
    private TableColumn<Map, Object> registrationPlateColumn;
    @FXML
    private TableColumn<Map, Object> carTypeColumn;
    @FXML
    private TableColumn<Map, Object> productionYearColumn;
    @FXML
    private TableColumn<Map, Object> customerNameColumn;
    @FXML
    private TableColumn<Map, Object> customerSurnameColumn;
    @FXML
    private TableColumn<Map, Object> telephoneColumn;
    @FXML
    private TableColumn<Map, Object> emailColumn;
    @FXML
    private TableColumn<Map, Object> stateColumn;
    @FXML
    private TableColumn<Map, Object> cityColumn;
    @FXML
    private TableColumn<Map, Object> streetColumn;
    @FXML
    private TableColumn<Map, Object> streetNumberColumn;
    @FXML
    private TableColumn<Map, Object> zipCodeColumn;
    @FXML
    private TableColumn<Map, Object> noteColumn;
    @FXML
    private TableColumn<String,Void> detailColumn;
    @FXML
    private TableColumn editColumn;
    @FXML
    private TableColumn deleteColumn;


    private final DatabaseManager databaseManager;

    public OrderListFormController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    private void returnToMainScene(MouseEvent event){
        try {
            Parent home_page = FXMLLoader.load(getClass().getResource("../forms/mainForm.fxml"));
            Stage app = (Stage)((Node) event.getSource()).getScene().getWindow();

            DragWindowUtils.moveWindow(home_page,app);

            Scene scene = new Scene(home_page);
            scene.setFill(Color.TRANSPARENT);

            app.setScene(scene);
            app.show();
        } catch (IOException e) {
            System.err.println("Došlo k chybě při načítání formuláře mainForm!");
            System.err.println(e.getMessage());
        }
    }
    @FXML
    private void closeApplication(MouseEvent event){
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Map<String, Object>> oList = FXCollections.<Map<String, Object>>observableArrayList();
        ArrayList<Map<String, Object>> items = databaseManager.selectFromDatabase("SELECT `orders`.`id`,`customer`.`firstName`,`customer`.`surname`,`customer`.`telephoneNumber`,`customer`.`email`,`orders`.`note`,`orders`.`date_of_fulfillment`,`orders`.`registration_plate`,`orders`.`type_of_car`,`orders`.`year_of_production`,`address`.`state`,`address`.`city`,`address`.`street`,`address`.`street_number`,`address`.`post_code` FROM `orders` JOIN customer on orders.id_customer = customer.id JOIN address on customer.id_address = address.id WHERE 1");
        orderIDColumn.setCellValueFactory(new MapValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new MapValueFactory<>("firstName"));
        customerSurnameColumn.setCellValueFactory(new MapValueFactory<>("surname"));
        telephoneColumn.setCellValueFactory(new MapValueFactory<>("telephoneNumber"));
        emailColumn.setCellValueFactory(new MapValueFactory<>("email"));
        noteColumn.setCellValueFactory(new MapValueFactory<>("note"));
        dateColumn.setCellValueFactory(new MapValueFactory<>("date"));
        timeColumn.setCellValueFactory(new MapValueFactory<>("time"));
        registrationPlateColumn.setCellValueFactory(new MapValueFactory<>("registration_plate"));
        carTypeColumn.setCellValueFactory(new MapValueFactory<>("type_of_car"));
        productionYearColumn.setCellValueFactory(new MapValueFactory<>("productionYear"));
        stateColumn.setCellValueFactory(new MapValueFactory<>("state"));
        cityColumn.setCellValueFactory(new MapValueFactory<>("city"));
        streetColumn.setCellValueFactory(new MapValueFactory<>("street"));
        streetNumberColumn.setCellValueFactory(new MapValueFactory<>("street_number"));
        zipCodeColumn.setCellValueFactory(new MapValueFactory<>("post_code"));


        for (Map<String,Object> map:items) {
            map.put("productionYear",(java.sql.Date.valueOf(map.get("year_of_production").toString()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy"))));
            map.put("date",((LocalDateTime)map.get("date_of_fulfillment")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            map.put("time",((LocalDateTime)map.get("date_of_fulfillment")).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            oList.add(map);
        }
    viewOrdersTbv.getItems().addAll(oList);

        Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<TableColumn<String, Void>, TableCell<String, Void>>() {
            @Override
            public TableCell<String, Void> call(final TableColumn<String, Void> param) {
                final TableCell<String, Void> cell = new TableCell<String, Void>() {

                    private final Button btn = new Button("Detail");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            HashMap<String,Object> map= (HashMap<String, Object>) viewOrdersTbv.getItems().get(this.getTableRow().getIndex());
                            System.out.println("selectedData: "+map.get("id"));
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        detailColumn.setCellFactory(cellFactory);




    }
}
