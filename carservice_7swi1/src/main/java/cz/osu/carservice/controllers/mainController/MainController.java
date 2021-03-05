package cz.osu.carservice.controllers.mainController;

import cz.osu.carservice.models.managers.DatabaseManager;
import cz.osu.carservice.models.utils.DragWindowUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    protected final DatabaseManager databaseManager;

    protected MainController() {
        databaseManager = new DatabaseManager();
    }

    protected final void setNewFormScene(String form, Event event) {
        try {
            Parent home_page = FXMLLoader.load(getClass().getResource("../forms/"+ form +".fxml"));
            Stage app = (Stage)((Node) event.getSource()).getScene().getWindow();

            DragWindowUtils.moveWindow(home_page,app);

            Scene scene = new Scene(home_page);
            scene.setFill(Color.TRANSPARENT);

            app.setScene(scene);
            app.show();
        } catch (IOException e) {
            System.err.println("Došlo k chybě při načítání formuláře "+form+"!");
            System.err.println(e.getMessage());
        }
    }

    protected final void closeApplication(){
        Platform.exit();
        System.exit(0);
    }

}
