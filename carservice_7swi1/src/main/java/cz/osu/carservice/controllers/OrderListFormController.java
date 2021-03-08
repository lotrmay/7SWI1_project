package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import cz.osu.carservice.models.entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class OrderListFormController extends MainController implements Initializable {
    //region ElementsVariable
    @FXML
    private TableView<Order> viewOrdersTbv;
    @FXML
    private TableColumn<Order, Long> orderIDColumn;
    @FXML
    private TableColumn<Order,LocalDateTime> dateColumn;
    @FXML
    private TableColumn<Order, String> registrationPlateColumn;
    @FXML
    private TableColumn<Order, String> carTypeColumn;
    @FXML
    private TableColumn<Order, Integer> productionYearColumn;
    @FXML
    private TableColumn<Order, String> noteColumn;
    @FXML
    private TableColumn<Order, String> customerNameColumn;
    @FXML
    private TableColumn<Order, String> customerSurnameColumn;
    @FXML
    private TableColumn<Order, String> telephoneColumn;
    @FXML
    private TableColumn<Order, String> emailColumn;
    @FXML
    private TableColumn<Order, String> stateColumn;
    @FXML
    private TableColumn<Order, String> cityColumn;
    @FXML
    private TableColumn<Order, String> streetColumn;
    @FXML
    private TableColumn<Order, String> streetNumberColumn;
    @FXML
    private TableColumn<Order, String> zipCodeColumn;
    @FXML
    private TableColumn<Order, String> timeColumn;
    @FXML
    private TableColumn<Order, String> carServiceColumn;
    @FXML
    private TableColumn<Order, String> tireServiceColumn;
    @FXML
    private TableColumn<Order, String> otherServiceColumn;
    @FXML
    private TableColumn<String,Void> detailColumn;
    @FXML
    private TableColumn<String,Void> editColumn;
    @FXML
    private TableColumn<String,Void> deleteColumn;
    //endregion

    //region Colors
    private final static String HEX_COLOR_GREEN = "#6cc969";
    private final static String HEX_COLOR_BLUE = "#60aac4";
    private final static String HEX_COLOR_RED = "#cf3232";
    //endregion

    @FXML
    private void returnToMainScene(MouseEvent event){
        super.setNewFormScene("mainForm",event);
    }
    @FXML
    private void closeApplication(MouseEvent event){
        super.closeApplication();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            List<Order> orders = entityManager.createNamedQuery("Order.findAll",Order.class)
                    .getResultList();

            ObservableList<Order> orderCollections = FXCollections.observableArrayList(orders);

            setDataToColumns(orderCollections);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }

        setButtonsToColumn(detailColumn,"Detail",HEX_COLOR_GREEN);
        setButtonsToColumn(editColumn,"Edit",HEX_COLOR_BLUE);
        setButtonsToColumn(deleteColumn,"Delete",HEX_COLOR_RED);
    }

    private void setButtonsToColumn(TableColumn<String,Void> column,String nameOfButton,String buttonColor){
        column.setCellFactory(new Callback<>() {
            @Override
            public TableCell<String, Void> call(final TableColumn<String, Void> param) {
                final TableCell<String, Void> cell = new TableCell<>() {
                    private final Button btn = new Button(nameOfButton);
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Order order = viewOrdersTbv.getItems().get(this.getTableRow().getIndex());
                            System.out.println("selectedData: " + order.getId());
                        });
                        btn.setStyle(String.format("-fx-background-color: %s;", buttonColor));
                        btn.setCursor(Cursor.HAND);
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
                cell.setStyle("-fx-alignment: CENTER;");
                return cell;
            }
        });
    }
    private void setDataToColumns(ObservableList<Order> list){

        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date_of_fulfillment"));
        registrationPlateColumn.setCellValueFactory(new PropertyValueFactory<>("Registration_plate"));
        carTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type_of_car"));
        productionYearColumn.setCellValueFactory(new PropertyValueFactory<>("Year_of_production"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("Note"));

        carServiceColumn.setCellValueFactory((data -> new SimpleStringProperty(data.getValue().getCar_service() == 1 ? "Ano" : "Ne" )));
        tireServiceColumn.setCellValueFactory((data -> new SimpleStringProperty(data.getValue().getTire_service() == 1 ? "Ano" : "Ne" )));
        otherServiceColumn.setCellValueFactory((data -> new SimpleStringProperty(data.getValue().getOther_service() == 1 ? "Ano" : "Ne" )));

        customerNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getFirstName()));
        customerSurnameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getSurname()));
        telephoneColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getTelephoneNumber()));
        emailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getEmail()));

        stateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getAddress().getState().getState_short()));
        cityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getAddress().getCity()));
        streetColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getAddress().getStreet()));
        streetNumberColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getAddress().getStreetNumber()));
        zipCodeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getAddress().getPostCode()));

        timeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTime().getTime().toString()));

        viewOrdersTbv.setItems(list);
    }
}
