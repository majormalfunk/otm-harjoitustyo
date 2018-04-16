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

    public EnemyMissile(long id) {
        super(id);

        this.setFill(Color.PINK);
        this.setStroke(Color.CRIMSON);

    }

}
