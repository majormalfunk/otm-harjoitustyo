/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import mizzilekommand.logics.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import mizzilekommand.logics.SceneController.Actions;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;

/**
 * This is the Starting scene for the game.
 *
 * @author jaakkovilenius
 */
public class StartScene extends SceneTemplate {

    Button btnPlay;

    public StartScene(SceneController controller) {

        super(controller);

        btnPlay = new Button();
        btnPlay.setText("PLAY!");
        btnPlay.setMinSize(100, 50);
        btnPlay.setPrefSize(100, 50);
        btnPlay.setMaxSize(100, 50);
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("PLAY!");
                getController().chooseNextScene(Actions.PLAY);
                getController().applyNextScene();
            }
        });
        btnPlay.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        btnPlay.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
            }
        });

        this.root.getChildren().add(btnPlay);
        btnPlay.setLayoutX((APP_WIDTH / 2) - 50);
        btnPlay.setLayoutY((APP_HEIGHT / 2) - 25);

    }

}
