/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import mizzilekommand.dao.FilePropertiesHandler;
import mizzilekommand.dao.FileStatisticDao;

/**
 * MIZZILE KÖMMÄND Application
 * The Main class of the game.
 *
 * @author jaakkovilenius
 */
public class MizzileKommand extends Application {

    // These constants define the game window size
    // Most other measurements are based in proportion to these
    public static final double APP_WIDTH = 640.0;
    public static final double APP_HEIGHT = 480.0;
    public static final double BASE_RADIUS = APP_WIDTH / 48.0;
    public static final double CITY_WIDTH = APP_WIDTH / 12.0;
    public static final double CITY_HEIGHT = APP_HEIGHT / 12.0;
    public static final double SMALL_LENGTH = APP_WIDTH / 48.0;
    public static final double GROUND_LEVEL = 450.0;

    public static final double[] BASE_X = {
        (BASE_RADIUS * 3.0), (APP_WIDTH / 2.0), APP_WIDTH - (BASE_RADIUS * 3.0)};
    public static final double[] CITY_X = {
        (BASE_X[0] + ((BASE_X[1] - BASE_X[0]) / 4.0)), 
        (BASE_X[0] + ((BASE_X[1] - BASE_X[0]) / 2.0)), 
        (BASE_X[1] - ((BASE_X[1] - BASE_X[0]) / 4.0)), 
        (BASE_X[1] + ((BASE_X[1] - BASE_X[0]) / 4.0)), 
        (BASE_X[1] + ((BASE_X[2] - BASE_X[1]) / 2.0)), 
        (BASE_X[2] - ((BASE_X[1] - BASE_X[0]) / 4.0))
    };
    public static final double BASE_Y = APP_HEIGHT - (SMALL_LENGTH * 4.0);

    public static final String SCORE = "SCORE";
    public static final String LEVEL = "LEVEL";
    public static final String INCOMING = "INCOMING";
    
    public static final int MISSILE_BONUS = 5;
    public static final int CITY_BONUS = 50;
    public static final int MAX_BONUS_LEVEL_FACTOR = 6;
    
    private SceneController scnController;
    
    private Properties config;
    
    private Clip ominousBackgroundSound;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setMaxWidth(APP_WIDTH);
        primaryStage.setMinWidth(APP_WIDTH);
        primaryStage.setMaxHeight(APP_HEIGHT);
        primaryStage.setMinHeight(APP_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mizzile Kömmänd");

        scnController = new SceneController(primaryStage);
        config = loadConfigProperties();
        FileStatisticDao statDao = loadFileStatisticDao();
        GameLoop gameloop = new GameLoop();
        gameloop.setStatisticDao(statDao);
        gameloop.setSceneController(scnController);
        scnController.setGameLoop(gameloop);
        scnController.applyFirstScene();

        loadOminousBackgroundSound();
        playOminousBackgroundSound();

    }
    
    private Properties loadConfigProperties() {
        FilePropertiesHandler fpLoader = new FilePropertiesHandler();
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("HighScoreFile", "highscores.txt");
        return fpLoader.loadOrCreateProperties("config.properties", defaultProperties);
    }
    
    private FileStatisticDao loadFileStatisticDao() {
        try {
            return new FileStatisticDao(config.getProperty("HighScoreFile"));
        } catch (Exception daoException) {
            return null;
        }
    }
    
    /**
     * This loads the ominous backgroundsounds
     */
    private void loadOminousBackgroundSound() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("MizzileKommand.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            ominousBackgroundSound = AudioSystem.getClip();
            ominousBackgroundSound.open(sound);
            FloatControl control = (FloatControl) ominousBackgroundSound.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(control.getMinimum() * (15.0f / 100.0f));
        } catch (Exception e) {
            // Do nothing
        }
    }
    
    /**
     * This starts the playback of the ominous background sounds
     */
    public void playOminousBackgroundSound() {
        try {
            ominousBackgroundSound.start();
            ominousBackgroundSound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            // Do nothing
        }
    }

    /**
     * This is the main method that starts the game
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
