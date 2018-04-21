/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import static mizzilekommand.logics.MizzileKommand.CITY_WIDTH;
import static mizzilekommand.logics.MizzileKommand.GROUND_LEVEL;
import static mizzilekommand.logics.MizzileKommand.SMALL_LENGTH;

/**
 *
 * @author jaakkovilenius
 */
public class City extends Polygon {

    public double width;
    public double height;
    public int id;

    public City() {

        this.width = CITY_WIDTH;
        this.height = SMALL_LENGTH * 2.0;
        this.setId("CITY");
        this.getPoints().addAll(
                0.0, this.height, // Lower left corner
                this.width, this.height, // Lower right corner
                this.width, 0.0, // Upper right corner
                0.0, 0.0 // Upper left corner
        );
        this.setFill(Color.GRAY);

    }

    /**
     * This method returns an explosion at the city location with an initial
     * burn radius of 4 * the city height. This is supposed to be called when
     * the city destructs.
     *
     * @return
     */
    public CityDestruction destruct() {
        this.setFill(Color.TRANSPARENT);
        return new CityDestruction(getLayoutX(), (getLayoutY() + (this.height)),
                this.height * 2.0, System.currentTimeMillis(), 3000l);
    }

}
