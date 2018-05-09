/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import static mizzilekommand.logics.MizzileKommand.BASE_RADIUS;

/**
 * The missile explosion of the player's missiles
 * @author jaakkovilenius
 */
public class PlayerMissileExplosion extends Explosion {

    public final static long PLAYER_EXPLOSION_BURNTIME = 2000l;
    public final static double PLAYER_EXPLOSION_RADIUS = BASE_RADIUS * 3.0;

    private static final String PLAYER_EXPLOSION_STYLE
            = "-fx-fill: "
            + "radial-gradient(focus-distance 0% , center 50% 50% , radius 67% , white 33%, #ffa500 75%, transparent 95%) ";

    
    public PlayerMissileExplosion(double centerX, double centerY, double radius, long now) {
        super(centerX, centerY, PLAYER_EXPLOSION_RADIUS, now, PLAYER_EXPLOSION_BURNTIME, PLAYER_EXPLOSION_STYLE);
    }

}
