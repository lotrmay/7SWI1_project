package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class EditFormController extends MainController {
    @FXML
    private void returnToMainScene(MouseEvent event){
        super.setNewFormScene("mainForm",event);
    }
    @FXML
    private void closeApplication(MouseEvent event){
        super.closeApplication();
    }
}
