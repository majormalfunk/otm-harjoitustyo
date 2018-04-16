package mizzilekommand.tests;

/*
 * OTM-harjoitustyö kevät 2018
 * Jaakko Vilenius
 */

import mizzilekommand.logics.ActionSelector;
import mizzilekommand.logics.SceneController;
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
    public void choosesCorrectSceneOnActionContinue() {
        assertEquals(SceneController.Scenes.PLAY, selector.chooseNextScene(SceneController.Actions.CONTINUE));
    }
    @Test
    public void choosesCorrectSceneOnActionTheEnd() {
        assertEquals(SceneController.Scenes.START, selector.chooseNextScene(SceneController.Actions.THEEND));
    }
    @Test
    public void choosesCorrectSceneOnActionEnoughDestroyed() {
        assertEquals(SceneController.Scenes.BONUS, selector.chooseNextScene(SceneController.Actions.ENOUGHDESTROYED));
    }
    @Test
    public void choosesCorrectSceneOnActionNoIncoming() {
        assertEquals(SceneController.Scenes.BONUS, selector.chooseNextScene(SceneController.Actions.NOINCOMING));
    }
    @Test
    public void choosesCorrectSceneOnActionNoCities() {
        assertEquals(SceneController.Scenes.END, selector.chooseNextScene(SceneController.Actions.NOCITIES));
    }
    @Test
    public void choosesCorrectSceneOnActionEnd() {
        assertEquals(SceneController.Scenes.START, selector.chooseNextScene(SceneController.Actions.THEEND));
    }
    
}
