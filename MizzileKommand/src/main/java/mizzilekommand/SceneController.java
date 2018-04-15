/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand;

import javafx.stage.Stage;
import layout.BonusScene;
import layout.EndScene;
import layout.GamePane;
import layout.GamePlayScene;
import layout.StartScene;
import layout.TopScoreScene;

/**
 *
 * @author jaakkovilenius
 */
public class SceneController {

    private Stage stage;
    private GameLoop gameloop;
    public GamePane gamepane;
    public ActionSelector actionSelector;

    public StartScene startScn;
    public GamePlayScene gamePlayScn;
    public BonusScene bonusScn;
    public TopScoreScene topScoreScn;
    public EndScene endScn;

    public enum Scenes {
        START, PLAY, BONUS, TOP, END
    };
    public Scenes nextScene;

    public enum Actions {
        PLAY, CONTINUE, NOINCOMING, NOBASES, NOCITIES, ENOUGHDESTROYED, THEEND //, QUIT
    }

    /**
     * Mizzile Kömmänd: Constructs a new SceneController
     *
     * @param stage
     * @param gameloop
     */
    public SceneController(Stage stage, GameLoop gameloop) {

        this.stage = stage;
        this.gameloop = gameloop;
        this.gamepane = new GamePane();
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
                gamePlayScn = null;
                bonusScn = null;
                topScoreScn = null;
                endScn = null;
                applyStartScene();
                break;
            case PLAY:
                startScn = null;
                bonusScn = null;
                topScoreScn = null;
                endScn = null;
                applyGamePlayScene();
                break;
            case BONUS:
                startScn = null;
                gamePlayScn = null;
                topScoreScn = null;
                endScn = null;
                applyBonusScene();
                break;
            case TOP:
                startScn = null;
                gamePlayScn = null;
                bonusScn = null;
                endScn = null;
                //applyTopScoreScene();
                break;
            case END:
                startScn = null;
                gamePlayScn = null;
                bonusScn = null;
                topScoreScn = null;
                applyEndScene();
                break;
        }
    }

    /**
     * This method prepares the StartScene. It calls the constructor of
     * StartScene if the current StartScene instance is null.
     */
    private void prepareStartScene() {
        if (startScn == null) {
            System.out.println("Start Scene is null. Making a new one");
            startScn = new StartScene(this, gamepane);
        }
    }

    /**
     * This method applies the StartScene. The method is a collection operations
     * to be run in order for the scene to function properly.
     */
    public void applyStartScene() {
        gameloop.stopLoop();
        prepareStartScene();
        gameloop.resetGameStatus();
        stage.setScene(startScn);
        stage.show();
    }

    /**
     * This method prepares the GamePlayScene. It calls the constructor of
     * GamePlayScene if the current GamePlayScene instance is null.
     */
    public void prepareGamePlayScene() {
        if (gamePlayScn == null) {
            System.out.println("Game Play Scene is null. Making a new one");
            gamePlayScn = new GamePlayScene(this, gamepane);
        }
    }

    /**
     * This method applies the GamePlayScene. The method is a collection
     * operations to be run in order for the scene to function properly.
     */
    public void applyGamePlayScene() {
        prepareGamePlayScene();
        stage.setScene(gamePlayScn);
        if (gameloop.startLoop(gamePlayScn)) {
            stage.show();
        }

    }

    /**
     * This method prepares the BonusScene. It calls the constructor of
     * BonusScene if the current BonusScene instance is null.
     */
    public void prepareBonusScene() {
        if (bonusScn == null) {
            System.out.println("Bonus Scene is null. Making a new one");
            gameloop.levelUp();
            bonusScn = new BonusScene(this, gamepane);
        }
    }

    /**
     * This method applies the BonusScene. The method is a collection of
     * operations to be run in order for the scene to function properly.
     */
    public void applyBonusScene() {
        gameloop.stopLoop();
        prepareBonusScene();
        stage.setScene(bonusScn);
        //if (gameloop.startLoop(bonusScn)) {
            stage.show();
        //}

    }

    /**
     * This method prepares the EndScene. It calls the constructor of EndScene
     * if the current EndScene instance is null.
     */
    public void prepareEndScene() {
        if (endScn == null) {
            System.out.println("End Scene is null. Making a new one");
            endScn = new EndScene(this, gamepane);
        }
    }

    /**
     * This method applies the EndScene. The method is a collection operations
     * to be run in order for the scene to function properly.
     */
    public void applyEndScene() {
        gameloop.stopLoop();
        prepareEndScene();
        stage.setScene(endScn);
        stage.show();
    }

    /**
     * Convenience method that calls applyNextScene(String) with parameter
     * NOBASES. Used to transition from GamePlayScene to next scene in the event
     * of all player bases been destroyed
     */
    public void noBasesLeft() {
        this.chooseNextScene(Actions.NOBASES);
        this.applyNextScene();
    }

    /**
     * Convenience method that calls applyNextScene(String) with parameter
     * NOCITIES. Used to transition from GamePlayScene to next scene in the event
     * of all player cities been destroyed
     */
    public void noCitiesLeft() {
        this.chooseNextScene(Actions.NOCITIES);
        this.applyNextScene();
    }
    
    /**
     * Convenience method that calls applyNextScene(String) with parameter
     * ENOUGHDESTROYED. Used to transition from GamePlayScene to next scene in the event
     * of enough of player cities have been destroyed
     */
    public void enoughCitiesDestroyed() {
        this.chooseNextScene(Actions.ENOUGHDESTROYED);
        this.applyNextScene();
    }
    
    /**
     * Convenience method that calls applysNextScene(String) with parameter
     * NOINCOMING. Used to transition from GamePlayScene to next scene in the event
     * of no more incoming missiles in level
     */
    public void noIncomingLeft() {
        this.chooseNextScene(Actions.NOINCOMING);
        this.applyNextScene();
    }
}

