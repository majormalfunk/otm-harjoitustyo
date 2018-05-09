/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.nodes;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import mizzilekommand.dao.FileHandler;
import static mizzilekommand.logics.MizzileKommand.CITY_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.CITY_WIDTH;

/**
 *
 * @author jaakkovilenius
 */
public class City extends Polygon {

    public double width;
    public double height;
    public int id;

    private static final String CITYLIGHTSFILL = "citylights.png";

    public City(int id) {
        Random pseudo = new Random((id * 137) % 7);
        this.width = CITY_WIDTH;
        this.height = CITY_HEIGHT;
        this.setId("CITY" + id);

        // We bild the cities with pseudo random numbers with a seed so we have same cities
        // at the same places every time but they all look a bit different.
        this.getPoints().addAll(0.0, CITY_HEIGHT, CITY_WIDTH, CITY_HEIGHT);
        double b = 1.0;
        while (b >= 0.0) {
            double h = ((pseudo.nextDouble() * 0.5) + 0.5);
            double w = ((pseudo.nextDouble() * 0.05) + 0.05);
            this.getPoints().addAll(CITY_WIDTH * (1.0 * b), CITY_HEIGHT * (1.0 - h));
            b -= w;
            this.getPoints().addAll(Math.max(CITY_WIDTH * (1.0 * b), 0.0), CITY_HEIGHT * (1.0 - h));
        }
        try {
            Image lights = FileHandler.loadImageFromResourceFile(CITYLIGHTSFILL);
            this.setFill(new ImagePattern(lights, 0.0, 0.0, 12.0, 12.0, false));
        } catch (Exception e) {
            this.setFill(Color.BLACK);
        }

    }

    /**
     * This method returns an explosion at the city location with an initial
     * burn radius of 4 * the city height. This is supposed to be called when
     * the city destructs.
     *
     * @return explosion object
     */
    public CityDestruction destruct() {
        this.setFill(Color.TRANSPARENT);
        return new CityDestruction(getLayoutX() + (this.width / 2.0), (getLayoutY() + (this.height)),
                this.height * 2.0, System.currentTimeMillis(), 3000l);
    }

}
