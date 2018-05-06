/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author jaakkovilenius
 */
public class MKButton extends Button{
    
    public static final String BUTTON_STYLE
            = "-fx-background-color: linear-gradient(#ff6000, #c01800); "
            + "-fx-background-radius: 30; "
            + "-fx-background-insets: 0; "
            + "-fx-text-fill: white; "
            + "-fx-font-weight: bold ";

    public MKButton() {
        super();
        
        this.setDefaultButton(true);
        this.setStyle(BUTTON_STYLE);
        this.setMinSize(100, 50);
        this.setPrefSize(100, 50);
        this.setMaxSize(100, 50);
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
            }
        });

    }
    
    
}
