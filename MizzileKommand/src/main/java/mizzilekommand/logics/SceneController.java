/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

import javafx.scene.Node;
import javafx.stage.Stage;
import mizzilekommand.layout.BonusScene;
import mizzilekommand.layout.EndScene;
import mizzilekommand.layout.GamePlayScene;
import mizzilekommand.layout.SceneTemplate;
import mizzilekommand.layout.StartScene;
import mizzilekommand.layout.TopScoreScene;

/**
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
        PLAY, CONTINUE, NOINCOMING, NOCITIES, ENOUGHDESTROYED, THEEND //, QUIT
    }

    /**
     * Mizzile Kömmänd: Constructs a new SceneController
     *
     * @param stage
     * @param gameloop
     */
    public SceneController(Stage stage) {

        this.stage = stage;
        this.gameloop = new GameLoop();
        this.gameloop.setSceneController(this);
        this.actionSelector = new ActionSelector();

    }

    /**
     * Convenience method for starting the game application
     */
    public void applyFirstScene() {
        applyStartScene();

    }

    public void chooseNextScene(Actions action) {
        nextScene = actionSelector.chooseNextScene(action);
    }

    public void applyNextScene() {
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
                break;
            case END:
                applyEndScene();
                break;
        }
    }

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
        currentScene = new StartScene(this);
        gameloop.resetGameStatus();
        applyScene();
    }

    /**
     * This method applies the GamePlayScene. The method is a collection
     * operations to be run in order for the scene to function properly.
     */
    public void applyGamePlayScene() {
        currentScene = new GamePlayScene(this);
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
        gameloop.levelUp();
        currentScene = new BonusScene(this);
        applyScene();
    }

    /**
     * This method applies the EndScene. The method is a collection operations
     * to be run in order for the scene to function properly.
     */
    public void applyEndScene() {
        gameloop.stopLoop();
        currentScene = new EndScene(this);
        applyScene();
    }

    public void addToCurrentScene(Node node) {
        currentScene.getSceneRoot().getChildren().add(node);
    }
    
    public void removeFromCurrentScene(Node node) {
        currentScene.getSceneRoot().getChildren().remove(node);
    }
    
    /**
     * Convenience method that calls applyNextScene(String) with parameter
     * NOCITIES. Used to transition from GamePlayScene to next scene in the
     * event of all player cities been destroyed
     */
    public void noCitiesLeft() {
        this.chooseNextScene(Actions.NOCITIES);
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
     * Convenience method that calls applysNextScene(String) with parameter
     * NOINCOMING. Used to transition from GamePlayScene to next scene in the
     * event of no more incoming missiles in level
     */
    public void noIncomingLeft() {
        this.chooseNextScene(Actions.NOINCOMING);
        this.applyNextScene();
    }
}
