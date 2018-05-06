/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import mizzilekommand.logics.SceneController;
import mizzilekommand.logics.GameStatus;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;

/**
 * This is the scene that is displayed when the player reaches a
 * top score
 *
 * @author jaakkovilenius
 */
public class TopScoreScene extends SceneTemplate {

    private MKButton btnSave;
    private Label initial1;
    private Label initial2;
    private Label initial3;
    private int focus;

    public TopScoreScene(SceneController controller, GameStatus status) {

        super(controller, status);

        addBases(status.baseOk);
        addCities(status.cityOk);

        addTitle();
        setUpInitials();
        setUpButton();

        showScoreCounter(status.score);
        showLevelIndicator(status.level);

        focus = 1;

        this.setOnKeyPressed(event -> {
            keyDown(event.getCode());
        });

    }

    private void addTitle() {
        Label title = new Label("TOP KÖMMÄNDER!");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40.0));
        title.setTextFill(Color.SILVER);
        title.setTextAlignment(TextAlignment.CENTER);
        this.root.getChildren().add(title);
        title.layoutXProperty().bind(this.widthProperty().subtract(title.widthProperty()).divide(2));
        title.setLayoutY((APP_HEIGHT / 8.0));
    }
    
    private void setUpInitials() {
        initial1 = new Label();
        setUpInitial(initial1, -1);
        initial2 = new Label();
        setUpInitial(initial2, 0);
        initial3 = new Label();
        setUpInitial(initial3, 1);
    }
    
    private void setUpInitial(Label initial, int position) {
        initial.setText("_");
        initial.setFont(Font.font("Arial", FontWeight.BOLD, 40.0));
        initial.setTextFill(Color.SILVER);
        initial.setTextAlignment(TextAlignment.CENTER);
        this.root.getChildren().add(initial);
        initial.layoutXProperty().bind(this.widthProperty().add(position*75).subtract(initial.widthProperty()).divide(2));
        initial.setLayoutY((APP_HEIGHT / 3));
    }

    private void keyDown(KeyCode key) {

        if (key == KeyCode.TAB || key == KeyCode.ENTER) {
            // Do nothing. This here just so that we can use isLetterKey() later
            // and so have a shorter list of conditions.
            // We could attach a fart sound to this.
        } else if (key == KeyCode.BACK_SPACE) {
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

        btnSave = new MKButton();
        btnSave.setText("OK");
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String initials = initial1.getText()+initial2.getText()+initial3.getText();
                status.recordCurrentScore(initials);
                getController().chooseNextScene(SceneController.Actions.SCORESAVED);
                getController().applyNextScene();
            }
        });

        this.root.getChildren().add(btnSave);
        btnSave.setLayoutX((APP_WIDTH / 2) - 50);
        btnSave.setLayoutY((APP_HEIGHT / 3) * 2);

    }

}
