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
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    
    private GridPane highScoreGrid;
    
    private final static String GRID_STYLE
            = "-fx-background-color: transparent; ";
    private final static String GRID_ROW_STYLE
            = "-fx-background-color: transparent; "
            + "-fx-text-fill: white; "
            + "-fx-font-weight: bold; "
            + "-fx-max-width: 80; "
            + "-fx-pref-width: 80; "
            + "-fx-min-width: 80";

    private final static String[] highScoreHeaders = {"RANK", "PLAYER", "SCORE", "LEVEL", "HITS"};
    private final static Pos[] highScoreAlignment
            = {Pos.CENTER_LEFT, Pos.CENTER, Pos.CENTER_RIGHT, Pos.CENTER_RIGHT, Pos.CENTER_RIGHT};
    
    /**
     * Constructor
     *
     * @param controller
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
        
        if (status.highScoresAvailable) {
            loadHighScores();
            runTeaser();
        } else {
            showTitle();
        }
        

    }

    private void runTeaser() {

        showTitle();
        try {

            new AnimationTimer() {
                int bonusCounter = 0;
                int content = 0;
                long tease = System.currentTimeMillis() + 5000l;

                @Override
                public void handle(long now) {

                    switch (content) {
                        case 0:
                            if (tease > System.currentTimeMillis()) {
                                fadeTitle((double) (tease - System.currentTimeMillis()) / (double) 5000.0);
                            } else {
                                tease = System.currentTimeMillis() + 5000l;
                                content = 1;
                                showHighScoreTitle();
                                showHighScores();
                            }
                            break;
                        case 1:
                            if (tease < System.currentTimeMillis()) {
                                tease = System.currentTimeMillis() + 5000l;
                                content = 0;
                                hideHighScoreTitle();
                                hideHighScores();
                                showTitle();
                            }
                            break;
                        default:
                            break;
                    }
                }

            }.start();

        } catch (Exception ex) {
            // Do nothing
        }

    }

    private void makeTitle() {
        title = new Label("MIZZILE\nKÖMMÄND");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 60.0));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTextFill(Color.TRANSPARENT);
        this.root.getChildren().add(title);
    }
    
    private void showTitle() {
        title.setTextFill(Color.SILVER);
        title.layoutXProperty().bind(this.widthProperty().subtract(title.widthProperty()).divide(2));
        title.layoutYProperty().bind((this.heightProperty().divide(3)).subtract(title.heightProperty().divide(2)));
    }

    private void fadeTitle(double factor) {
        title.setTextFill(Color.rgb(192, 192, 192, Math.max(factor, 0.0)));
    }

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

    private void showHighScoreTitle() {
        highScoreTitle.setVisible(true);
    }

    private void hideHighScoreTitle() {
        highScoreTitle.setVisible(false);
    }

    private void loadHighScores() {
        highScores = status.getStatistics();
        
        highScoreGrid = new GridPane();
        highScoreGrid.setStyle(GRID_STYLE);
        
        for (int i = -1; i < highScores.size(); i++) {
            if (i == -1) {
                for (int h = 0; h < highScoreHeaders.length; h++) {
                    Label header = new Label(highScoreHeaders[h]);
                    header.setStyle(GRID_ROW_STYLE);
                    highScoreGrid.setConstraints(header, h, 0);//, 1, 1, HPos.RIGHT, VPos.BASELINE);
                    highScoreGrid.setFillWidth(header, true);
                    header.setMaxWidth(Double.MAX_VALUE);
                    header.setAlignment(highScoreAlignment[h]);
                    //highScoreGrid.setHalignment(header, HPos.RIGHT);
                    highScoreGrid.getChildren().add(header);
                }
            } else {
                String[] stats = highScores.get(i).toStringArray();
                for (int s = 0; s < stats.length; s++) {
                    Label stat = new Label(stats[s]);
                    stat.setStyle(GRID_ROW_STYLE);
                    highScoreGrid.setConstraints(stat, s, i + 1);//, 1, 1, HPos.RIGHT, VPos.BASELINE);
                    highScoreGrid.setFillWidth(stat, true);
                    stat.setMaxWidth(Double.MAX_VALUE);
                    stat.setAlignment(highScoreAlignment[s]);
                    //highScoreGrid.setHalignment(stat, HPos.RIGHT);
                    highScoreGrid.getChildren().add(stat);
                }
            }
        }
        
        this.root.getChildren().add(highScoreGrid);
        highScoreGrid.layoutXProperty().bind(this.widthProperty().subtract(highScoreGrid.widthProperty()).divide(2));
        highScoreGrid.layoutYProperty().bind(this.heightProperty().divide(5));
        highScoreGrid.setVisible(false);
    }
    
    private void showHighScores() {
        highScoreGrid.setVisible(true);

    }

    private void hideHighScores() {
        highScoreGrid.setVisible(false);
    }

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
