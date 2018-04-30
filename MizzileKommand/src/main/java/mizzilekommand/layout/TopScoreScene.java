/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.layout;

import mizzilekommand.logics.SceneController;
import javafx.scene.Parent;

/**
 * Not implemented yet but will be the scene displayed when the player reaches a top score
 *
 * @author jaakkovilenius
 */
public class TopScoreScene extends SceneTemplate {

    public TopScoreScene(SceneController controller, int level) {
        super(controller);

        showLevelIndicator(level);

    }

}
