/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import javafx.scene.paint.Color;
import static mizzilekommand.logics.MizzileKommand.BASE_RADIUS;

/**
 *
 * @author jaakkovilenius
 */
public class EnemyMissile extends Missile {

    /**
     * 
     * @param id
     * @param boost
     * @param srcX The X-coordinate where the missile originates
     * @param tgtX The X-coordinate where the missile is targeting
     * @param tgtY The Y-coordinate where the missile is targeting
     */
    public EnemyMissile(long id, double boost, double srcX, double tgtX, double tgtY) {
        super(id, boost, 0, 1, tgtX, tgtY); // 0, 1 = down

        this.setFill(Color.PINK);
        this.setStroke(Color.CRIMSON);

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
        return new EnemyMissileExplosion(getLayoutX(), getLayoutY(), BASE_RADIUS * 3.0, System.currentTimeMillis());
    }

}
