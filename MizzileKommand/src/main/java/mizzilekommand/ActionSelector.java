/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand;

import mizzilekommand.SceneController.Actions;
import mizzilekommand.SceneController.Scenes;

/**
 *
 * @author jaakkovilenius
 */
public class ActionSelector {

    /**
     * Calling this method with the String defining the action determines the
     * next scene to be displayed. Possible values defined in this
     * SceneController class are: PLAY : Start game play QUIT : For testing
     * purposes to stop game play NOBASES : Called when game play ends with no
     * bases left THEEND : Called when game play ends without top score
     *
     * @param action
     */
    public Scenes chooseNextScene(Actions action) {
        System.out.println(action);
        switch (action) {
            case PLAY:
                return Scenes.PLAY;
            case QUIT:
            case THEEND:
                return Scenes.START;
            case NOBASES:
                return Scenes.END;
            default:
                return Scenes.START;
        }
    }
    
}
