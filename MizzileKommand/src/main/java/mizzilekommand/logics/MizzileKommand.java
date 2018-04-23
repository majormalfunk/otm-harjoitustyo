/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

import javafx.application.Application;
//import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

/**
 * MIZZILE KÖMMÄND Application
 *
 * @author jaakkovilenius
 */
public class MizzileKommand extends Application {

    // These constants define the game window size
    // Most other measurements are based in proportion to these
    public static final double APP_WIDTH = 640.0;
    public static final double APP_HEIGHT = 480.0;
    public static final double BASE_RADIUS = APP_WIDTH / 48.0;
    public static final double CITY_WIDTH = APP_WIDTH / 32.0;
    public static final double SMALL_LENGTH = APP_WIDTH / 48.0;
    public static final double GROUND_LEVEL = 450.0;

    public static final double[] BASE_X = {
        (BASE_RADIUS * 3.0), (APP_WIDTH / 2.0), APP_WIDTH - (BASE_RADIUS * 3.0)};
    public static final double BASE_Y = APP_HEIGHT - (SMALL_LENGTH * 4.0);

    private SceneController scnController;

    //AudioClip muzak = new AudioClip("file:MizzileKommand.m4a");

    @Override
    public void start(Stage primaryStage) {

        //muzak.setVolume(0.10);
        //muzak.setCycleCount(-1); // Looped
        //muzak.play();

        primaryStage.setMaxWidth(APP_WIDTH);
        primaryStage.setMinWidth(APP_WIDTH);
        primaryStage.setMaxHeight(APP_HEIGHT);
        primaryStage.setMinHeight(APP_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mizzile Kömmänd");

        scnController = new SceneController(primaryStage);
        scnController.applyFirstScene();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
