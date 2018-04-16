/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */
package mizzilekommand.logics;

import mizzilekommand.logics.SceneController.Actions;
import mizzilekommand.logics.SceneController.Scenes;

/**
 *
 * @author jaakkovilenius
 */
public class ActionSelector {

    /**
     * Calling this method with the String defining the action determines the
     * next scene to be displayed. Possible values defined in this
     * SceneController class are: PLAY : Start game play, NOCITIES : Called when
     * game play ends with no cities left, ENOUGHDESTROYED : Called when enough
     * cities are destroyed in level, NOINCOMING : Called when no more incoming
     * missiles in level are left, THEEND : Called when game play ends without
     * top score
     *
     * @param action The action
     */
    public Scenes chooseNextScene(Actions action) {
        System.out.println(action);
        switch (action) {
            case PLAY:
            case CONTINUE:
                return Scenes.PLAY;
            case THEEND:
                return Scenes.START;
            case ENOUGHDESTROYED:
            case NOINCOMING:
                return Scenes.BONUS;
            case NOCITIES:
                return Scenes.END;
            default:
                return Scenes.START;
        }
    }

}
