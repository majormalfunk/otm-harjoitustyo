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
 * This is the Starting scene for the game.
 * @author jaakkovilenius
 */
public class StartScene extends SceneTemplate {

    Button btnPlay;

    public StartScene(SceneController controller, GamePane gamepane) {

        super(controller);
        
        this.root.getChildren().add(gamepane);
        
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

        this.root.getChildren().add(btnPlay);
        btnPlay.setLayoutX((APP_WIDTH / 2) - 50);
        btnPlay.setLayoutY((APP_HEIGHT / 2) - 25);

    }

}
