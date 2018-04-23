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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
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

        hideLevelIndicator();

        btnPlay = new Button();
        btnPlay.setText("PLAY!");
        btnPlay.setMinSize(100, 50);
        btnPlay.setPrefSize(100, 50);
        btnPlay.setMaxSize(100, 50);
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
        btnPlay.setLayoutY((APP_HEIGHT / 2) - 50);

        Label keys = new Label();
        keys.setTextFill(Color.WHITESMOKE);
        keys.setTextAlignment(TextAlignment.CENTER);
        String guide = "POINT WITH MOUSE. FIRE MISSILE FROM BASE:\n"
                + "Z/\u21E6 Left Base   X/\u21E7/\u21E9 Center Base    C/\u21E8 Right Base";
        keys.setText(guide);
        this.root.getChildren().add(keys);
        keys.setMinWidth(200);
        keys.setLayoutY((APP_HEIGHT / 2) + 50);
        keys.layoutXProperty().bind(this.widthProperty().subtract(keys.widthProperty()).divide(2));

    }

}
