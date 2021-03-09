package cz.osu.carservice.controllers;

import cz.osu.carservice.controllers.mainController.MainController;
import cz.osu.carservice.models.entities.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javax.persistence.EntityManager;

public class WarningFormController extends MainController{
    @FXML
    private Label titleLBL;

    private Order order;
    private EntityManager entityManager;

    public void initData(Order order) {
       this.order = order;

        titleLBL.setText("Opravdu si přejete smazat vybranou objednávku č." +order.getId()+" ?");
    }
    @FXML
    private void returnToOrderListForm(MouseEvent event){
        super.setNewFormScene("orderListForm",event);
    }
    @FXML
    private void deleteOrder(MouseEvent event){
        entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(order));
            entityManager.getTransaction().commit();
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }finally {
            entityManager.close();
            super.setNewFormScene("orderListForm",event);
        }
    }

}
