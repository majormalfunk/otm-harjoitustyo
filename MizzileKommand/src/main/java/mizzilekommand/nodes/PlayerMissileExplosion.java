/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

/**
 *
 * @author jaakkovilenius
 */
public class PlayerMissileExplosion extends Explosion {

    public PlayerMissileExplosion(double centerX, double centerY, double radius, long now) {
        super(centerX, centerY, radius, now, 3000l);
    }
    
}
