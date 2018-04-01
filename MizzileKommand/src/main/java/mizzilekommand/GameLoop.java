/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Shape;
import layout.SceneTemplate;
import static mizzilekommand.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.MizzileKommand.APP_WIDTH;
import nodes.Base;
import nodes.Explosion;
import nodes.Missile;

/**
 * This is the game loop for the game. The actual implementation involves
 * a javafx AnimationTimer
 * @author jaakkovilenius
 */
public class GameLoop {

    private boolean stopLoop;
    private boolean allowIncoming;
    private List<Missile> enemyMissiles;
    private List<Missile> enemyMissilesToRemove;
    private List<Explosion> enemyExplosions;
    private List<Explosion> enemyExplosionsToRemove;
    private List<Base> bases;
    private List<Base> basesToRemove;
    private List<Explosion> baseExplosions;
    private List<Explosion> baseExplosionsToRemove;

    public GameLoop() {

        this.stopLoop = false;
        this.allowIncoming = false;
        this.enemyMissiles = new ArrayList<>();
        this.enemyMissilesToRemove = new ArrayList<>();
        this.enemyExplosions = new ArrayList<>();
        this.enemyExplosionsToRemove = new ArrayList<>();
        this.bases = new ArrayList<>();
        this.basesToRemove = new ArrayList<>();
        this.baseExplosions = new ArrayList<>();
        this.baseExplosionsToRemove = new ArrayList<>();

    }
    
    /**
     * This method constructs and adds all player bases to the scene and a List
     * that holds references to bases.
     * @param scene a scene to add the bases to
     */
    public void addAllBases(SceneTemplate scene) {
        this.bases.clear();

        // Bases are arcs. Unlike e.g. Polygons that have their location in (0,0)
        // Arcs have their (0,0) in the center of the arc.
        Base baseLeft = new Base();
        baseLeft.setLayoutX(baseLeft.getBaseWidth()*1.5);
        baseLeft.setLayoutY(APP_HEIGHT * 0.9);
        this.bases.add(baseLeft);
        Base baseCenter = new Base();
        baseCenter.setLayoutX(APP_WIDTH/2.0);
        baseCenter.setLayoutY(APP_HEIGHT * 0.9);
        this.bases.add(baseCenter);
        Base baseRight = new Base();
        baseRight.setLayoutX(APP_WIDTH-(baseRight.getBaseWidth()*1.5));
        baseRight.setLayoutY(APP_HEIGHT * 0.9);
        this.bases.add(baseRight);
        
        scene.getSceneRoot().getChildren().addAll(baseLeft, baseCenter, baseRight);

        System.out.println("Bases containing " + this.bases.size() + " bases");
    }

    /**
     * This method starts the game loop which is a javafx AnimationTimer.
     * We should move stuff from the loop to methods to be called from the loop
     * to keep it tidier and more readable. Also lots of repetition in place
     * at the moment.
     * @param scene
     * @return 
     */
    public boolean startLoop(SceneTemplate scene) {

        addAllBases(scene);
        
        stopLoop = false;
        allowIncoming = true;
        try {
            System.out.println("Hello from The Game Loop!");
            new AnimationTimer() {

                @Override
                public void handle(long now) {
                    // Handle loop stopping first
                    if (stopLoop) {
                        stopLoop = false;
                        System.out.println("Stopping Game Loop.");
                        enemyMissiles.clear();
                        enemyExplosions.clear();
                        baseExplosions.clear();
                        stop();
                    }
                    
                    // Bases
                    basesToRemove.forEach(base -> {
                        scene.getSceneRoot().getChildren().remove(base);
                    });
                    basesToRemove.clear();

                    // Enemy missiles
                    enemyMissilesToRemove.forEach(missile -> {
                        scene.getSceneRoot().getChildren().remove(missile);
                    });
                    enemyMissilesToRemove.clear();
                    enemyMissiles.forEach(missile -> missile.fly());
                    enemyMissiles.forEach(missile -> {
                        if (missile.getLayoutY() >= APP_HEIGHT * 0.8) {
                            enemyMissilesToRemove.add(missile);
                            Explosion explosion = missile.detonate();
                            scene.getSceneRoot().getChildren().add(explosion);
                            enemyExplosions.add(explosion);
                        }
                    });
                    enemyMissiles.removeAll(enemyMissilesToRemove);

                    // Enemy explosions
                    enemyExplosionsToRemove.forEach(explosion -> {
                        scene.getSceneRoot().getChildren().remove(explosion);
                    });
                    enemyExplosionsToRemove.clear();
                    enemyExplosions.forEach(explosion -> { 
                            explosion.fade(System.currentTimeMillis());
                            bases.forEach(base -> {
                                if (didDestroy(explosion, base)) {
                                    System.out.println("ANNIHILATION!");
                                    Explosion annihilation = base.detonate();
                                    scene.getSceneRoot().getChildren().add(annihilation);
                                    baseExplosions.add(annihilation);
                                    basesToRemove.add(base);
                                }
                            });
                    });
                    enemyExplosions.forEach(explosion -> {
                        if (explosion.faded()) {
                            enemyExplosionsToRemove.add(explosion);
                            //explosion.dissipate();
                        }
                    });
                    enemyExplosions.removeAll(enemyExplosionsToRemove);

                    // Base explosions
                    bases.removeAll(basesToRemove);
                    baseExplosionsToRemove.forEach(explosion -> {
                        scene.getSceneRoot().getChildren().remove(explosion);
                    });
                    baseExplosionsToRemove.clear();
                    baseExplosions.forEach(explosion -> { 
                            explosion.fade(System.currentTimeMillis());
                    });
                    baseExplosions.forEach(explosion -> {
                        if (explosion.faded()) {
                            baseExplosionsToRemove.add(explosion);
                            //explosion.dissipate();
                        }
                    });
                    baseExplosions.removeAll(baseExplosionsToRemove);
                    
                    if (bases.isEmpty() && baseExplosions.isEmpty()) {
                        allowIncoming = false;
                    }
                    if (bases.isEmpty() &&
                            baseExplosions.isEmpty() &&
                            enemyMissiles.isEmpty() &&
                            enemyExplosions.isEmpty()) {
                        scene.getController().noBasesLeft();
                    }
                    if (allowIncoming && Math.random() < 0.005) {
                        inComing(scene);
                    }

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
     * This method adds a new enemy missile to the scene.
     * @param scene 
     */
    private void inComing(SceneTemplate scene) {
        //System.out.println("Enemy missile count before: " + enemyMissiles.size());
        Missile missile = new Missile(System.currentTimeMillis());
        enemyMissiles.add(missile);
        missile.setLayoutX(0.05*APP_WIDTH + Math.random() * (APP_WIDTH*0.90));
        missile.setLayoutY(0);
        missile.setRotate(180.0);
        scene.getSceneRoot().getChildren().add(missile);
        System.out.println("Incoming! (" + missile.getId() + ")");
        //System.out.println("Enemy missile count after: " + enemyMissiles.size());
    }
    
    /**
     * This method checks if a base was destroyed by an explosion
     * @param explosion possibly destructive explosion
     * @param base possibly destructing base
     * @return true if the base was destroyed by the explosion false otherwise.
     */
    public boolean didDestroy(Explosion explosion, Base base) {
        Shape impactZone = Shape.intersect(explosion, base);
        return impactZone.getBoundsInLocal().getWidth() != -1;
    }
    
    public int basesLeft() {
        return bases.size();
    }
    
    public int enemyMissilesLeft() {
        return enemyMissiles.size();
    }


}
