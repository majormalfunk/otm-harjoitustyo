/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import javafx.scene.paint.Color;

/**
 *
 * @author jaakkovilenius
 */
public class PlayerMissile extends Missile {

    public PlayerMissile(long id, double tgtX, double tgtY) {
        super(id, 0, -1, tgtX, tgtY); // 0, -1 = up

        this.setFill(Color.LIGHTCYAN);
        this.setStroke(Color.CORNFLOWERBLUE);

    }

}
