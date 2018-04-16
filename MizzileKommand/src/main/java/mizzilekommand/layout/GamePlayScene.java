/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import mizzilekommand.logics.SceneController;

/**
 *
 * @author jaakkovilenius
 */
public class GamePlayScene extends SceneTemplate {

    public GamePlayScene(SceneController controller, GamePane gamepane) {
        //public GamePlayScene(Parent root) {
        //super(root);
        super(controller);

        this.root.getChildren().add(gamepane);

    }

}