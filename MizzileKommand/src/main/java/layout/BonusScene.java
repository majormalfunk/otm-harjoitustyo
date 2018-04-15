/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package layout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import static mizzilekommand.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.MizzileKommand.APP_WIDTH;
import mizzilekommand.SceneController;

/**
 * Not implemented yet
 * @author jaakkovilenius
 */
public class BonusScene extends SceneTemplate {
    
    public BonusScene(SceneController controller, GamePane gamepane) {
        super(controller);

        Button btnContinue;
    
        this.root.getChildren().add(gamepane);
        
        btnContinue = new Button();
        btnContinue.setText("CONTINUE");
        btnContinue.setMinSize(100, 50);
        btnContinue.setPrefSize(100, 50);
        btnContinue.setMaxSize(100, 50);
        btnContinue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("CONTINUE!");
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
        btnContinue.setLayoutX((APP_WIDTH/2)-50);
        btnContinue.setLayoutY((APP_HEIGHT/2)-25);
        


    }
    
}
