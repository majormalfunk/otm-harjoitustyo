/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Shape;
import mizzilekommand.layout.SceneTemplate;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import static mizzilekommand.logics.MizzileKommand.SMALL_LENGTH;
import mizzilekommand.nodes.Base;
import mizzilekommand.nodes.City;
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
    public SceneTemplate currentScene;

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
    public List<Explosion> cityExplosions;
    public List<Explosion> cityExplosionsToRemove;

    public GameLoop() {

        this.stopLoop = false;
        this.allowIncoming = false;

        this.gameStatus = new GameStatus();

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
        this.cityExplosions = new ArrayList<>();
        this.cityExplosionsToRemove = new ArrayList<>();

    }

    /**
     * This method starts the game loop which is a javafx AnimationTimer.
     *
     * @return true when loop is successfully run
     */
    public boolean startLoop(SceneTemplate scene) {

        currentScene = scene;

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
                        stop();
                    }

                    // Bases
                    handleBases();

                    // Enemy missiles
                    handleEnemyMissiles();
                    handleNewEnemyMissiles();

                    // Enemy explosions
                    handleEnemyMissileExplosions();

                    // Base explosions
                    handleBaseExplosions();

                    // City destructions
                    handleCities();

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

    public int levelUp() {
        return gameStatus.levelUp();
    }

    public void resetGameStatus() {
        gameStatus.reset();
    }

    private void handleNewEnemyMissiles() {

        if (gameStatus.incomingMissilesLeft()) {
            if (allowIncoming && Math.random() < 0.005) {
                incoming();
                gameStatus.incomingMissilesDecrease();
            }
        } else {
            allowIncoming = false;
            if (baseExplosions.isEmpty()
                    && enemyMissiles.isEmpty()
                    && enemyExplosions.isEmpty()) {
                currentScene.getController().noIncomingLeft();
            }
        }
    }

    /**
     * This method adds a new enemy missile to the scene.
     */
    private void incoming() {
        EnemyMissile missile = new EnemyMissile(System.currentTimeMillis());
        enemyMissiles.add(missile);
        missile.setLayoutX(0.05 * APP_WIDTH + Math.random() * (APP_WIDTH * 0.90));
        missile.setLayoutY(0);
        missile.setRotate(180.0);
        currentScene.getSceneRoot().getChildren().add(missile);
    }

    private void handleEnemyMissiles() {
        // Enemy missiles
        enemyMissilesToRemove.forEach(missile -> {
            currentScene.getSceneRoot().getChildren().remove(missile);
        });
        enemyMissilesToRemove.clear();
        enemyMissiles.forEach(missile -> missile.fly());
        enemyMissiles.forEach(missile -> {
            if (missile.getLayoutY() >= APP_HEIGHT * 0.8) {
                enemyMissilesToRemove.add(missile);
                Explosion explosion = missile.detonate();
                if (explosion != null) {
                    currentScene.getSceneRoot().getChildren().add(explosion);
                    enemyExplosions.add(explosion);
                }
            }
        });
        enemyMissiles.removeAll(enemyMissilesToRemove);

    }

    private void handleEnemyMissileExplosions() {
        enemyExplosionsToRemove.forEach(explosion -> {
            currentScene.getSceneRoot().getChildren().remove(explosion);
        });
        enemyExplosionsToRemove.clear();
        enemyExplosions.forEach(explosion -> {
            explosion.fade(System.currentTimeMillis());
            bases.forEach(base -> {
                if (didDestroyBase(explosion, base)) {
                    Explosion annihilation = base.detonate();
                    currentScene.getSceneRoot().getChildren().add(annihilation);
                    baseExplosions.add(annihilation);
                    basesToRemove.add(base);
                    gameStatus.destroyBase(base.id);
                }
            });
            cities.forEach(city -> {
                if (!gameStatus.citiesForLevelDestructed()) {
                    if (didDestroyCity(explosion, city)) {
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

    private void handleBases() {
        basesToRemove.forEach(base -> {
            currentScene.getSceneRoot().getChildren().remove(base);
        });
        basesToRemove.clear();
    }

    private void handleBaseExplosions() {
        bases.removeAll(basesToRemove);
        baseExplosionsToRemove.forEach(explosion -> {
            currentScene.getSceneRoot().getChildren().remove(explosion);
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

        cities.removeAll(citiesToRemove);

        citiesToRemove.forEach(city -> {
            currentScene.getSceneRoot().getChildren().remove(city);
        });
        citiesToRemove.clear();

        // If no cities are left, it's game over -> The End Scene
        if (cities.isEmpty()) {
            allowIncoming = false;
            if (baseExplosions.isEmpty()
                    && enemyMissiles.isEmpty()
                    && enemyExplosions.isEmpty()) {
                currentScene.getController().noCitiesLeft();
            }
        } else {
            // Otherwise if already enough cities were destroyed -> Bonus Scene
            if (gameStatus.citiesForLevelDestructed()) {
                allowIncoming = false;
                if (baseExplosions.isEmpty()
                        && enemyMissiles.isEmpty()
                        && enemyExplosions.isEmpty()) {
                    currentScene.getController().enoughCitiesDestroyed();
                }
            }
        }
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

        // Bases are arcs. Unlike e.g. Polygons that have their location in (0,0)
        // Arcs have their (0,0) in the center of the arc.
        if (gameStatus.baseNotDestroyed(0)) {
            Base baseLeft = new Base();
            baseLeft.setLayoutX(baseLeft.getBaseWidth() * 1.5);
            baseLeft.setLayoutY(APP_HEIGHT - SMALL_LENGTH * 4.0);
            baseLeft.id = 0;
            this.bases.add(baseLeft);
            currentScene.getSceneRoot().getChildren().add(baseLeft);
        }
        if (gameStatus.baseNotDestroyed(1)) {
            Base baseCenter = new Base();
            baseCenter.setLayoutX(APP_WIDTH / 2.0);
            baseCenter.setLayoutY(APP_HEIGHT - SMALL_LENGTH * 4.0);
            baseCenter.id = 1;
            this.bases.add(baseCenter);
            currentScene.getSceneRoot().getChildren().add(baseCenter);
        }
        if (gameStatus.baseNotDestroyed(2)) {
            Base baseRight = new Base();
            baseRight.setLayoutX(APP_WIDTH - (baseRight.getBaseWidth() * 1.5));
            baseRight.setLayoutY(APP_HEIGHT - SMALL_LENGTH * 4.0);
            baseRight.id = 2;
            this.bases.add(baseRight);
            currentScene.getSceneRoot().getChildren().add(baseRight);
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
                currentScene.getSceneRoot().getChildren().add(cityL);
            }
            if (gameStatus.cityNotDestroyed(c + 2)) {
                City cityR = new City();
                cityR.setLayoutX((APP_WIDTH / 2.0) + ((APP_WIDTH / 8.0) * c) - (cityR.width / 2.0));
                cityR.setLayoutY(APP_HEIGHT - SMALL_LENGTH * 5.0);
                cityR.id = (int) c + 2;
                this.cities.add(cityR);
                currentScene.getSceneRoot().getChildren().add(cityR);
            }
        }
    }

    public int basesLeft() {
        return bases.size();
    }

    public int citiesLeft() {
        return cities.size();
    }

    public int enemyMissilesLeft() {
        return enemyMissiles.size();
    }

}
