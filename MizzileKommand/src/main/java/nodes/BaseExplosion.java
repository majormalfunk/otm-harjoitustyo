/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package nodes;

/**
 * This extends the explosion class. Here we have a hardcoded value for burn time.
 * We should probably make it a constant and define it at a convenient place.
 * @author jaakkovilenius
 */
public class BaseExplosion extends Explosion {
    
    public BaseExplosion(double centerX, double centerY, double radius, long now) {
        super(centerX, centerY, radius, now, 3000l);
    }
    
}
