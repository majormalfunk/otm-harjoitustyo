/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package layout;

import mizzilekommand.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import mizzilekommand.SceneController.Actions;
import static mizzilekommand.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.MizzileKommand.APP_WIDTH;

/**
 *
 * @author jaakkovilenius
 */
public class EndScene extends SceneTemplate {
    
    Button btnEnd;

    public EndScene(SceneController controller, GamePane gamepane) {

        super(controller);
        
        this.root.getChildren().add(gamepane);
        
        btnEnd = new Button();
        btnEnd.setText("THE END");
        btnEnd.setMinSize(100, 50);
        btnEnd.setPrefSize(100, 50);
        btnEnd.setMaxSize(100, 50);
        btnEnd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("THE END");
                getController().chooseNextScene(Actions.THEEND);
                getController().applyNextScene();
            }
        });
        btnEnd.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        btnEnd.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
            }
        });

        this.root.getChildren().add(btnEnd);
        btnEnd.setLayoutX((APP_WIDTH / 2) - 50);
        btnEnd.setLayoutY((APP_HEIGHT / 2) - 25);
    }
    
}
