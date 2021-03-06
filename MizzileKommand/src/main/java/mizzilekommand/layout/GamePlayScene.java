/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import mizzilekommand.logics.GameStatus;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import mizzilekommand.logics.SceneController;

/**
 * The actual game play scene where all the action happens
 * 
 * @author jaakkovilenius
 */
public class GamePlayScene extends SceneTemplate {

    double targetX;
    double targetY;

    public GamePlayScene(SceneController controller, GameStatus status) {

        super(controller, status);

        showScoreCounter(status.score);
        showLevelIndicator(status.level);
        showIncomingCounter(status.incomingLeft);
        showBaseMissileCounters(status.missilesLeft);
        status.scoreCounter = this.scoreCounter;
        status.levelIndicator = this.levelIndicator;
        status.incomingCounter = this.incomingCounter;
        status.baseMissileCounter[0] = this.baseMissileCounter[0];
        status.baseMissileCounter[1] = this.baseMissileCounter[1];
        status.baseMissileCounter[2] = this.baseMissileCounter[2];

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
