/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package layout;

import mizzilekommand.SceneController;

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
