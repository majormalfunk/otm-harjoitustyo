/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import javafx.scene.image.Image;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This is a static class used for file operations
 * 
 * 
 * @author jaakkovilenius
 */
public class FileHandler {

    
    /**
     * This loads properties from a given property file. If the the required file doesn't exist
     * it creates the file with default properties.
     * 
     * @param filename name of the file
     * @param defaultProperties default properties to be used
     * @return the saved or loaded properties
     */
    public static Properties loadOrCreateProperties(String filename, Properties defaultProperties) {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filename));
        } catch (Exception loadException) {
            try {
                FileOutputStream out = new FileOutputStream(filename);
                if (defaultProperties != null) {
                    defaultProperties.store(out, null);
                }
                out.close();
                properties.load(new FileInputStream(filename));
            } catch (Exception writeException) {
                // Do nothing
                return null;
            }
        }
        return properties;
    }

    /**
     * This stores the give properties in the file named in the parameters
     * @param filename the file name
     * @param properties the properties
     * @return true if writing to the file was successful
     */
    public static boolean storeProperties(String filename, Properties properties) {

        try {
            FileOutputStream out = new FileOutputStream(filename);
            if (properties != null) {
                properties.store(out, null);
            }
            out.close();
            return true;
        } catch (Exception e) {
            // Do nothing
            return false;
        }

    }
    
    /**
     * This loads a sound file to a audio Clip from the resources folder
     * @param filename
     * @return Audio Clip
     */
    public static Clip loadSoundFromResourceFile(String filename) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(filename);
            AudioInputStream sound = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            Clip audioclip = AudioSystem.getClip();
            audioclip.open(sound);
            return audioclip;

        } catch (Exception e) {
            // Do nothing
            return null;
        }
    }
    
    /**
     * This loads an image from a resource file from the resources folder.
     * @param filename
     * @return The image
     */
    public static Image loadImageFromResourceFile(String filename) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream(filename);
            return new Image(is);
        } catch (Exception e) {
            return null;
        }
    
    }
    

}
