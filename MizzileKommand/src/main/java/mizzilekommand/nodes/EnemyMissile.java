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
public class EnemyMissile extends Missile {

    public EnemyMissile(long id, double tgtX, double tgtY) {
        super(id, 0, 1, tgtX, tgtY); // 0, 1 = down

        this.setFill(Color.PINK);
        this.setStroke(Color.CRIMSON);

    }

}
