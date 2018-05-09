/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import static mizzilekommand.logics.MizzileKommand.BASE_RADIUS;

/**
 * This extends the explosion class. Here we have a hardcoded value for burn
 * time. We should probably make it a constant and define it at a convenient
 * place.
 *
 * @author jaakkovilenius
 */
public class BaseExplosion extends Explosion {

    public final static long BASE_EXPLOSION_BURNTIME = 3000l;
    public final static double BASE_EXPLOSION_RADIUS = BASE_RADIUS * 3.0;

    private static final String BASE_EXPLOSION_STYLE
            = "-fx-fill: "
            + "radial-gradient(focus-distance 0% , center 50% 50% , radius 67% , white 33%, #c0c0ff 80%, transparent 95%) ";
    
    public BaseExplosion(double centerX, double centerY, double radius, long now) {
        super(centerX, centerY, BASE_EXPLOSION_RADIUS, now, BASE_EXPLOSION_BURNTIME, BASE_EXPLOSION_STYLE);

        setId("Base explosion");
    }

}
