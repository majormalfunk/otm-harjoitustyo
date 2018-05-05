/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import mizzilekommand.logics.GameStatus;
import static mizzilekommand.logics.MizzileKommand.APP_HEIGHT;
import static mizzilekommand.logics.MizzileKommand.APP_WIDTH;
import mizzilekommand.logics.SceneController;
import mizzilekommand.nodes.City;
import mizzilekommand.nodes.PlayerMissile;

/**
 * The Bonus scene shown after every successful level in game
 *
 * @author jaakkovilenius
 */
public class BonusScene extends SceneTemplate {

    private Label missileBonusCounter;
    private Label cityBonusCounter;

    public BonusScene(SceneController controller, GameStatus status) {

        super(controller, status);

        addBases(status.baseOk);
        addCities(status.cityOk);

        showScoreCounter(status.score);
        showLevelIndicator(status.level);
        // ADD TOTAL INCOMING DESTROYED

        addTitle();
        addItemsLeftCount(status.playerMissilesLeft(), 0);
        addItemsLeftCount(status.citiesLeft(), 1);
        addMissileImg();
        addCityImg();
        addRewardsSigns();
        missileBonusCounter = new Label("");
        addBonusCounter(missileBonusCounter, 0);
        cityBonusCounter = new Label("");
        addBonusCounter(cityBonusCounter, 1);
        addContinueButton();

    }

    @Override
    public void runActions() {

        this.runBonusCounter(missileBonusCounter, status.playerMissilesLeft(), status.bonusPerMissileLeft());
        status.recordMissileBonus();
        updateScoreCounter(status.score);

        this.runBonusCounter(cityBonusCounter, status.citiesLeft(), status.bonusPerCityLeft());
        status.recordCityBonus();
        updateScoreCounter(status.score);

    }

    private void addTitle() {
        Label title = new Label("BONUS");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40.0));
        title.setTextFill(Color.SILVER);
        title.setTextAlignment(TextAlignment.CENTER);
        this.root.getChildren().add(title);
        title.layoutXProperty().bind(this.widthProperty().subtract(title.widthProperty()).divide(2));
        title.setLayoutY((APP_HEIGHT / 8.0));
    }

    private void addMissileImg() {
        PlayerMissile missile = new PlayerMissile(0l, 0.0, 0.0, 0.0);
        missile.setScaleX(4);
        missile.setScaleY(4);
        missile.setLayoutX((APP_WIDTH * (3.0 / 8.0)) - (missile.width / 2.0));
        missile.setLayoutY((APP_HEIGHT * (2.0 / 6.0)) - (missile.height / 2.0));
        this.root.getChildren().add(missile);
    }

    private void addCityImg() {
        City city = new City(status.cityOk.length); // One more so that it's different from all
        //missile.setScaleX(4);
        //missile.setScaleY(4);
        city.setLayoutX((APP_WIDTH * (3.0 / 8.0)) - (city.width / 2.0));
        city.setLayoutY((APP_HEIGHT * (3.0 / 6.0)) - (city.height / 2.0));
        this.root.getChildren().add(city);
    }

    private void addMissilesLeftCount() {
        Label missiles = new Label("" + status.playerMissilesLeft());
        missiles.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40.0));
        missiles.setTextFill(Color.SILVER);
        missiles.setTextAlignment(TextAlignment.CENTER);
        this.root.getChildren().add(missiles);
        missiles.layoutXProperty().bind(this.widthProperty().subtract(missiles.widthProperty()).divide(4));
        missiles.setLayoutY((APP_HEIGHT * (2.0 / 6.0)) - 20.0);
    }

    private void addCitiesLeftCount() {
        Label cities = new Label("" + status.citiesLeft());
        cities.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40.0));
        cities.setTextFill(Color.SILVER);
        cities.setTextAlignment(TextAlignment.CENTER);
        this.root.getChildren().add(cities);
        cities.layoutXProperty().bind(this.widthProperty().subtract(cities.widthProperty()).divide(4));
        cities.setLayoutY((APP_HEIGHT * (3.0 / 6.0)) - 20.0);
    }
    
    private void addItemsLeftCount(int count, int item) {
        Label counter = new Label("" + count);
        counter.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40.0));
        counter.setTextFill(Color.SILVER);
        counter.setTextAlignment(TextAlignment.CENTER);
        this.root.getChildren().add(counter);
        counter.layoutXProperty().bind(this.widthProperty().subtract(counter.widthProperty()).divide(4));
        counter.setLayoutY((APP_HEIGHT * ((2.0 + item) / 6.0)) - 20.0);
    }

    private void addRewardsSigns() {
        for (int e = 0; e < 2; e++) {
            Label reward = new Label("=");
            reward.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40.0));
            reward.setTextFill(Color.SILVER);
            reward.setTextAlignment(TextAlignment.CENTER);
            this.root.getChildren().add(reward);
            reward.layoutXProperty().bind(this.widthProperty().subtract(reward.widthProperty()).divide(2));
            reward.setLayoutY((APP_HEIGHT * ((2.0 + e) / 6.0)) - 20.0);
        }
    }

    private void addBonusCounter(Label counter, int item){
        counter.setText("");
        counter.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40.0));
        counter.setTextFill(Color.SILVER);
        counter.setTextAlignment(TextAlignment.CENTER);
        this.root.getChildren().add(counter);
        counter.setLayoutX(APP_WIDTH * (5.0 / 8.0));
        counter.setLayoutY((APP_HEIGHT * ((2.0 + item) / 6.0)) - 20.0);
    }
    
    private void runBonusCounter(Label counter, int itemsLeft, int bonusPerItem) {
        try {

            new AnimationTimer() {
                int bonusCounter = 0;
                long upd = System.currentTimeMillis() + 250l;

                @Override
                public void handle(long now) {
                    if (bonusCounter <= itemsLeft) {
                        if (upd < System.currentTimeMillis() + 250l) {
                            counter.setText("" + (bonusCounter * bonusPerItem));
                            bonusCounter++;
                            upd = System.currentTimeMillis() + 250l;
                        }
                    } else {
                        stop();
                    }
                }

            }.start();

        } catch (Exception ex) {
            // Do nothing
        }

    }

    private void addContinueButton() {

        Button btnContinue;

        btnContinue = new Button();
        btnContinue.setText("CONTINUE");
        btnContinue.setMinSize(100, 50);
        btnContinue.setPrefSize(100, 50);
        btnContinue.setMaxSize(100, 50);
        btnContinue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getController().chooseNextScene(SceneController.Actions.CONTINUE);
                getController().applyNextScene();
            }
        });
        btnContinue.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        btnContinue.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
            }
        });

        this.root.getChildren().add(btnContinue);
        btnContinue.setLayoutX((APP_WIDTH / 2) - 50);
        btnContinue.setLayoutY((APP_HEIGHT * (4.0 / 6.0)) - 25.0);

    }

}
