/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import mizzilekommand.nodes.Ground;
import javafx.scene.layout.Pane;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import static mizzilekommand.logics.MizzileKommand.SMALL_LENGTH;

/**
 * This Pane provides the background for the game play scene
 *
 * @author jaakkovilenius
 */
public class GamePane extends Pane {

    private Ground ground;

    public static final String BACKGROUND_STYLE
            = "-fx-background-color: linear-gradient(#000030 65%, #ff3000 87%, #000000 90%); "
            + "-fx-background-radius: 0; "
            + "-fx-background-insets: 0; ";

    /**
     * Constructor for GamePane. Creates a GamePane object that extends the Pane
     * object. The size of the playing field which is supposed to be the same
     * size as the screen window. The bases, cities and the ground are sized and
     * placed in proportion to the window size.
     */
    public GamePane() {

        // Total
        
        this.setStyle(BACKGROUND_STYLE);
        
        this.setPrefSize(APP_WIDTH, APP_HEIGHT);

        // Ground
        ground = new Ground();
        ground.setLayoutX(0.0);
        ground.setLayoutY(APP_HEIGHT - (SMALL_LENGTH * 4.0));
        this.getChildren().add(ground);

    }

}
