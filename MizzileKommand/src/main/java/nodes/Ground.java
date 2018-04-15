/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package nodes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import static mizzilekommand.MizzileKommand.APP_WIDTH;
import static mizzilekommand.MizzileKommand.SMALL_LENGTH;

/**
 * This represents the ground in the bottom of the screen.
 * @author jaakkovilenius
 */
public class Ground extends Polygon {

    public Ground() {

        //APP_WIDTH, (SMALL_LENGTH*2)
        this.setId("GROUND");
        this.getPoints().addAll(
                0.0, SMALL_LENGTH*2.0, // Lower left corner
                APP_WIDTH, SMALL_LENGTH*2.0, // Lower right corner
                APP_WIDTH, SMALL_LENGTH, // Ground level right edge
                APP_WIDTH-(SMALL_LENGTH*1.0), SMALL_LENGTH, // Right mound 
                APP_WIDTH-(SMALL_LENGTH*2.0), 0.0,
                APP_WIDTH-(SMALL_LENGTH*4.0), 0.0,
                APP_WIDTH-(SMALL_LENGTH*5.0), SMALL_LENGTH,
                (APP_WIDTH/2.0)+(SMALL_LENGTH*2.0), SMALL_LENGTH, // Center mound 
                (APP_WIDTH/2.0)+(SMALL_LENGTH*1.0), 0.0,
                (APP_WIDTH/2.0)-(SMALL_LENGTH*1.0), 0.0,
                (APP_WIDTH/2.0)-(SMALL_LENGTH*2.2), SMALL_LENGTH,
                (SMALL_LENGTH*5.0), SMALL_LENGTH, // Left mound
                (SMALL_LENGTH*4.0), 0.0,
                (SMALL_LENGTH*2.0), 0.0,
                (SMALL_LENGTH*1.0), SMALL_LENGTH, 
                0.0, SMALL_LENGTH // Ground level left edge
        );
        this.setFill(Color.SANDYBROWN);

    }

}
