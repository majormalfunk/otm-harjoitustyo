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
public class GamePlayScene extends SceneTemplate {
    
    public GamePlayScene(SceneController controller, GamePane gamepane) {
    //public GamePlayScene(Parent root) {
        //super(root);
        super(controller);

        this.root.getChildren().add(gamepane);

        
    }
    
    
}
