/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package layout;

import mizzilekommand.SceneController;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import static mizzilekommand.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.MizzileKommand.APP_WIDTH;

/**
 * This is an abstract class designed to be inherited by the actual scene
 * classes. It provides the common functionality and properties for the actual
 * scene classes.
 *
 * @author jaakkovilenius
 */
public abstract class SceneTemplate extends Scene {

    SceneController controller;
    Group root;

    HashMap<KeyCode, Boolean> painetutNapit = new HashMap<>();

    public SceneTemplate(SceneController controller) {
        
        super(new Group(), APP_WIDTH, APP_HEIGHT);
        this.root = (Group) this.getRoot();
        this.controller = controller;

        this.setOnKeyPressed(event -> {
            nappiPainettu(event.getCode());
        });
        this.setOnKeyReleased(event -> {
            nappiPaastetty(event.getCode());
        });

        this.setOnMouseDragged((event) -> {
            double kohtaX = event.getX();
            double kohtaY = event.getY();
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });


    }

    /**
     * This is a placeholder method waiting for future use in game play.
     *
     * @param nappi
     */
    private void nappiPainettu(KeyCode nappi) {
        switch (nappi) {
            case LEFT:
            case RIGHT:
            case UP:
            case DOWN:
            case SPACE:
                painetutNapit.put(nappi, Boolean.TRUE);
                break;
        }
    }

    /**
     * This is a placeholder method waiting for future use in game play.
     *
     * @param nappi
     */
    private void nappiPaastetty(KeyCode nappi) {
        switch (nappi) {
            case LEFT:
            case RIGHT:
            case UP:
            case DOWN:
            case SPACE:
                painetutNapit.put(nappi, Boolean.FALSE);
                break;
        }
    }

    /**
     * This is a convenience method to get a reference to the SceneController.
     *
     * @return SceneController a refernce to the curren SceneController instance
     */
    public SceneController getController() {
        return controller;
    }

    /**
     * This is a convenience method to get a reference to the Scene root.
     *
     * @return Group a reference to the current scene root group.
     */
    public Group getSceneRoot() {
        return root;
    }
}
