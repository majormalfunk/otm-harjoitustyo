/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

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
    
    public Explosion(double centerX, double centerY, double radius, long now, long burnTime, String style) {

        super(centerX, centerY, radius);

        this.radiusOriginal = radius;
        this.radius = radius;
        this.burnFrom = now;
        this.burnUntil = now + burnTime;
        this.burnTime = burnTime;
        this.setStyle(style);

    }

    /**
     * This fades the explosion.
     *
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
