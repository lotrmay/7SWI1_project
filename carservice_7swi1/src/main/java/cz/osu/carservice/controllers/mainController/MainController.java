package cz.osu.carservice.controllers.mainController;

import cz.osu.carservice.controllers.EditFormController;
import cz.osu.carservice.controllers.ShowFormController;
import cz.osu.carservice.controllers.WarningFormController;
import cz.osu.carservice.models.entities.Order;
import cz.osu.carservice.models.utils.DragWindowUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class MainController {

    protected static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("MySQL");

    protected final void setNewFormScene(String form, Event event) {
        try {
            Parent home_page = FXMLLoader.load(getClass().getResource("/forms/" + form + ".fxml"));
            Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();

            DragWindowUtils.moveWindow(home_page, app);

            Scene scene = new Scene(home_page);
            scene.setFill(Color.TRANSPARENT);

            app.setScene(scene);
            app.show();
        } catch (IOException e) {
            System.err.println("Došlo k chybě při načítání formuláře " + form + "!");
            System.err.println(e.getMessage());
        }
    }

    protected final void setNewFormScene(String formName, Order order, Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/forms/" + formName + ".fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            DragWindowUtils.moveWindow(root, stage);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            Class<?> controllerClass = (Class<?>) (((Node) event.getSource()).getUserData());

            if (controllerClass.equals(ShowFormController.class)) {
                ShowFormController controller = loader.getController();
                controller.initData(order);
            } else if (controllerClass.equals(EditFormController.class)) {
                EditFormController controller = loader.getController();
                controller.initData(order);
            } else if (controllerClass.equals(WarningFormController.class)) {
                WarningFormController controller = loader.getController();
                controller.initData(order);
            }

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Došlo k chybě při načítání formuláře " + formName + "!");
            System.err.println(e.getMessage());
        }
    }

    protected final void closeApplication() {
        ENTITY_MANAGER_FACTORY.close();
        Platform.exit();
        System.exit(0);
    }

}
