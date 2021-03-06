/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import mizzilekommand.dao.FileHandler;
import mizzilekommand.dao.StatisticDao;
import mizzilekommand.nodes.Base;
import mizzilekommand.nodes.City;
import mizzilekommand.nodes.CityDestruction;
import mizzilekommand.nodes.Explosion;
import mizzilekommand.nodes.EnemyMissile;
import mizzilekommand.nodes.PlayerMissile;

/**
 * This is the game loop for the game. The actual implementation involves a
 * javafx AnimationTimer
 *
 * @author jaakkovilenius
 */
public class GameLoop {

    public boolean stopLoop;
    public boolean allowIncoming;

    public GameStatus gameStatus;
    public SceneController controller;

    public List<PlayerMissile> playerMissiles;
    public List<PlayerMissile> playerMissilesToRemove;
    public List<Explosion> playerExplosions;
    public List<Explosion> playerExplosionsToRemove;
    public List<EnemyMissile> enemyMissiles;
    public List<EnemyMissile> enemyMissilesToRemove;
    public List<Explosion> enemyExplosions;
    public List<Explosion> enemyExplosionsToRemove;
    public List<Base> bases;
    public List<Base> basesToRemove;
    public List<Explosion> baseExplosions;
    public List<Explosion> baseExplosionsToRemove;
    public List<City> cities;
    public List<City> citiesToRemove;
    public List<CityDestruction> cityDestructions;
    public List<CityDestruction> cityDestructionsToRemove;

    private Clip explosionSound;

    /**
     * GameLoop constructor. GameLoop handles the game operation
     *
     * @see mizzilekommand.logics.GameStatus
     */
    public GameLoop() {

        this.stopLoop = false;
        this.allowIncoming = false;

        this.gameStatus = new GameStatus();

        this.playerMissiles = new ArrayList<>();
        this.playerMissilesToRemove = new ArrayList<>();
        this.playerExplosions = new ArrayList<>();
        this.playerExplosionsToRemove = new ArrayList<>();
        this.enemyMissiles = new ArrayList<>();
        this.enemyMissilesToRemove = new ArrayList<>();
        this.enemyExplosions = new ArrayList<>();
        this.enemyExplosionsToRemove = new ArrayList<>();
        this.bases = new ArrayList<>();
        this.basesToRemove = new ArrayList<>();
        this.baseExplosions = new ArrayList<>();
        this.baseExplosionsToRemove = new ArrayList<>();
        this.cities = new ArrayList<>();
        this.citiesToRemove = new ArrayList<>();
        this.cityDestructions = new ArrayList<>();
        this.cityDestructionsToRemove = new ArrayList<>();

        loadExplosionSound();

    }

    /**
     * This method sets the SceneController that is needed to perform the
     * actions decided in the game loop logic on the current scene.
     *
     * @param controller a SceneController Scenecontroller takes care of
     * displaying the correct view
     *
     * @see mizzilekommand.logics.SceneController
     */
    public void setSceneController(SceneController controller) {
        this.controller = controller;
    }

    public void setStatisticDao(StatisticDao statDao) {
        gameStatus.setStatisticDao(statDao);
    }

    /**
     * This method starts the game loop which is a javafx AnimationTimer.
     *
     * @return true when loop is successfully run
     */
    public boolean startLoop() {

        prepareForGameLoop();

        try {

            new AnimationTimer() {

                @Override
                public void handle(long now) {
                    // Handle loop stopping first
                    if (stopLoop) {
                        stopLoop = false;
                        clearNodes();
                        stop();
                    } else {
                        handleGameLoopCalls();
                    }

                }

            }.start();

            return true;

        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * This handles the calls neccessary to be called befrore start of loop
     */
    public void prepareForGameLoop() {
        addBases();
        addCities();
        //addNodesToScene();
        stopLoop = false;
        allowIncoming = true;
    }

    /**
     * This method handles the action inside the gameloop
     */
    private void handleGameLoopCalls() {
        // DO NOT change the order:
        checkLevelStatus(); // 0
        handleBases(); // 1
        handleCities(); // 2
        handlePlayerMissiles(); // 3
        handlePlayerMissileExplosions(); // 4
        handleEnemyMissiles(); // 5
        handleNewEnemyMissiles(); // 6
        handleEnemyMissileExplosions(); // 7
        handleBaseExplosions(); // 8
        handleCityDestructions(); // 9
    }

    /**
     * Calling this method sets a status variable that stops the game loop from
     * the outside.
     */
    public void stopLoop() {
        stopLoop = true;
    }

    /**
     * This clears the nodes from the lists handled by the game loop
     */
    public void clearNodes() {
        playerMissiles.clear();
        playerExplosions.clear();
        enemyMissiles.clear();
        enemyExplosions.clear();
        baseExplosions.clear();
        cityDestructions.clear();
    }

    /**
     * This method increases the level in the game. It calls the levelUp()
     * method of the GameStatus object.
     *
     * @see mizzilekommand.logics.GameStatus#levelUp()
     *
     * @return The new level
     */
    public int levelUp() {
        return gameStatus.levelUp();
    }

    /**
     * This method resets the game status. It Calls the GameStatus object
     *
     * @see mizzilekommand.logics.GameStatus#reset()
     */
    public void resetGameStatus() {
        gameStatus.reset();
    }

    /**
     * This checks the level status to determine if the level has ended.
     * If so it calls the method of the scene controller that corresponds
     * to the reason the level ended.
     */
    public void checkLevelStatus() {
        if (cities.isEmpty()) {
            allowIncoming = false;
            if (actionsDone()) {
                controller.noCitiesLeft(gameStatus.isTopScore());
            }
        } else if (gameStatus.citiesForLevelDestructed()) {
            // Otherwise if already enough cities were destroyed -> Bonus Scene
            allowIncoming = false;
            if (actionsDone()) {
                controller.enoughCitiesDestroyed();
            }
        } else if (!gameStatus.incomingMissilesLeft()) {
            allowIncoming = false;
            if (actionsDone()) {
                controller.noIncomingLeft();
            }
        }
    }

    /**
     * Method to check that all actions have played out
     *
     * @return true if no unfinished actions, false otherwise
     */
    public boolean actionsDone() {
        return cityDestructions.isEmpty()
                && baseExplosions.isEmpty()
                && playerMissiles.isEmpty()
                && playerExplosions.isEmpty()
                && enemyMissiles.isEmpty()
                && enemyExplosions.isEmpty();
    }

    /**
     * This method is used to add new player missiles to the game. It checks
     * from the gamestatus object if the base from which it should be launched
     * is still operational and whether any missiles are left in that base. The
     * method constructs a missile and adds it to the list for the game loop to
     * handle and asks the controller to add it to the scene
     *
     * @param missile The new missile to be handled
     * @param fromBase The id of the base from which it should be launched
     *
     * @see mizzilekommand.logics.GameStatus#baseMissilesLeft(int)
     * @see mizzilekommand.logics.GameStatus#substractMissileFromBase(int)
     */
    public void handleNewPlayerMissile(PlayerMissile missile, int fromBase) {
        playerMissiles.add(missile);
        gameStatus.substractMissileFromBase(fromBase);
    }

    /**
     * This method handles the existing player missiles. It uses two lists - one
     * with missiles to be removed and the other with missiles continuing in the
     * game. The removables will be removed by the scene controller. After that
     * it checks to see if the missiles have reached the height at which they
     * explode and if that is the case adds those to the removables list and
     * adds an explosion to the player explosions list and instructs the
     * controller to add the explosion to the scene.
     */
    public void handlePlayerMissiles() {
        // Player missiles
        playerMissilesToRemove.forEach(missile -> {
            controller.removeFromCurrentScene(missile);
        });
        playerMissilesToRemove.clear();
        flyPlayerMissiles();
        playerMissiles.removeAll(playerMissilesToRemove);
    }
    
    /**
     * This method flys the missiles and checks if they reach
     * target height. In that case they explode.
     */
    private void flyPlayerMissiles() {
        playerMissiles.forEach(missile -> {
            missile.fly();
            if (missile.isAtTargetHeight()) {
                playerMissilesToRemove.add(missile);
                Explosion explosion = missile.detonate();
                if (explosion != null) {
                    controller.addToCurrentScene(explosion);
                    playerExplosions.add(explosion);
                    playExplosionSound();
                }
            }
        });
        
    }

    /**
     * This method handles the existing player missile explosions. It uses two
     * lists - one with explosions to be removed and the other with explosinos
     * continuing in the game. The removables will be removed by the scene
     * controller. The method checks to see if the explosion destroys any enemy
     * missiles. After that it checks to see if the explosions have faded enough
     * and if that is the case adds those to the removables list.
     */
    public void handlePlayerMissileExplosions() {
        playerExplosionsToRemove.forEach(explosion -> {
            controller.removeFromCurrentScene(explosion);
        });
        playerExplosionsToRemove.clear();
        checkForEnemyMissileInterceptions();
        enemyMissiles.removeAll(enemyMissilesToRemove);
        playerExplosions.removeAll(playerExplosionsToRemove);

    }

    /**
     * Checks to see if player missile explosion destroyed any enemy missiles.
     */
    private void checkForEnemyMissileInterceptions() {
        playerExplosions.forEach(explosion -> {
            explosion.fade();
            enemyMissiles.forEach(missile -> {
                if (didDestroyEnemyMissile(explosion, missile)) {
                    detonateMissile(missile);
                    gameStatus.enemyMissileDestroyed();
                }
            });
            if (explosion.faded()) {
                playerExplosionsToRemove.add(explosion);
            }
        });

    }

    /**
     * This detonates the enemy missile
     *
     * @param missile The missile to be detonated
     */
    public void detonateMissile(EnemyMissile missile) {
        Explosion destruction = missile.detonate();
        controller.addToCurrentScene(destruction);
        enemyExplosions.add(destruction);
        playExplosionSound();
        enemyMissilesToRemove.add(missile);
    }

    /**
     * This method checks for the conditions in which new enemy missiles should
     * be added to the scene.
     *
     * @see mizzilekommand.logics.GameStatus#incomingMissilesDecrease()
     */
    public void handleNewEnemyMissiles() {

        if (gameStatus.incomingMissilesLeft()) {
            if (allowIncoming && Math.random() < gameStatus.incomingPace) {
                incoming();
                gameStatus.incomingMissilesDecrease();
            }
        }
    }

    /**
     * This method adds a new enemy missile to the scene.
     */
    public void incoming() {
        enemyMissiles.add(controller.addIncoming(gameStatus.getIncomingSpeedFactor()));
    }

    /**
     * This method handles the existing enemy missiles. It uses two lists - one
     * with missiles to be removed and the other with missiles continuing in the
     * game. The removables will be removed by the scene controller. After that
     * it checks to see if the missiles have reached the height at which they
     * explode and if that is the case adds those to the removables list and
     * adds an explosion to the enemy explosions list and instructs the
     * controller to add the explosion to the scene.
     */
    public void handleEnemyMissiles() {
        // Enemy missiles
        enemyMissilesToRemove.forEach(missile -> {
            controller.removeFromCurrentScene(missile);
        });
        enemyMissilesToRemove.clear();
        flyEnemyMissiles();
        enemyMissiles.removeAll(enemyMissilesToRemove);
    }

    /**
     * This handles the flight and end of player missiles.
     */
    private void flyEnemyMissiles() {
        enemyMissiles.forEach(missile -> missile.fly());
        enemyMissiles.forEach(missile -> {
            if (missile.isAtTargetHeight()) {
                enemyMissilesToRemove.add(missile);
                Explosion explosion = missile.detonate();
                if (explosion != null) {
                    controller.addToCurrentScene(explosion);
                    enemyExplosions.add(explosion);
                    playExplosionSound();
                }
            }
        });
    }
    
    /**
     * This method handles the existing enemy missile explosions. It uses two
     * lists - one with explosions to be removed and the other with explosinos
     * continuing in the game. The removables will be removed by the scene
     * controller. The method checks to see if the explosion causes any
     * destruction to cities or bases. After that it checks to see if the
     * explosions have faded enough and if that is the case adds those to the
     * removables list.
     *
     * @see mizzilekommand.logics.GameStatus#citiesForLevelDestructed()
     */
    public void handleEnemyMissileExplosions() {
        enemyExplosionsToRemove.forEach(explosion -> {
            controller.removeFromCurrentScene(explosion);
        });
        enemyExplosionsToRemove.clear();
        checkForCityAndBaseDestructions();
        enemyExplosions.removeAll(enemyExplosionsToRemove);
    }

    /**
     * This checks if enemy explosion destroyed player assets.
     */
    private void checkForCityAndBaseDestructions() {
        enemyExplosions.forEach(explosion -> {
            explosion.fade();
            bases.forEach(base -> {
                if (didDestroyBase(explosion, base)) {
                    explodeBase(base);
                }
            });
            cities.forEach(city -> {
                if (!gameStatus.citiesForLevelDestructed()) {
                    if (didDestroyCity(explosion, city)) {
                        destructCity(city);
                    }
                }
            });
            if (explosion.faded()) {
                enemyExplosionsToRemove.add(explosion);
            }
        });
    }

    /**
     * This method explodes the base
     *
     * @param base The base to be exploded
     *
     * @see mizzilekommand.logics.GameStatus#destroyBase(int)
     */
    public void explodeBase(Base base) {
        Explosion annihilation = base.detonate();
        controller.addToCurrentScene(annihilation);
        baseExplosions.add(annihilation);
        playExplosionSound();
        basesToRemove.add(base);
        gameStatus.destroyBase(base.id);
    }

    /**
     * This method destructs the city
     *
     * @param city The city to be destructed
     *
     * @see mizzilekommand.logics.GameStatus#destroyCity(int)
     */
    public void destructCity(City city) {
        CityDestruction armageddon = city.destruct();
        controller.addToCurrentScene(armageddon);
        cityDestructions.add(armageddon);
        citiesToRemove.add(city);
        gameStatus.destroyCity(city.id);
    }

    /**
     * This method intructs the SceneController to remove destructed bases from
     * the scene.
     */
    public void handleBases() {
        basesToRemove.forEach(base -> {
            controller.removeFromCurrentScene(base);
        });
        basesToRemove.clear();
    }

    /**
     * This method handles the existing enemy base explosions. It uses two lists
     * - one with explosions to be removed and the other with explosinos
     * continuing in the game. The removables will be removed by the scene
     * controller. After that it checks to see if the explosions have faded
     * enough and if that is the case adds those to the removables list.
     */
    public void handleBaseExplosions() {
        bases.removeAll(basesToRemove);
        baseExplosionsToRemove.forEach(explosion -> {
            controller.removeFromCurrentScene(explosion);
        });
        baseExplosionsToRemove.clear();
        fadeBaseExplosions();
        baseExplosions.removeAll(baseExplosionsToRemove);
    }

    /**
     * This fades the exploded player bases.
     */
    private void fadeBaseExplosions() {
        baseExplosions.forEach(explosion -> {
            explosion.fade();
            if (explosion.faded()) {
                baseExplosionsToRemove.add(explosion);
            }
        });
    }
    
    /**
     * This method is called from the actual game loop. It checks the status if
     * the cities and instructs the SceneController to switch the scene if no
     * cities are left or if already 3 cities were destroyed in level.
     */
    public void handleCities() {
        citiesToRemove.forEach(city -> {
            controller.removeFromCurrentScene(city);
        });
        citiesToRemove.clear();
    }

    /**
     * This handles destructing cities
     * 
     */
    public void handleCityDestructions() {
        cities.removeAll(citiesToRemove);
        cityDestructionsToRemove.forEach(destruction -> {
            controller.removeFromCurrentScene(destruction);
        });
        cityDestructionsToRemove.clear();
        fadeCityDestructions();
        cityDestructions.removeAll(cityDestructionsToRemove);
    }
    
    /**
     * This handles teh fading explosion of the cities
     */
    private void fadeCityDestructions() {
        cityDestructions.forEach(destruction -> {
            destruction.fade(System.currentTimeMillis());
            if (destruction.faded()) {
                cityDestructionsToRemove.add(destruction);
            }
        });
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
        return controller.didDestroyEnemyMissile(explosion, missile);
    }

    /**
     * This method checks if a base was destroyed by an explosion
     *
     * @param explosion possibly destructive explosion
     * @param base possibly destructing base
     * @return true if the base was destroyed by the explosion false otherwise.
     */
    public boolean didDestroyBase(Explosion explosion, Base base) {
        return controller.didDestroyBase(explosion, base);
    }

    /**
     * This method checks if a city was destroyed by an explosion
     *
     * @param explosion possibly destructive explosion
     * @param city possibly destructing city
     * @return true if the city was destroyed by the explosion false otherwise.
     */
    public boolean didDestroyCity(Explosion explosion, City city) {
        return controller.didDestroyCity(explosion, city);
    }

    /**
     * This method asks for a new list of bases from the controller. It passes
     * as paramters an array indicating which bases are ok. The new list of
     * bases is set as the bases list
     */
    public void addBases() {
        this.bases.clear();
        if (controller != null) {
            bases = controller.addBases(gameStatus.baseOk);
        }
    }

    /**
     * This method asks for a new list of cities from the controller. It passes
     * as paramters an array indicating which cities are ok. The new list of
     * cities is set as the cities list
     */
    public void addCities() {
        this.cities.clear();
        if (controller != null) {
            cities = controller.addCities(gameStatus.cityOk);
        }
    }

    /**
     * Loads an explosion sound from resources.
     */
    private void loadExplosionSound() {
        try {
            explosionSound = FileHandler.loadSoundFromResourceFile("Explosion.wav");
            FloatControl control = (FloatControl) explosionSound.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(control.getMinimum() * (1.0f / 100.0f));
        } catch (Exception e) {
            // Do nothing
        }
    }

    /**
     * Plays the loaded explosion sound.
     */
    public void playExplosionSound() {
        try {
            explosionSound.stop();
            explosionSound.setMicrosecondPosition(0);
            explosionSound.start();
        } catch (Exception e) {
            // Do nothing
        }
    }

}
