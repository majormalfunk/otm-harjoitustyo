/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import mizzilekommand.logics.SceneController;
import mizzilekommand.dao.FileStatisticDao;
import mizzilekommand.logics.GameStatus;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;

/**
 * Not implemented yet but will be the scene displayed when the player reaches a
 * top score
 *
 * @author jaakkovilenius
 */
public class TopScoreScene extends SceneTemplate {

    private Button btnSave;
    private Label initial1;
    private Label initial2;
    private Label initial3;
    private int focus;
    private GameStatus status;

    public TopScoreScene(SceneController controller, GameStatus status) {
        super(controller);

        this.status = status;

        setUpButton();
        setUpInitials();

        showScoreCounter(status.score);
        showLevelIndicator(status.level);

        focus = 1;

        this.setOnKeyPressed(event -> {
            keyDown(event.getCode());
        });

    }

    private void setUpInitials() {

        initial1 = new Label("_");
        initial2 = new Label("_");
        initial3 = new Label("_");
        initial1.setTextFill(Color.WHITESMOKE);
        initial2.setTextFill(Color.WHITESMOKE);
        initial3.setTextFill(Color.WHITESMOKE);
        initial1.setScaleX(4.0);
        initial1.setScaleY(4.0);
        initial2.setScaleX(4.0);
        initial2.setScaleY(4.0);
        initial3.setScaleX(4.0);
        initial3.setScaleY(4.0);
        initial1.setTextAlignment(TextAlignment.CENTER);
        initial2.setTextAlignment(TextAlignment.CENTER);
        initial3.setTextAlignment(TextAlignment.CENTER);
        this.root.getChildren().addAll(initial1, initial2, initial3);
        initial1.setLayoutX((APP_WIDTH / 2) - 75 - (initial1.getWidth() / 2.0));
        initial1.setLayoutY((APP_HEIGHT / 4));
        initial2.setLayoutX((APP_WIDTH / 2) - (initial2.getWidth() / 2.0));
        initial2.setLayoutY((APP_HEIGHT / 4));
        initial3.setLayoutX((APP_WIDTH / 2) + 75 - (initial3.getWidth() / 2.0));
        initial3.setLayoutY((APP_HEIGHT / 4));

    }

    private void keyDown(KeyCode key) {

        if (key == KeyCode.BACK_SPACE || key == KeyCode.TAB || key == KeyCode.ENTER) {
            if (focus == 3) {
                initial3.setText("_");
                focus = 2;
            } else if (focus == 2) {
                initial3.setText("_");
                initial2.setText("_");
                focus = 1;
            } else {
                initial3.setText("_");
                initial2.setText("_");
                initial1.setText("_");
            }
        } else if (key.isDigitKey() || key.isLetterKey() || key.isWhitespaceKey()) {
            String initial = String.valueOf(key).substring(key.toString().length()-1);
            if (focus == 1) {
                try {
                    initial1.setText(initial);
                    focus = 2;
                } catch (Exception e) {
                    initial1.setText("_");
                }
            } else if (focus == 2) {
                try {
                    initial2.setText(initial);
                    focus = 3;
                } catch (Exception e) {
                    initial2.setText("_");
                }
            } else {
                try {
                    initial3.setText(initial);
                    focus = 3;
                } catch (Exception e) {
                    initial3.setText("_");
                }
            }
        }
    }

    private void setUpButton() {

        btnSave = new Button();
        btnSave.setText("OK");
        btnSave.setMinSize(100, 50);
        btnSave.setPrefSize(100, 50);
        btnSave.setMaxSize(100, 50);
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String initials = initial1.getText()+initial2.getText()+initial3.getText();
                status.recordCurrentScore(initials);
                getController().chooseNextScene(SceneController.Actions.SCORESAVED);
                getController().applyNextScene();
            }
        });
        btnSave.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        btnSave.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
            }
        });

        this.root.getChildren().add(btnSave);
        btnSave.setLayoutX((APP_WIDTH / 2) - 50);
        btnSave.setLayoutY((APP_HEIGHT / 2) - 25);

    }

}
