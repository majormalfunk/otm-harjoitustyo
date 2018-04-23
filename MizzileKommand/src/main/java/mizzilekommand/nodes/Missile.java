/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import static mizzilekommand.logics.MizzileKommand.BASE_RADIUS;

/**
 * Class representing missiles. This is a common abtract class for player and
 * enemy missile classes to inherit.
 *
 * @author jaakkovilenius
 */
public abstract class Missile extends Polygon {

    public final double width;
    public final double height;
    private final double boost;
    public final Point2D target;
    private Point2D direction;

    public Missile(long id, double boost, double dirX, double dirY, double tgtX, double tgtY) {

        this.width = APP_HEIGHT / 240.0;
        this.height = APP_HEIGHT / 40.0;
        this.target = new Point2D(tgtX, tgtY);
        this.direction = new Point2D(dirX, dirY);

        this.setId("MISSILE" + id);
        this.boost = boost;
        this.getPoints().addAll(
                this.width * 0.2, this.height,
                this.width * 0.8, this.height,
                this.width * 0.8, this.height * 0.9,
                this.width, this.height * 0.9,
                this.width * 0.8, this.height * 0.7,
                this.width * 0.8, (this.height * 0.333),
                (this.width * 0.625), (this.height * 0.05),
                (this.width * 0.50), 0.0,
                (this.width * 0.375), (this.height * 0.05),
                this.width * 0.2, (this.height * 0.333),
                this.width * 0.2, this.height * 0.7,
                0.0, this.height * 0.9,
                this.width * 0.2, this.height * 0.9
        );

    }

    public void setDirection() {
        Point2D subs = target.subtract(getLayoutX(), getLayoutY());
        direction = subs.normalize();
        double angle = (new Point2D(0.0, -1.0)).angle(direction.getX(), direction.getY());
        this.setRotate(angle * (direction.getX() < 0 ? -1.0 : 1.0));

    }

    /**
     * This method moves the missile to the direction given in the direction
     * attribute.
     */
    public void fly() {
        setLayoutX(getLayoutX() + getTranslateX() + (direction.getX() * boost));
        setLayoutY(getLayoutY() + getTranslateY() + (direction.getY() * boost));
    }

    /**
     * Returns the missiles target X coordinate.
     *
     * @return The target's x coordinate
     */
    public double getTargetX() {
        return target.getX();
    }

    /**
     * Returns the missiles target Y coordinate.
     *
     * @return The target's y coordinate
     */
    public double getTargetY() {
        return target.getY();
    }

    public boolean isAtTargetHeight() {
        if (direction.getY() > 0.0 && getLayoutY() >= getTargetY()) {
            return true;
        } else if (direction.getY() < 0.0 && getLayoutY() <= getTargetY()) {
            return true;
        }
        return false;
    }

    /**
     * This method returns an explosion at the missile location with an initial
     * burn radius of 3 * the base radius. This is supposed to be called when
     * the missile detonates.
     *
     * @return Explosion
     */
    public abstract Explosion detonate();

}
