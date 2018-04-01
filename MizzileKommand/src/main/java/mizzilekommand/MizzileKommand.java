/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * MIZZILE KÖMMÄND Application
 * @author jaakkovilenius
 */
public class MizzileKommand extends Application {

    // These constants define the game window size
    // Most other measurements are based in proportion to these
    public static final double APP_WIDTH = 640.0;
    public static final double APP_HEIGHT = 480.0;
    public static final double BASE_RADIUS = APP_WIDTH/48.0;
    public static final double BASE_Y = APP_HEIGHT*0.9;
    
    private GameLoop gameloop;
    private SceneController scnController;
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setMaxWidth(APP_WIDTH);
        primaryStage.setMinWidth(APP_WIDTH);
        primaryStage.setMaxHeight(APP_HEIGHT);
        primaryStage.setMinHeight(APP_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mizzile Kömmänd");
        
        gameloop = new GameLoop();
        scnController = new SceneController(primaryStage, gameloop);
        scnController.applyFirstScene();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
