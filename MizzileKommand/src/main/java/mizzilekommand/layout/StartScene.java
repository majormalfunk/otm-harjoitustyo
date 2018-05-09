/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import java.util.List;
import javafx.animation.AnimationTimer;
import mizzilekommand.logics.SceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import mizzilekommand.logics.GameStatus;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import mizzilekommand.logics.SceneController.Actions;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import mizzilekommand.logics.Statistic;

/**
 * This is the Starting scene for the game.
 *
 * @author jaakkovilenius
 */
public class StartScene extends SceneTemplate {

    MKButton btnPlay;
    private Label title;
    private Label highScoreTitle;
    private List<Statistic> highScores;
    private int content;

    private GridPane highScoreGrid;

    private final static String GRID_STYLE
            = "-fx-background-color: transparent; ";
    private final static String GRID_ROW_STYLE
            = "-fx-background-color: transparent; "
            + "-fx-text-fill: white; "
            + "-fx-font-weight: bold; "
            + "-fx-max-width: 70; "
            + "-fx-pref-width: 70; "
            + "-fx-min-width: 70";

    private final static String[] HIGH_SCORE_HEADERS = {"RANK", "PLAYER", "SCORE", "LEVEL", "INT."};
    private final static Pos[] HIGH_SCORE_ALIGN
            = {Pos.CENTER_LEFT, Pos.CENTER, Pos.CENTER_RIGHT, Pos.CENTER_RIGHT, Pos.CENTER_RIGHT};

    /**
     * Constructor
     *
     * @param controller Reference to the SceneController object
     * @param status Reference to the GameStatus object
     */
    public StartScene(SceneController controller, GameStatus status) {

        super(controller, status);

        addBases(status.baseOk);
        addCities(status.cityOk);

        hideLevelIndicator();

        setUpButton();
        addInstructions();

        makeTitle();
        makeHighScoreTitle();

        content = 0;
        showTitle();
        if (status.highScoresAvailable) {
            loadHighScores();
            runTeaser();
        }

    }

    /**
     * This runs the animation that changes between the title and the high score table
     */
    private void runTeaser() {

        try {

            new AnimationTimer() {
                long timer = System.currentTimeMillis() + 5000l;

                @Override
                public void handle(long now) {

                    timer = handleTeaserSwitching(timer);

                }

            }.start();

        } catch (Exception ex) {
            // Do nothing
        }

    }
    
    /**
     * This handles the switching between the settings
     * @param timer
     * @return value of the timer after the evaluation
     */
    private long handleTeaserSwitching(long timer) {
        switch (content) {
            case 0:
                if (timer > System.currentTimeMillis()) {
                    fadeTitle((double) (timer - System.currentTimeMillis()) / (double) 5000.0);
                } else {
                    timer = System.currentTimeMillis() + 5000l;
                    switchToHighScoreTable();
                }
                break;
            case 1:
                if (timer < System.currentTimeMillis()) {
                    timer = System.currentTimeMillis() + 5000l;
                    switchToMainTitle();
                }
                break;
        }
        return timer;
    }
    
    /**
     * This switches to the main title
     */
    private void switchToMainTitle() {
        content = 0;
        hideHighScoreTitle();
        hideHighScores();
        showTitle();
    }

    /**
     * This switches to the high score table
     */
    private void switchToHighScoreTable() {
        content = 1;
        showHighScoreTitle();
        showHighScores();
    }

    /**
     * This makes the title
     */
    private void makeTitle() {
        title = new Label("MIZZILE\nKÖMMÄND");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 60.0));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTextFill(Color.TRANSPARENT);
        this.root.getChildren().add(title);
    }

    /**
     * This shows the title
     */
    private void showTitle() {
        title.setTextFill(Color.SILVER);
        title.layoutXProperty().bind(this.widthProperty().subtract(title.widthProperty()).divide(2));
        title.layoutYProperty().bind((this.heightProperty().divide(3)).subtract(title.heightProperty().divide(2)));
    }

    /**
     * This fades the title
     * @param factor the factor to which to fade
     */
    private void fadeTitle(double factor) {
        title.setOpacity(Math.max(factor, 0.0));
    }

    /**
     * This makes the high score title
     */
    private void makeHighScoreTitle() {
        highScoreTitle = new Label("TOP KÖMMÄNDERS");
        highScoreTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30.0));
        highScoreTitle.setTextAlignment(TextAlignment.CENTER);
        highScoreTitle.setTextFill(Color.SILVER);
        this.root.getChildren().add(highScoreTitle);
        highScoreTitle.layoutXProperty().bind(this.widthProperty().subtract(highScoreTitle.widthProperty()).divide(2));
        highScoreTitle.layoutYProperty().bind((this.heightProperty().divide(7)).subtract(highScoreTitle.heightProperty().divide(2)));
        highScoreTitle.setVisible(false);
    }

    /**
     * This shows the high score title
     */
    private void showHighScoreTitle() {
        highScoreTitle.setVisible(true);
    }

    /**
     * This hides the high score title
     */
    private void hideHighScoreTitle() {
        highScoreTitle.setVisible(false);
    }

    /**
     * This loads the high scores
     */
    private void loadHighScores() {
        highScores = status.getStatistics();

        highScoreGrid = new GridPane();
        highScoreGrid.setStyle(GRID_STYLE);

        for (int i = -1; i < highScores.size(); i++) {
            if (i == -1) {
                loadHighScoreTableHeaders();
            } else {
                loadHighScoreTableRows(i);
            }
        }

        this.root.getChildren().add(highScoreGrid);
        highScoreGrid.layoutXProperty().bind(this.widthProperty().subtract(highScoreGrid.widthProperty()).divide(2));
        highScoreGrid.layoutYProperty().bind(this.heightProperty().divide(5));
        highScoreGrid.setVisible(false);
    }

    /**
     * This loads the high score table headers
     */
    private void loadHighScoreTableHeaders() {
        for (int h = 0; h < HIGH_SCORE_HEADERS.length; h++) {
            Label header = new Label(HIGH_SCORE_HEADERS[h]);
            header.setStyle(GRID_ROW_STYLE);
            highScoreGrid.setConstraints(header, h, 0);
            highScoreGrid.setFillWidth(header, true);
            header.setMaxWidth(Double.MAX_VALUE);
            header.setAlignment(HIGH_SCORE_ALIGN[h]);
            highScoreGrid.getChildren().add(header);
        }
    }
    
    /**
     * This loads the high score table rows
     * @param row 
     */
    private void loadHighScoreTableRows(int row) {
        String[] stats = highScores.get(row).toStringArray();
        for (int s = 0; s < stats.length; s++) {
            Label stat = new Label(stats[s]);
            stat.setStyle(GRID_ROW_STYLE);
            highScoreGrid.setConstraints(stat, s, row + 1);
            highScoreGrid.setFillWidth(stat, true);
            stat.setMaxWidth(Double.MAX_VALUE);
            stat.setAlignment(HIGH_SCORE_ALIGN[s]);
            highScoreGrid.getChildren().add(stat);
        }
    }
    
    /**
     * This shows the high scores
     */
    private void showHighScores() {
        highScoreGrid.setVisible(true);
    }

    /**
     * This hides the high scores
     */
    private void hideHighScores() {
        highScoreGrid.setVisible(false);
    }

    /**
     * This makes the PLAY button
     */
    private void setUpButton() {

        btnPlay = new MKButton();
        btnPlay.setText("PLAY!");
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getController().chooseNextScene(Actions.PLAY);
                getController().applyNextScene();
            }
        });
        this.root.getChildren().add(btnPlay);

        btnPlay.setLayoutX((APP_WIDTH / 2) - 50);
        btnPlay.setLayoutY(APP_HEIGHT * (5.0 / 8.0));

    }

    /**
     * This adds instructions to the scene
     */
    private void addInstructions() {

        Label keys = new Label();
        keys.setTextFill(Color.WHITESMOKE);
        keys.setTextAlignment(TextAlignment.CENTER);
        String guide = "POINT WITH MOUSE. FIRE MISSILE FROM BASE:\n"
                + "1/\u21E6 Left Base   2/\u21E7/\u21E9 Center Base    3/\u21E8 Right Base";
        keys.setText(guide);
        this.root.getChildren().add(keys);
        keys.setMinWidth(200);
        keys.setLayoutY(APP_HEIGHT * (6.0 / 8.0));
        keys.layoutXProperty().bind(this.widthProperty().subtract(keys.widthProperty()).divide(2));

    }

}
