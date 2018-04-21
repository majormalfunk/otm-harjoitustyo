/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
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

    private boolean stopLoop;
    private boolean allowIncoming;

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

    AudioClip explosionAudio = new AudioClip("file:Explosion.m4a");

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

        explosionAudio.setVolume(0.25);
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

        addBases();
        addCities();

        stopLoop = false;
        allowIncoming = true;
        try {

            new AnimationTimer() {

                @Override
                public void handle(long now) {
                    // Handle loop stopping first
                    if (stopLoop) {
                        stopLoop = false;
                        enemyMissiles.clear();
                        enemyExplosions.clear();
                        baseExplosions.clear();
                        cityDestructions.clear();
                        stop();
                    }

                    // These need to be in the following order:
                    // 1 - handleBases()
                    // 2 - handleCities()
                    // 3 - handleEnemyMissiles()
                    // 4 - handleNewEnemyMissiles()
                    // 5 - handleEnemyMissileExplosions()
                    // 6 - handleBaseExplosions()
                    // 7 - handleCityDesctructions()
                    handleBases();
                    handleCities();
                    handleEnemyMissiles();
                    handleNewEnemyMissiles();
                    handleEnemyMissileExplosions();
                    handleBaseExplosions();
                    handleCityDestructions();

                }

            }.start();

            return true;

        } catch (Exception ex) {
            System.out.println("Failed to start game loop: " + ex.getMessage());
            return false;
        }

    }

    /**
     * Calling this method sets a status variable that stops the game loop from
     * the outside.
     */
    public void stopLoop() {
        stopLoop = true;
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

    public void resetGameStatus() {
        gameStatus.reset();
    }

    /**
     * This method is used to add new player missiles to the game.
     * It checks from the gamestatus object if the base from which it should
     * be launched is still operational and whether any missiles are left
     * in that base. The method constructs a missile and adds it to the list
     * for the game loop to handle and asks the controller to add it to the scene
     * 
     * @param base The id of the base from which it should be launched
     * @param targetX The x coordinate of the missile's target
     * @param targetY The y coordinate of the missile's target
     */
    public void launchNewPlayerMissile(int base, double targetX, double targetY) {
        if (gameStatus.baseMissilesLeft(base)) {
            PlayerMissile missile = new PlayerMissile(System.currentTimeMillis(), targetX, targetY);
            playerMissiles.add(missile);
            missile.setLayoutX(BASE_X[base]-(missile.width/2.0));
            missile.setLayoutY(BASE_Y-BASE_RADIUS-missile.height);
            //missile.setRotate(180.0);
            controller.addToCurrentScene(missile);
        
        }
    }
    
    private void handlePlayerMissiles() {
        // Player missiles
        playerMissilesToRemove.forEach(missile -> {
            controller.removeFromCurrentScene(missile);
        });
        playerMissilesToRemove.clear();
        playerMissiles.forEach(missile -> missile.fly());
        playerMissiles.forEach(missile -> {
            if (missile.getLayoutY() >= APP_HEIGHT * 0.8) {
                playerMissilesToRemove.add(missile);
                Explosion explosion = missile.detonate();
                if (explosion != null) {
                    controller.addToCurrentScene(explosion);
                    playerExplosions.add(explosion);
                    explosionAudio.play();
                }
            }
        });
        playerMissiles.removeAll(playerMissilesToRemove);

    }

    
    /**
     * This method checks for the conditions in which new enemy missiles should
     * be added to the scene.
     */
    private void handleNewEnemyMissiles() {

        if (gameStatus.incomingMissilesLeft()) {
            if (allowIncoming && Math.random() < 0.005) {
                incoming();
                gameStatus.incomingMissilesDecrease();
            }
        } else {
            allowIncoming = false;
            if (cityDestructions.isEmpty()
                    && baseExplosions.isEmpty()
                    && enemyMissiles.isEmpty()
                    && enemyExplosions.isEmpty()) {
                controller.noIncomingLeft();
            }
        }
    }

    /**
     * This method adds a new enemy missile to the scene.
     */
    private void incoming() {
        double x = 0.05 * APP_WIDTH + Math.random() * (APP_WIDTH * 0.90);
        EnemyMissile missile = new EnemyMissile(System.currentTimeMillis(), x, APP_HEIGHT * 0.8);
        enemyMissiles.add(missile);
        missile.setLayoutX(x);
        missile.setLayoutY(0);
        missile.setRotate(180.0);
        controller.addToCurrentScene(missile);
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
    private void handleEnemyMissiles() {
        // Enemy missiles
        enemyMissilesToRemove.forEach(missile -> {
            controller.removeFromCurrentScene(missile);
        });
        enemyMissilesToRemove.clear();
        enemyMissiles.forEach(missile -> missile.fly());
        enemyMissiles.forEach(missile -> {
            if (missile.getLayoutY() >= missile.getTargetY()) {
                enemyMissilesToRemove.add(missile);
                Explosion explosion = missile.detonate();
                if (explosion != null) {
                    controller.addToCurrentScene(explosion);
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
    private void handleEnemyMissileExplosions() {
        enemyExplosionsToRemove.forEach(explosion -> {
            controller.removeFromCurrentScene(explosion);
        });
        enemyExplosionsToRemove.clear();
        enemyExplosions.forEach(explosion -> {
            explosion.fade(System.currentTimeMillis());
            bases.forEach(base -> {
                if (didDestroyBase(explosion, base)) {
                    Explosion annihilation = base.detonate();
                    controller.addToCurrentScene(annihilation);
                    baseExplosions.add(annihilation);
                    explosionAudio.play();
                    basesToRemove.add(base);
                    gameStatus.destroyBase(base.id);
                }
            });
            cities.forEach(city -> {
                if (!gameStatus.citiesForLevelDestructed()) {
                    if (didDestroyCity(explosion, city)) {
                        CityDestruction armageddon = city.destruct();
                        controller.addToCurrentScene(armageddon);
                        cityDestructions.add(armageddon);
                        citiesToRemove.add(city);
                        gameStatus.destroyCity(city.id);
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
     * This method intructs the SceneController to remove destructed bases from
     * the scene.
     */
    private void handleBases() {
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
    private void handleBaseExplosions() {
        bases.removeAll(basesToRemove);
        baseExplosionsToRemove.forEach(explosion -> {
            controller.removeFromCurrentScene(explosion);
        });
        baseExplosionsToRemove.clear();
        baseExplosions.forEach(explosion -> {
            explosion.fade(System.currentTimeMillis());
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
    private void handleCities() {
        citiesToRemove.forEach(city -> {
            controller.removeFromCurrentScene(city);
        });
        citiesToRemove.clear();

        // If no cities are left, it's game over -> The End Scene
        if (cities.isEmpty()) {
            allowIncoming = false;
            if (cityDestructions.isEmpty()
                    && baseExplosions.isEmpty()
                    && enemyMissiles.isEmpty()
                    && enemyExplosions.isEmpty()) {
                controller.noCitiesLeft();
            }
        } else {
            // Otherwise if already enough cities were destroyed -> Bonus Scene
            if (gameStatus.citiesForLevelDestructed()) {
                allowIncoming = false;
                if (cityDestructions.isEmpty()
                        && baseExplosions.isEmpty()
                        && enemyMissiles.isEmpty()
                        && enemyExplosions.isEmpty()) {
                    controller.enoughCitiesDestroyed();
                }
            }
        }
    }

    private void handleCityDestructions() {
        cities.removeAll(citiesToRemove);
        cityDestructionsToRemove.forEach(destruction -> {
            controller.removeFromCurrentScene(destruction);
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
    private void addBases() {
        this.bases.clear();

        if (gameStatus.baseNotDestroyed(0)) {
            Base baseLeft = new Base();
            baseLeft.setLayoutX(BASE_X[0]);
            baseLeft.setLayoutY(BASE_Y);
            baseLeft.id = 0;
            this.bases.add(baseLeft);
            controller.addToCurrentScene(baseLeft);
        }
        if (gameStatus.baseNotDestroyed(1)) {
            Base baseCenter = new Base();
            baseCenter.setLayoutX(BASE_X[1]);
            baseCenter.setLayoutY(BASE_Y);
            baseCenter.id = 1;
            this.bases.add(baseCenter);
            controller.addToCurrentScene(baseCenter);
        }
        if (gameStatus.baseNotDestroyed(2)) {
            Base baseRight = new Base();
            baseRight.setLayoutX(BASE_X[2]);
            baseRight.setLayoutY(BASE_Y);
            baseRight.id = 2;
            this.bases.add(baseRight);
            controller.addToCurrentScene(baseRight);
        }

    }

    /**
     * This method constructs and adds all player cities to the scene and a List
     * that holds references to cities.
     *
     * @param scene a scene to add the cities to
     */
    private void addCities() {
        this.cities.clear();

        // Cities are polygons that have their location in (0,0) = left upper corner
        for (int c = 1; c <= 3; c += 1) {
            if (gameStatus.cityNotDestroyed(c - 1)) {
                City cityL = new City();
                cityL.setLayoutX((APP_WIDTH / 32.0) + ((APP_WIDTH / 8.0) * c) - (cityL.width / 2.0));
                cityL.setLayoutY(APP_HEIGHT - SMALL_LENGTH * 5.0);
                cityL.id = (int) c - 1;
                this.cities.add(cityL);
                controller.addToCurrentScene(cityL);
            }
            if (gameStatus.cityNotDestroyed(c + 2)) {
                City cityR = new City();
                cityR.setLayoutX((APP_WIDTH / 2.0) + ((APP_WIDTH / 8.0) * c) - (cityR.width / 2.0));
                cityR.setLayoutY(APP_HEIGHT - SMALL_LENGTH * 5.0);
                cityR.id = (int) c + 2;
                this.cities.add(cityR);
                controller.addToCurrentScene(cityR);
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
