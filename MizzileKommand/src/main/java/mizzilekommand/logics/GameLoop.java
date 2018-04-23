/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Shape;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import static mizzilekommand.logics.MizzileKommand.BASE_RADIUS;
import static mizzilekommand.logics.MizzileKommand.BASE_X;
import static mizzilekommand.logics.MizzileKommand.BASE_Y;
import static mizzilekommand.logics.MizzileKommand.SMALL_LENGTH;
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

    public List<Node> addToScene;
    public List<Node> removeFromScene;

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

    AudioClip explosionAudio = new AudioClip("file:Explosion.m4a");

    public GameLoop() {

        this.stopLoop = false;
        this.allowIncoming = false;

        this.gameStatus = new GameStatus();

        this.addToScene = new ArrayList<>();
        this.removeFromScene = new ArrayList<>();

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

        explosionAudio.setVolume(0.35);
        explosionAudio.setCycleCount(0); // Played once

    }

    /**
     * This method sets the SceneController that is needed to perform the
     * actions decided in the game loop logic on the current scene.
     *
     * @param controller a SceneController
     */
    public void setSceneController(SceneController controller) {
        this.controller = controller;
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
                    }

                    handleGameLoopCalls();

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
    private void prepareForGameLoop() {
        addBases();
        addCities();
        addNodesToScene();
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
        removeNodesFromScene();
        addNodesToScene();
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
    private void clearNodes() {
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
     * @return The new level
     */
    public int levelUp() {
        return gameStatus.levelUp();
    }

    /**
     * This method resets the game status. It Calls the GameStatus object
     */
    public void resetGameStatus() {
        gameStatus.reset();
    }

    /**
     * This adds to the current scene all the nodes in the list addToScene
     */
    private void addNodesToScene() {
        if (!addToScene.isEmpty()) {
            controller.addAllToCurrentScene(addToScene);
            addToScene.clear();
        }
    }

    /**
     * This removes from the current scene all the nodes in list removeFromScene
     */
    private void removeNodesFromScene() {
        if (!removeFromScene.isEmpty()) {
            controller.removeAllFromCurrentScene(removeFromScene);
            removeFromScene.clear();
        }
    }

    private void checkLevelStatus() {
        // If no cities are left, it's game over -> The End Scene
        if (cities.isEmpty()) {
            allowIncoming = false;
            if (actionsDone()) {
                controller.noCitiesLeft();
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
     * @param base The id of the base from which it should be launched
     * @param targetX The x coordinate of the missile's target
     * @param targetY The y coordinate of the missile's target
     */
    public void launchNewPlayerMissile(int base, double targetX, double targetY) {
        if (gameStatus.baseMissilesLeft(base)) {
            PlayerMissile missile = new PlayerMissile(System.currentTimeMillis(),
                    (base == 1 ? 2.5 : 2.0), targetX, targetY);
            playerMissiles.add(missile);
            missile.setLayoutX(BASE_X[base] - (missile.width / 2.0));
            missile.setLayoutY(BASE_Y - BASE_RADIUS - missile.height);
            missile.setDirection();
            addToScene.add(missile);

        }
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
            removeFromScene.add(missile);
        });
        playerMissilesToRemove.clear();
        playerMissiles.forEach(missile -> missile.fly());
        playerMissiles.forEach(missile -> {
            if (missile.isAtTargetHeight()) {
                playerMissilesToRemove.add(missile);
                Explosion explosion = missile.detonate();
                if (explosion != null) {
                    addToScene.add(explosion);
                    playerExplosions.add(explosion);
                    explosionAudio.play();
                }
            }
        });
        playerMissiles.removeAll(playerMissilesToRemove);

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
            removeFromScene.add(explosion);
        });
        playerExplosionsToRemove.clear();
        playerExplosions.forEach(explosion -> {
            explosion.fade();
            enemyMissiles.forEach(missile -> {
                if (didDestroyEnemyMissile(explosion, missile)) {
                    detonateMissile(missile);
                }
            });
        });
        enemyMissiles.removeAll(enemyMissilesToRemove);
        playerExplosions.forEach(explosion -> {
            if (explosion.faded()) {
                playerExplosionsToRemove.add(explosion);
            }
        });
        playerExplosions.removeAll(playerExplosionsToRemove);

    }
    
    /**
     * This detonates the enemy missile
     * @param missile The missile to be detonated
     */
    private void detonateMissile(EnemyMissile missile) {
        Explosion destruction = missile.detonate();
        addToScene.add(destruction);
        enemyExplosions.add(destruction);
        explosionAudio.play();
        enemyMissilesToRemove.add(missile);
    }

    /**
     * This method checks for the conditions in which new enemy missiles should
     * be added to the scene.
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
        double x = 0.05 * APP_WIDTH + Math.random() * (APP_WIDTH * 0.90);
        EnemyMissile missile = new EnemyMissile(System.currentTimeMillis(),
                gameStatus.getIncomingSpeedFactor(), x, APP_HEIGHT * 0.8);
        enemyMissiles.add(missile);
        missile.setLayoutX(x);
        missile.setLayoutY(0);
        missile.setRotate(180.0);
        addToScene.add(missile);
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
            removeFromScene.add(missile);
        });
        enemyMissilesToRemove.clear();
        enemyMissiles.forEach(missile -> missile.fly());
        enemyMissiles.forEach(missile -> {
            if (missile.isAtTargetHeight()) {
                enemyMissilesToRemove.add(missile);
                Explosion explosion = missile.detonate();
                if (explosion != null) {
                    addToScene.add(explosion);
                    enemyExplosions.add(explosion);
                    explosionAudio.play();
                }
            }
        });
        enemyMissiles.removeAll(enemyMissilesToRemove);

    }

    /**
     * This method handles the existing enemy missile explosions. It uses two
     * lists - one with explosions to be removed and the other with explosinos
     * continuing in the game. The removables will be removed by the scene
     * controller. The method checks to see if the explosion causes any
     * destruction to cities or bases. After that it checks to see if the
     * explosions have faded enough and if that is the case adds those to the
     * removables list.
     */
    public void handleEnemyMissileExplosions() {
        enemyExplosionsToRemove.forEach(explosion -> {
            removeFromScene.add(explosion);
        });
        enemyExplosionsToRemove.clear();
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
        });
        enemyExplosions.forEach(explosion -> {
            if (explosion.faded()) {
                enemyExplosionsToRemove.add(explosion);
            }
        });
        enemyExplosions.removeAll(enemyExplosionsToRemove);

    }
    
    /**
     * This method explodes the base
     * @param base The base to be exploded
     */
    private void explodeBase(Base base) {
        Explosion annihilation = base.detonate();
        addToScene.add(annihilation);
        baseExplosions.add(annihilation);
        explosionAudio.play();
        basesToRemove.add(base);
        gameStatus.destroyBase(base.id);
    }
    
    /**
     * This method detructs the city
     * @param city The city to be destructed
     */
    private void destructCity(City city) {
        CityDestruction armageddon = city.destruct();
        addToScene.add(armageddon);
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
            removeFromScene.add(base);
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
            removeFromScene.add(explosion);
        });
        baseExplosionsToRemove.clear();
        baseExplosions.forEach(explosion -> {
            explosion.fade();
        });
        baseExplosions.forEach(explosion -> {
            if (explosion.faded()) {
                baseExplosionsToRemove.add(explosion);
            }
        });
        baseExplosions.removeAll(baseExplosionsToRemove);

    }

    /**
     * This method is called from the actual game loop. It checks the status if
     * the cities and instructs the SceneController to switch the scene if no
     * cities are left or if already 3 cities were destroyed in level.
     */
    public void handleCities() {
        citiesToRemove.forEach(city -> {
            removeFromScene.add(city);
        });
        citiesToRemove.clear();
    }

    public void handleCityDestructions() {
        cities.removeAll(citiesToRemove);
        cityDestructionsToRemove.forEach(destruction -> {
            removeFromScene.add(destruction);
        });
        cityDestructionsToRemove.clear();
        cityDestructions.forEach(destruction -> {
            destruction.fade(System.currentTimeMillis());
        });
        cityDestructions.forEach(destruction -> {
            if (destruction.faded()) {
                cityDestructionsToRemove.add(destruction);
            }
        });
        cityDestructions.removeAll(cityDestructionsToRemove);

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

    /**
     * This method constructs and adds all player bases to the scene and a List
     * that holds references to bases.
     *
     * @param scene a scene to add the bases to
     */
    public void addBases() {
        this.bases.clear();
        for (int id = 0; id < 3; id++) {
            Base base = new Base();
            base.setLayoutX(BASE_X[id]);
            base.setLayoutY(BASE_Y);
            base.id = id;
            this.bases.add(base);
            addToScene.add(base);
        }
    }

    /**
     * This method constructs and adds all player cities to the scene and a List
     * that holds references to cities.
     *
     * @param scene a scene to add the cities to
     */
    public void addCities() {
        this.cities.clear();

        // Cities are polygons that have their location in (0,0) = left upper corner
        for (int c = 1; c <= 3; c += 1) {
            if (gameStatus.cityNotDestroyed(c - 1)) {
                City cityL = new City();
                cityL.setLayoutX((APP_WIDTH / 32.0) + ((APP_WIDTH / 8.0) * c) - (cityL.width / 2.0));
                cityL.setLayoutY(APP_HEIGHT - SMALL_LENGTH * 5.0);
                cityL.id = (int) c - 1;
                this.cities.add(cityL);
                addToScene.add(cityL);
            }
            if (gameStatus.cityNotDestroyed(c + 2)) {
                City cityR = new City();
                cityR.setLayoutX((APP_WIDTH / 2.0) + ((APP_WIDTH / 8.0) * c) - (cityR.width / 2.0));
                cityR.setLayoutY(APP_HEIGHT - SMALL_LENGTH * 5.0);
                cityR.id = (int) c + 2;
                this.cities.add(cityR);
                addToScene.add(cityR);
            }
        }
    }

    /**
     * Convenience method that tells how many bases are left in the list
     * containing the base objects.
     *
     * @return number of bases left
     */
    public int basesLeft() {
        return bases.size();
    }

    /**
     * Convenience method that tells how many cities are left in the list
     * containing the city objects.
     *
     * @return number of cities left
     */
    public int citiesLeft() {
        return cities.size();
    }

    /**
     * Convenience method that tells how many enemy missiles are left in the
     * list containing the enemy missile objects.
     *
     * @return number of enemy missiles left
     */
    public int enemyMissilesLeft() {
        return enemyMissiles.size();
    }

}
