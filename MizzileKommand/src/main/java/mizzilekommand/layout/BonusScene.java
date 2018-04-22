/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import mizzilekommand.logics.SceneController;

/**
 * Not fully implemented yet
 *
 * @author jaakkovilenius
 */
public class BonusScene extends SceneTemplate {

    public BonusScene(SceneController controller, int level) {
        super(controller);

        showLevelIndicator(level);
        
        Button btnContinue;

        btnContinue = new Button();
        btnContinue.setText("CONTINUE");
        btnContinue.setMinSize(100, 50);
        btnContinue.setPrefSize(100, 50);
        btnContinue.setMaxSize(100, 50);
        btnContinue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getController().chooseNextScene(SceneController.Actions.CONTINUE);
                getController().applyNextScene();
            }
        });
        btnContinue.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        btnContinue.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
            }
        });

        this.root.getChildren().add(btnContinue);
        btnContinue.setLayoutX((APP_WIDTH / 2) - 50);
        btnContinue.setLayoutY((APP_HEIGHT / 2) - 25);

    }

}
