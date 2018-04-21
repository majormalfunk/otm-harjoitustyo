/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

/**
 *
 * @author jaakkovilenius
 */
public class CityDestruction extends Arc {

    private long burnFrom;
    private long burnUntil;
    private long burnTime;
    private int r;
    private int g;
    private int b;

    public CityDestruction(double centerX, double centerY, double radius, long now, long burnTime) {
        
        this.burnFrom = now;
        this.burnUntil = (long) (now + burnTime);
        this.burnTime = burnTime;

        this.setLayoutX(centerX);
        this.setLayoutY(centerY);
        
        this.setRadiusX(radius);
        this.setRadiusY(radius);
        this.setStartAngle(0.0);
        this.setLength(180.0);

        //Setting the type of the arc 
        this.setType(ArcType.ROUND);
        r = 255;
        g = 160;
        b = 0;
        this.setFill(Color.rgb(r, g, b, 1.0));
        
        this.setId("City destruction");
    }

    /**
     * This fades the explosion.
     *
     * @param now System time in milliseconds used to calculate the remaining
     * burn time and current size
     */
    public void fade(long now) {
        double factor = ((double) (now - burnFrom) / (double) burnTime);
        this.setFill(Color.rgb(r, g, b, Math.max(1.0 - factor, 0.0)));
        double scale = 1.0 + (factor * 0.33);
        this.setScaleX(scale);
        this.setScaleY(scale);
        this.setLayoutY(getLayoutY()*0.9999);
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
