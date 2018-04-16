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
 * Class representing enemymissiles. We should create a common abtract class for
 * player and enemy classes to be inherited.
 *
 * @author jaakkovilenius
 */
public class Missile extends Polygon {

    private final double width;
    private final double height;
    private Point2D direction;

    public Missile(long id) {

        this.width = APP_HEIGHT / 240.0;
        this.height = APP_HEIGHT / 40.0;
        this.direction = new Point2D(0, 1);

        this.setId("MISSILE" + id);
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

    /**
     * This method moves the missile to the direction given in the direction
     * attribute.
     */
    public void fly() {
        setLayoutX(getLayoutX() + getTranslateX() + direction.getX());
        setLayoutY(getLayoutY() + getTranslateY() + direction.getY());
    }

    /**
     * This method returns an explosion at the missile location with an initial
     * burn radius of 3 * the base radius. This is supposed to be called when
     * the missile detonates.
     *
     * @return Explosion
     */
    public Explosion detonate() {
        this.setFill(Color.TRANSPARENT);
        return new EnemyMissileExplosion(getLayoutX(), getLayoutY(), BASE_RADIUS * 3.0, System.currentTimeMillis());
    }

}
