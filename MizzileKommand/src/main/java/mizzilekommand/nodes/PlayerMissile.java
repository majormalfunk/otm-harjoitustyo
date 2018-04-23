/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import static mizzilekommand.logics.MizzileKommand.BASE_RADIUS;

/**
 *
 * @author jaakkovilenius
 */
public class PlayerMissile extends Missile {

    public PlayerMissile(long id, double boost, double tgtX, double tgtY) {
        super(id, boost, 0.0, -1.0, tgtX, tgtY); // 0, -1 = up

        this.setFill(Color.LIGHTCYAN);
        this.setStroke(Color.CORNFLOWERBLUE);

    }

    /**
     * This method returns an explosion at the missile location with an initial
     * burn radius of 3 * the base radius. This is supposed to be called when
     * the missile detonates.
     *
     * @return Explosion
     */
    @Override
    public Explosion detonate() {
        this.setFill(Color.TRANSPARENT);
        return new PlayerMissileExplosion(getLayoutX(), getLayoutY(), BASE_RADIUS * 3.0, System.currentTimeMillis());
    }

}
