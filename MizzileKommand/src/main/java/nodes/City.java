/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package nodes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import static mizzilekommand.MizzileKommand.CITY_WIDTH;
import static mizzilekommand.MizzileKommand.SMALL_LENGTH;

/**
 *
 * @author jaakkovilenius
 */
public class City extends Polygon {

    public double width;
    public double height;
    public int id;
    
    public City () {
        
        this.width = CITY_WIDTH;
        this.height = SMALL_LENGTH*2.0;
        this.setId("CITY");
        this.getPoints().addAll(
                0.0, this.height, // Lower left corner
                this.width, this.height, // Lower right corner
                this.width, 0.0, // Upper right corner
                0.0, 0.0 // Upper left corner
        );
        this.setFill(Color.GRAY);
        
    }
    
    
    
}
