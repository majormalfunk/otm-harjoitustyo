/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package nodes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import static mizzilekommand.MizzileKommand.BASE_RADIUS;

/**
 * This represents the ground in the bottom of the screen.
 * @author jaakkovilenius
 */
public class Ground extends Polygon {

    private double width;
    private double height;

    public Ground(double width, double height) {

        this.width = width;
        this.height = height;
        double groundLevel = this.height*0.25;
        //System.out.println("groundHeight: " + this.height);
        //System.out.println("groundLevel : " + groundLevel);
        //double domeWidth = (2.0/48.0)*this.width;
        this.setId("GROUND");
        this.getPoints().addAll(
                0.0, this.height, // Lower left corner
                this.width, this.height, // Lower right corner
                this.width, groundLevel, // Ground level right edge
                this.width-(BASE_RADIUS*1.0), groundLevel, // Right mound 
                this.width-(BASE_RADIUS*2.0), 0.0,
                this.width-(BASE_RADIUS*4.0), 0.0,
                this.width-(BASE_RADIUS*5.0), groundLevel,
                (this.width/2.0)+(BASE_RADIUS*2.0), groundLevel, // Center mound 
                (this.width/2.0)+(BASE_RADIUS*1.0), 0.0,
                (this.width/2.0)-(BASE_RADIUS*1.0), 0.0,
                (this.width/2.0)-(BASE_RADIUS*2.2), groundLevel,
                (BASE_RADIUS*5.0), groundLevel, // Left mound
                (BASE_RADIUS*4.0), 0.0,
                (BASE_RADIUS*2.0), 0.0,
                (BASE_RADIUS*1.0), groundLevel, 
                0.0, groundLevel // Ground level left edge
        );
        this.setFill(Color.SANDYBROWN);

    }

}
