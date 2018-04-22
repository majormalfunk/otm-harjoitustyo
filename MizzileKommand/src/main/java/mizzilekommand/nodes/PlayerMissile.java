/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

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

}
