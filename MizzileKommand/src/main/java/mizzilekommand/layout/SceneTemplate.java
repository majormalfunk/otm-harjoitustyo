/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import mizzilekommand.logics.SceneController;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;

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
    Text scoreCounter;
    Text levelIndicator;
    Text incomingCounter;

    public SceneTemplate(SceneController controller) {

        super(new Group(), APP_WIDTH, APP_HEIGHT);
        this.controller = controller;
        this.root = (Group) this.getRoot();
        this.root.getChildren().add(new GamePane());

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

        // Score counter
        scoreCounter = new Text();
        scoreCounter.setStroke(Color.WHITE);

        // Level indicator
        levelIndicator = new Text();
        levelIndicator.setStroke(Color.WHITE);

        // Incoming counter
        incomingCounter = new Text();
        incomingCounter.setStroke(Color.WHITE);

    }

    /**
     * This is a convenience method to get a reference to the SceneController.
     *
     * @return SceneController a reference to the current SceneController instance
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

    public void showScoreCounter(int count) {
        updateScoreCounter(count);
        scoreCounter.setLayoutX(20.0);
        scoreCounter.setLayoutY(20.0);
        try {
            this.root.getChildren().add(scoreCounter);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to show score counter");
        }
    }
    
    public void updateScoreCounter(int count) {
        scoreCounter.setText("SCORE: " + count);
    }

    public void hideScoreCounter() {
        try {
            this.root.getChildren().remove(scoreCounter);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to hide score counter");
        }
    }

    public void showLevelIndicator(int level) {
        levelIndicator.setText("LEVEL: " + level);
        levelIndicator.setLayoutX((APP_WIDTH / 2.0) - (levelIndicator.getLayoutBounds().getWidth() / 2.0));
        levelIndicator.setLayoutY(20);
        try {
            this.root.getChildren().add(levelIndicator);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to show level indicator");
        }
    }

    public void hideLevelIndicator() {
        try {
            this.root.getChildren().remove(levelIndicator);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to hide level indicator");
        }
    }

    public void showIncomingCounter(int count) {
        updateIncomingCounter(count);
        incomingCounter.setLayoutX(APP_WIDTH - incomingCounter.getLayoutBounds().getWidth() - 20.0);
        incomingCounter.setLayoutY(20);
        try {
            this.root.getChildren().add(incomingCounter);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to show incoming counter");
        }
    }
    
    public void updateIncomingCounter(int count) {
        incomingCounter.setText("INCOMING: " + count);
    }

    public void hideIncomingCounter() {
        try {
            this.root.getChildren().remove(incomingCounter);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to hide incoming counter");
        }
    }
}
