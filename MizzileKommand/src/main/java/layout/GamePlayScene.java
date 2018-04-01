/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package layout;

import mizzilekommand.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import mizzilekommand.SceneController.Actions;
import static mizzilekommand.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.MizzileKommand.APP_WIDTH;

/**
 *
 * @author jaakkovilenius
 */
public class GamePlayScene extends SceneTemplate {
    
    Button btnQuit;
    
    public GamePlayScene(SceneController controller, GamePane gamepane) {
    //public GamePlayScene(Parent root) {
        //super(root);
        super(controller);
        
        this.root.getChildren().add(gamepane);
        
        btnQuit = new Button();
        btnQuit.setText("QUIT");
        btnQuit.setMinSize(100, 50);
        btnQuit.setPrefSize(100, 50);
        btnQuit.setMaxSize(100, 50);
        btnQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("QUIT!");
                getController().chooseNextScene(Actions.QUIT);
                getController().applyNextScene();
            }
        });

        this.root.getChildren().add(btnQuit);
        btnQuit.setLayoutX((APP_WIDTH/2)-50);
        btnQuit.setLayoutY((APP_HEIGHT/2)-25);
        
    }
    
    
}
