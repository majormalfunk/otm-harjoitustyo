/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import mizzilekommand.layout.BonusScene;
import mizzilekommand.layout.EndScene;
import mizzilekommand.layout.GamePlayScene;
import mizzilekommand.layout.SceneTemplate;
import mizzilekommand.layout.StartScene;
import mizzilekommand.layout.TopScoreScene;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import static mizzilekommand.logics.MizzileKommand.BASE_RADIUS;
import static mizzilekommand.logics.MizzileKommand.BASE_X;
import static mizzilekommand.logics.MizzileKommand.BASE_Y;
import mizzilekommand.nodes.Base;
import mizzilekommand.nodes.City;
import mizzilekommand.nodes.EnemyMissile;
import mizzilekommand.nodes.Explosion;
import mizzilekommand.nodes.PlayerMissile;

/**
 * This class handles changing the correct view. It is sort of a middle man
 * between the UI classes and the application logic.
 * 
 * @author jaakkovilenius
 */
public class SceneController {

    private Stage stage;
    private GameLoop gameloop;
    public ActionSelector actionSelector;
    public SceneTemplate currentScene;

    public enum Scenes {
        START, PLAY, BONUS, TOP, END
    };
    public Scenes nextScene;

    public enum Actions {
        PLAY, CONTINUE, NOINCOMING, NOCITIES, ENOUGHDESTROYED, THEEND, TOPSCORE, SCORESAVED //, QUIT
    }

    /**
     * Constructs a new SceneController.
     * 
     * It also creates an instance of the GameLoop and the ActionSelector
     *
     * @param stage
     */
    public SceneController(Stage stage) {

        this.stage = stage;
        //this.gameloop = new GameLoop();
        //this.gameloop.setSceneController(this);
        this.actionSelector = new ActionSelector();

    }
    
    public void setGameLoop(GameLoop gameloop) {
        this.gameloop = gameloop;
    }

    /**
     * This method handles the game control input from the player. It receives
     * the pressed key and the X and Y coordinates of the mouse pointer at the
     * time the user pressed the key.
     *
     * @param key
     * @param targetX
     * @param targetY
     */
    public void keyDown(KeyCode key, double targetX, double targetY) {
        switch (key) {
            case LEFT:
            case DIGIT1:
                if (gameloop.gameStatus.baseMissilesLeft(0)) {
                    PlayerMissile missile = launchNewPlayerMissile(0, targetX, targetY);
                    gameloop.handleNewPlayerMissile(missile, 0);
                }
                break;
            case DOWN:
            case UP:
            case DIGIT2:
                if (gameloop.gameStatus.baseMissilesLeft(1)) {
                    PlayerMissile missile = launchNewPlayerMissile(0, targetX, targetY);
                    gameloop.handleNewPlayerMissile(missile, 1);
                }
                break;
            case RIGHT:
            case DIGIT3:
                if (gameloop.gameStatus.baseMissilesLeft(2)) {
                    PlayerMissile missile = launchNewPlayerMissile(0, targetX, targetY);
                    gameloop.handleNewPlayerMissile(missile, 2);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Convenience method for starting the game application
     */
    public void applyFirstScene() {
        applyStartScene();

    }

    /**
     * This method chooses the next scene using the ActionSelector. Before that
     * it calls the levelUp method of the GameLoop
     * @param action 
     * 
     * @see mizzilekommand.logics.GameLoop#levelUp() 
     */
    public void chooseNextScene(Actions action) {
        if (action == Actions.CONTINUE) {
            gameloop.levelUp();
        }
        nextScene = actionSelector.chooseNextScene(action);
    }

    /**
     * This calls the method that applies the next scene set in chooseNextScene
     * 
     * @see mizzilekommand.logics.SceneController#chooseNextScene(mizzilekommand.logics.SceneController.Actions) 
     * 
     */
    public void applyNextScene() {
        if (stage != null) {
            switch (nextScene) {
                case START:
                    applyStartScene();
                    break;
                case PLAY:
                    applyGamePlayScene();
                    break;
                case BONUS:
                    applyBonusScene();
                    break;
                case TOP:
                    applyTopScoreScene();
                    break;
                case END:
                    applyEndScene();
                    break;
            }
        }
    }

    /**
     * This applies the next scene
     */
    public void applyScene() {
        stage.setScene(currentScene);
        stage.show();
    }

    /**
     * This method applies the StartScene. The method is a collection operations
     * to be run in order for the scene to function properly.
     */
    private void applyStartScene() {
        gameloop.stopLoop();
        gameloop.resetGameStatus();
        currentScene = new StartScene(this, gameloop.gameStatus);
        applyScene();
    }

    /**
     * This method applies the GamePlayScene. The method is a collection
     * operations to be run in order for the scene to function properly.
     */
    public void applyGamePlayScene() {
        currentScene = new GamePlayScene(this, gameloop.gameStatus);
        if (gameloop.startLoop()) {
            applyScene();
        }
    }

    /**
     * This method applies the BonusScene. The method is a collection of
     * operations to be run in order for the scene to function properly.
     */
    public void applyBonusScene() {
        gameloop.stopLoop();
        currentScene = new BonusScene(this, gameloop.gameStatus);
        currentScene.runActions();
        applyScene();
    }

    /**
     * This method applies the TopScoreScene. The method is a collection operations
     * to be run in order for the scene to function properly.
     */
    public void applyTopScoreScene() {
        gameloop.stopLoop();
        currentScene = new TopScoreScene(this, gameloop.gameStatus);
        applyScene();
    }

    /**
     * This method applies the EndScene. The method is a collection operations
     * to be run in order for the scene to function properly.
     */
    public void applyEndScene() {
        gameloop.stopLoop();
        currentScene = new EndScene(this, gameloop.gameStatus);
        applyScene();
    }

    /**
     * This method asks the currentScene to add the bases with ok status to
     * the current scene
     * @param baseOk a boolean array indicating by the index which base is ok
     * @return the base nodes with ok status as a list
     */
    public List<Base> addBases(boolean[] baseOk) {
        return currentScene.addBases(baseOk);
    }
    
    /**
     * This method asks the currentScene to add the cities with ok status to
     * the current scene
     * @param cityOk a boolean array indicating by the index which city is ok
     * @return the city nodes with ok status as a list
     */
    public List<City> addCities(boolean[] cityOk) {
        return currentScene.addCities(cityOk);
    }
    
    /**
     * This method adds to the current scene the node given as parameter. This
     * is because we want to isolate access to the scene from the game logic
     *
     * @param node The node to be added
     */
    public void addToCurrentScene(Node node) {
        try {
            currentScene.getSceneRoot().getChildren().add(node);
        } catch (Exception e) {
            // Do nothing
        }
    }

    /**
     * This method adds to the current scene the list of nodes given as
     * parameter. This is because we want to isolate access to the scene from
     * the game logic
     *
     * @param nodes to be added
     */
    public void addAllToCurrentScene(List nodes) {
        try {
            currentScene.getSceneRoot().getChildren().addAll(nodes);
        } catch (Exception e) {
            // Do nothing
        }
    }

    /**
     * This method removes from the current scene the node given as parameter.
     * This is because we want to isolate access to the scene from the game
     * logic
     *
     * @param node The node to be removed
     */
    public void removeFromCurrentScene(Node node) {
        currentScene.getSceneRoot().getChildren().remove(node);
    }

    /**
     * This method removes from the current scene the list of nodes given as
     * parameter. This is because we want to isolate access to the scene from
     * the game logic
     *
     * @param nodes to be removed
     */
    public void removeAllFromCurrentScene(List nodes) {
        currentScene.getSceneRoot().getChildren().removeAll(nodes);
    }

    /**
     * Convenience method that calls applyNextScene(String) with parameter
     * NOCITIES. Used to transition from GamePlayScene to next scene in the
     * event of all player cities been destroyed
     */
    public void noCitiesLeft(boolean topScore) {
        if (topScore) {
            this.chooseNextScene(Actions.TOPSCORE);
        } else {
            this.chooseNextScene(Actions.NOCITIES);
        }
        this.applyNextScene();
    }

    /**
     * Convenience method that calls applyNextScene(String) with parameter
     * ENOUGHDESTROYED. Used to transition from GamePlayScene to next scene in
     * the event of enough of player cities have been destroyed
     */
    public void enoughCitiesDestroyed() {
        this.chooseNextScene(Actions.ENOUGHDESTROYED);
        this.applyNextScene();
    }

    /**
     * This method adds a new enemy missile to the scene.
     * 
     * @param speedFactor
     * @return a reference to the new missile
     * 
     * @see missilekommand.nodes.EnemyMissile
     */
    public EnemyMissile addIncoming(double speedFactor) {
        double sourceX = 0.05 * APP_WIDTH + Math.random() * (APP_WIDTH * 0.90);
        double targetX = 0.05 * APP_WIDTH + Math.random() * (APP_WIDTH * 0.90);
        EnemyMissile missile = new EnemyMissile(System.currentTimeMillis(),
                speedFactor, sourceX, targetX, APP_HEIGHT * 0.8);
        missile.setLayoutX(sourceX);
        missile.setLayoutY(0);
        missile.setDirection();
        addToCurrentScene(missile);
        return missile;
    }

    
    /**
     * Convenience method that calls applysNextScene(String) with parameter
     * NOINCOMING. Used to transition from GamePlayScene to next scene in the
     * event of no more incoming missiles in level
     */
    public void noIncomingLeft() {
        this.chooseNextScene(Actions.NOINCOMING);
        this.applyNextScene();
    }
    
    /**
     * This method adds a new player missile to the scene.
     * 
     * @param base id of base from which the missile is launched
     * @param targetX x-coordinate of missile target
     * @param targetY y-coordinate if missile target
     * @return a reference to the new missile
     */
    public PlayerMissile launchNewPlayerMissile(int base, double targetX, double targetY) {
        PlayerMissile missile = new PlayerMissile(System.currentTimeMillis(),
                (base == 1 ? 3.0 : 2.0), targetX, targetY);
        missile.setLayoutX(BASE_X[base] - (missile.width / 2.0));
        missile.setLayoutY(BASE_Y - BASE_RADIUS - missile.height);
        missile.setDirection();
        addToCurrentScene(missile);
        return missile;
    }

        /**
     * This method checks to see if an enemy missile was destroyed by an
     * explosion
     *
     * @param explosion
     * @param missile
     * @return true if the missile was destroyed by the explosion false
     * otherwise
     */
    public boolean didDestroyEnemyMissile(Explosion explosion, EnemyMissile missile) {
        Shape impactZone = Shape.intersect(explosion, missile);
        return impactZone.getBoundsInLocal().getWidth() != -1;
    }

    /**
     * This method checks if a base was destroyed by an explosion
     *
     * @param explosion possibly destructive explosion
     * @param base possibly destructing base
     * @return true if the base was destroyed by the explosion false otherwise.
     */
    public boolean didDestroyBase(Explosion explosion, Base base) {
        Shape impactZone = Shape.intersect(explosion, base);
        return impactZone.getBoundsInLocal().getWidth() != -1;
    }

    /**
     * This method checks if a city was destroyed by an explosion
     *
     * @param explosion possibly destructive explosion
     * @param city possibly destructing city
     * @return true if the city was destroyed by the explosion false otherwise.
     */
    public boolean didDestroyCity(Explosion explosion, City city) {
        Shape impactZone = Shape.intersect(explosion, city);
        return impactZone.getBoundsInLocal().getWidth() != -1;
    }


}
