/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import javafx.animation.AnimationTimer;
import mizzilekommand.logics.SceneController;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import mizzilekommand.logics.GameStatus;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import mizzilekommand.logics.SceneController.Actions;

/**
 * The End -scene shown when the game ends without a new high score
 * 
 * @author jaakkovilenius
 */
public class EndScene extends SceneTemplate {

    public EndScene(SceneController controller, GameStatus status) {

        super(controller, status);

        addBases(status.baseOk);
        addCities(status.cityOk);

        addTitle();
        
        showScoreCounter(status.score);
        showLevelIndicator(status.level);

        runTransitCounter();
        
    }
    
    private void runTransitCounter() {
        try {

            new AnimationTimer() {
                int bonusCounter = 0;
                long transit = System.currentTimeMillis() + 5000l;

                @Override
                public void handle(long now) {
                    if (transit < System.currentTimeMillis()) {
                        getController().chooseNextScene(Actions.THEEND);
                        getController().applyNextScene();
                        stop();
                    }
                }

            }.start();

        } catch (Exception ex) {
            // Do nothing
        }

    }
    
    private void addTitle() {
        Label title = new Label("THE END");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40.0));
        title.setTextFill(Color.SILVER);
        title.setTextAlignment(TextAlignment.CENTER);
        this.root.getChildren().add(title);
        title.layoutXProperty().bind(this.widthProperty().subtract(title.widthProperty()).divide(2));
        title.layoutYProperty().bind(this.heightProperty().subtract(title.heightProperty()).divide(2));
    }

        
}
