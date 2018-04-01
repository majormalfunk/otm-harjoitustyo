/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package nodes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * This is a base class for explosions
 * @author jaakkovilenius
 */
public class Explosion extends Circle {

    private double radiusOriginal;
    private double radius;
    private long burnFrom;
    private long burnUntil;
    private double burnTime;

    public Explosion(double centerX, double centerY, double radius, long now, long burnTime) {

        super(centerX, centerY, radius);

        this.radiusOriginal = radius;
        this.radius = radius;
        this.burnFrom = now;
        this.burnUntil = now + burnTime;
        this.burnTime = (double)burnTime;
        this.setFill(Color.WHITE);

    }

    /**
     * This fades the explosion.
     * @param now System time in milliseconds used to calculate the remaining
     * burn time and current size
     */
    public void fade(long now) {
        double factor = ((double)(now-burnFrom) / burnTime);
        this.setFill(Color.rgb(255, 255, 255, Math.max(1.0-factor,0.0)));
        double scale = 1.0+(factor*0.33);
        this.radius = radiusOriginal*scale;
        this.setScaleX(scale);
        this.setScaleY(scale);
    }

    /**
     * This checks whether the explosion has already faded.
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
