/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is a base class for explosions
 *
 * @author jaakkovilenius
 */
public class Explosion extends Circle {

    private double radiusOriginal;
    private double radius;
    private long burnFrom;
    public long burnUntil;
    private long burnTime;
    
    private static final String EXPLOSION_STYLE
            = "-fx-fill: "
            + "radial-gradient(focus-distance 0% , center 50% 50% , radius 67% , white 33%, #ffa500 75%, transparent 95%) ";

    public Explosion(double centerX, double centerY, double radius, long now, long burnTime) {

        super(centerX, centerY, radius);

        this.radiusOriginal = radius;
        this.radius = radius;
        this.burnFrom = now;
        this.burnUntil = now + burnTime;
        this.burnTime = burnTime;
        this.setStyle(EXPLOSION_STYLE);

    }

    /**
     * This fades the explosion.
     *
     * @param now System time in milliseconds used to calculate the remaining
     * burn time and current size
     */
    public void fade() {
        double factor = ((double) (System.currentTimeMillis() - burnFrom) / (double) burnTime);
        this.setOpacity(Math.max(1.0 - factor, 0.0));
        double scale = 1.0 + (factor * 0.33);
        this.radius = radiusOriginal * scale;
        this.setScaleX(scale);
        this.setScaleY(scale);
    }

    /**
     * This checks whether the explosion has already faded.
     *
     * @return true if has faded and should be removed from the scene, false
     * otherwise.
     */
    public boolean faded() {
        if (System.currentTimeMillis() >= burnUntil) {
            return true;
        }
        return false;
    }

}
