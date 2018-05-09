/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import static mizzilekommand.logics.MizzileKommand.BASE_RADIUS;

/**
 * This extends the explosion class to represent enemy missile explosion.
 *
 * @author jaakkovilenius
 */
public class EnemyMissileExplosion extends Explosion {

    public final static long ENEMY_EXPLOSION_BURNTIME = 2000l;
    public final static double ENEMY_EXPLOSION_RADIUS = BASE_RADIUS * 2.3;

    private static final String ENEMY_EXPLOSION_STYLE
            = "-fx-fill: "
            + "radial-gradient(focus-distance 0% , center 50% 50% , radius 67% , #fff600 33%, #ff5c00 75%, transparent 95%) ";

    public EnemyMissileExplosion(double centerX, double centerY, long now) {
        super(centerX, centerY, ENEMY_EXPLOSION_RADIUS, now, ENEMY_EXPLOSION_BURNTIME, ENEMY_EXPLOSION_STYLE);
    }

}
