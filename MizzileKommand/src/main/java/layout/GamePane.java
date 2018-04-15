/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package layout;

import nodes.Ground;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static mizzilekommand.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.MizzileKommand.APP_WIDTH;
import static mizzilekommand.MizzileKommand.SMALL_LENGTH;

/**
 * This Pane provides the background for the game play scene
 *
 * @author jaakkovilenius
 */
public class GamePane extends Pane {

    Ground ground;

    /**
     * Constructor for GamePane. Creates a GamePane object that extends the Pane
     * object. The size of the playing field which is supposed to be the same
     * size as the screen window. The bases, cities and the ground are sized and
     * placed in proportion to the window size.
     */
    public GamePane() {

        // Total
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPrefSize(APP_WIDTH, APP_HEIGHT);

        // Ground
        ground = new Ground();
        ground.setLayoutX(0.0);
        ground.setLayoutY(APP_HEIGHT - (SMALL_LENGTH * 4.0));
        this.getChildren().add(ground);

    }

}
