/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import static mizzilekommand.logics.MizzileKommand.BASE_RADIUS;

/**
 * This is a class representing player bases
 *
 * @author jaakkovilenius
 */
public class Base extends Arc {

    public int id;

    public Base() {

        this.setRadiusX(BASE_RADIUS);
        this.setRadiusY(BASE_RADIUS);
        this.setStartAngle(0.0);
        this.setLength(180.0);

        //Setting the type of the arc 
        this.setType(ArcType.ROUND);
        this.setFill(Color.STEELBLUE);

    }

    /**
     * This is a convenience method.
     *
     * @return returns the base dome height which is the same as the base dome
     * radius.
     */
    public double getBaseHeight() {
        return BASE_RADIUS;
    }

    /**
     * This is a convenience method.
     *
     * @return returns the base dome width which is the same as 2 * base dome
     * radius.
     */
    public double getBaseWidth() {
        return BASE_RADIUS * 2.0;
    }

    /**
     * This method returns an explosion at the base location with an initial
     * burn radius of 4 * the base radius. This is supposed to be called when
     * the base detonates.
     *
     * @return
     */
    public Explosion detonate() {
        this.setFill(Color.TRANSPARENT);
        return new BaseExplosion(getLayoutX(), getLayoutY(), BASE_RADIUS * 4.0, System.currentTimeMillis());
    }

}
