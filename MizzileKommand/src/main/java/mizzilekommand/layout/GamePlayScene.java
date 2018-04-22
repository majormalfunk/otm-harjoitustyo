/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import mizzilekommand.logics.SceneController;

/**
 *
 * @author jaakkovilenius
 */
public class GamePlayScene extends SceneTemplate {

    double targetX;
    double targetY;
    
    public GamePlayScene(SceneController controller, int level) {
        //public GamePlayScene(Parent root) {
        //super(root);
        super(controller);

        showLevelIndicator(level);
        
        targetX = APP_WIDTH / 2.0;
        targetY = APP_HEIGHT / 2.0;
        this.setOnKeyPressed(event -> {
            controller.keyDown(event.getCode(), targetX, targetY);
        });

        this.setOnMouseMoved(event -> {
                targetX = event.getX();
                targetY = event.getY();
        });

        
    }

}
