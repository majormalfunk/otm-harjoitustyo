/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */

import javafx.stage.Stage;
import mizzilekommand.ActionSelector;
import mizzilekommand.SceneController;
import mizzilekommand.SceneController.Scenes;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jaakkovilenius
 */
public class ActionSelectorTest {
    
    private ActionSelector selector;

    @Before
    public void setUp() {
        
        selector = new ActionSelector();
        
    }
    
    @Test
    public void choosesCorrectSceneOnActionPlay() {
        assertEquals(SceneController.Scenes.PLAY, selector.chooseNextScene(SceneController.Actions.PLAY));
    }
    @Test
    public void choosesCorrectSceneOnActionNoBases() {
        assertEquals(SceneController.Scenes.END, selector.chooseNextScene(SceneController.Actions.NOBASES));
    }
    @Test
    public void choosesCorrectSceneOnActionEnd() {
        assertEquals(SceneController.Scenes.START, selector.chooseNextScene(SceneController.Actions.THEEND));
    }
    
}
