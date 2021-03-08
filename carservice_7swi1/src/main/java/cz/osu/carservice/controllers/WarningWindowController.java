package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import cz.osu.carservice.models.utils.DatabaseUtils;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class WarningWindowController extends MainController {
    @FXML
    private void returnToOrderListForm(MouseEvent event){
        super.setNewFormScene("orderListForm",event);
    }
    @FXML
    private void deleteOrder(MouseEvent event){
        DatabaseUtils.removeOrder(1);
        super.setNewFormScene("orderListForm",event);
    }
}
