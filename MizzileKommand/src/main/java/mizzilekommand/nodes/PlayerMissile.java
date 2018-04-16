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

    public PlayerMissile(long id) {
        super(id);

        this.setFill(Color.LIGHTCYAN);
        this.setStroke(Color.CORNFLOWERBLUE);

    }

}
