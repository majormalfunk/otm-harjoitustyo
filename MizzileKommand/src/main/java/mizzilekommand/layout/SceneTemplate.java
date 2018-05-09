/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mizzilekommand.logics.SceneController;
import mizzilekommand.logics.GameStatus;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import static mizzilekommand.logics.MizzileKommand.BASE_X;
import static mizzilekommand.logics.MizzileKommand.BASE_Y;
import static mizzilekommand.logics.MizzileKommand.CITY_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.CITY_X;
import static mizzilekommand.logics.MizzileKommand.INCOMING;
import static mizzilekommand.logics.MizzileKommand.LEVEL;
import static mizzilekommand.logics.MizzileKommand.SCORE;
import mizzilekommand.nodes.Base;
import mizzilekommand.nodes.City;

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
    protected GameStatus status;
    Text scoreCounter;
    Text levelIndicator;
    Text incomingCounter;
    public Text[] baseMissileCounter;

    /**
     * The constructor
     *
     * @param controller
     */
    public SceneTemplate(SceneController controller, GameStatus status) {

        super(new Group(), APP_WIDTH, APP_HEIGHT);
        this.controller = controller;
        this.root = (Group) this.getRoot();
        this.root.getChildren().add(new GamePane());
        this.status = status;

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

        // Base missile counter
        baseMissileCounter = new Text[]{new Text(), new Text(), new Text()};
        baseMissileCounter[0].setStroke(Color.SILVER);
        baseMissileCounter[1].setStroke(Color.SILVER);
        baseMissileCounter[2].setStroke(Color.SILVER);

    }

    /**
     * This is a convenience method to get a reference to the SceneController.
     *
     * @return SceneController a reference to the current SceneController
     * instance
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

    /**
     * This shows the score counter
     *
     * @param count current score to shoe
     */
    public void showScoreCounter(int count) {
        scoreCounter.setText(SCORE + " " + count);
        scoreCounter.setLayoutX(20.0);
        scoreCounter.setLayoutY(20.0);
        try {
            this.root.getChildren().add(scoreCounter);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to show score counter");
        }
    }

    /**
     * This hides the score counter
     *
     */
    public void hideScoreCounter() {
        try {
            this.root.getChildren().remove(scoreCounter);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to hide score counter");
        }
    }
    
    public void updateScoreCounter(int count) {
        scoreCounter.setText(SCORE + " " + count);
    }

    /**
     * This shows the level indicator
     *
     * @param level current level to show
     */
    public void showLevelIndicator(int level) {
        levelIndicator.setText(LEVEL + " " + level);
        levelIndicator.setLayoutX((APP_WIDTH / 2.0) - (levelIndicator.getLayoutBounds().getWidth() / 2.0));
        levelIndicator.setLayoutY(20);
        try {
            this.root.getChildren().add(levelIndicator);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to show level indicator");
        }
    }

    /**
     * This hides the level indicator
     *
     */
    public void hideLevelIndicator() {
        try {
            this.root.getChildren().remove(levelIndicator);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to hide level indicator");
        }
    }

    /**
     * This shows the counter of incoming missiles left in the scene
     *
     * @param count The count of incoming missiles left in the current level
     */
    public void showIncomingCounter(int count) {
        incomingCounter.setText(INCOMING + " " + count);
        incomingCounter.setLayoutX(APP_WIDTH - incomingCounter.getLayoutBounds().getWidth() - 20.0);
        incomingCounter.setLayoutY(20);
        try {
            this.root.getChildren().add(incomingCounter);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to show incoming counter");
        }
    }

    /**
     * This hides the incoming counter
     *
     */
    public void hideIncomingCounter() {
        try {
            this.root.getChildren().remove(incomingCounter);
        } catch (Exception e) {
            // Do nothing
            System.out.println("Exception trying to hide incoming counter");
        }
    }

    /**
     * This shows the counters that tell how many missiles are left at the
     * player bases
     *
     * @param count
     */
    public void showBaseMissileCounters(int[] count) {
        for (int i = 0; i < baseMissileCounter.length; i++) {
            baseMissileCounter[i].setText("" + count[i]);
            baseMissileCounter[i].setLayoutX(BASE_X[i] - (baseMissileCounter[i].getLayoutBounds().getWidth() / 2.0));
            baseMissileCounter[i].setLayoutY(APP_HEIGHT - 40.0);
            try {
                this.root.getChildren().add(baseMissileCounter[i]);
            } catch (Exception e) {
                // Do nothing
                System.out.println("Exception trying to show base " + i + " missile counter");
            }
        }
    }

    /**
     * This hides the counters that tell how many missiles are left at the
     * player bases.
     *
     */
    public void hideBaseMissileCounters() {
        for (int i = 0; i < baseMissileCounter.length; i++) {
            try {
                this.root.getChildren().remove(baseMissileCounter[i]);
            } catch (Exception e) {
                // Do nothing
                System.out.println("Exception trying to hide base " + i + " counter");
            }
        }
    }
    
    /**
     * This method should be implemented in the extended class if needed
     */
    public void runActions() {
        // Implement this in exteded class;
    }

    /**
     * This method adds the bases with ok status to the current Scene and returns
     * a reference to them in a list.
     *
     * @param baseOk a boolean list of bases indicating if base is destructed (false)
     * or not (true)
     */
    public List<Base> addBases(boolean[] baseOk) {
        List<Base> bases = new ArrayList<>();
        for (int id = 0; id < baseOk.length; id++) {
            if (baseOk[id]) {
                Base base = new Base();
                base.setLayoutX(BASE_X[id]);
                base.setLayoutY(BASE_Y);
                base.id = id;
                bases.add(base);
                this.root.getChildren().add(base);
            }
        }
        return bases;
    }

    /**
     * This method adds the cities with ok status to the current Scene and returns
     * a reference to them in a list.
     * 
     * @param cityOk a boolean list of cities indicating if city is destructed (false)
     * or not (true)
     */
    public List<City> addCities(boolean[] cityOk) {
        List<City> cities = new ArrayList<>();
        for (int id = 0; id < cityOk.length; id++) {
            if (cityOk[id]) {
                City city = new City(id);
                city.setLayoutX(CITY_X[id] - (city.width / 2.0));
                city.setLayoutY(APP_HEIGHT - (CITY_HEIGHT * 2.0));
                city.id = id;
                cities.add(city);
                this.root.getChildren().add(city);
            }
        }
        return cities;
    }

}
