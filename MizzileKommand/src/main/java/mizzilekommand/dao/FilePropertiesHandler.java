/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author jaakkovilenius
 */
public class FilePropertiesHandler {

    public Properties loadOrCreateProperties(String filename, Properties defaultProperties) {

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
                System.out.println("The file system could not be read or written to");
                return null;
            }
        }
        return properties;
    }

    public boolean storeProperties(String filename, Properties properties) {

        try {
            FileOutputStream out = new FileOutputStream(filename);
            if (properties != null) {
                properties.store(out, null);
            }
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println("The file system could not be written to");
            return false;
        }

    }

}
