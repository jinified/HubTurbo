package guitests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.loadui.testfx.utils.FXTestUtils;

import backend.RepoIO;
import javafx.scene.control.ComboBox;
import prefs.ConfigFile;
import prefs.SessionConfig;
import prefs.Preferences;
import ui.TestController;
import ui.UI;
import ui.components.StatusUI;
import util.events.EventDispatcher;

public class SavedLoginTest extends UITest {

    @Override
    public void launchApp() {
        FXTestUtils.launchApp(TestUI.class, "--testconfig=true");
    }

    @Override
    public void beforeStageStarts() {
        UI.status = mock(StatusUI.class);
        UI.events = mock(EventDispatcher.class);
        // setup test json with last viewed repo "test/test"
        // and then create the corresponding repo json file
        SessionConfig sessionConfig = new SessionConfig();
        sessionConfig.setLastLoginCredentials("test", "test");
        sessionConfig.setLastViewedRepository("test/test");
        ConfigFile sessionConfigFile =
                new ConfigFile(Preferences.DIRECTORY, Preferences.TEST_SESSION_CONFIG_FILENAME);
        sessionConfigFile.saveConfig(sessionConfig);
        RepoIO testIO = TestController.createTestingRepoIO(Optional.empty());
        try {
            testIO.openRepository("test/test").get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void savedLogin_lastSavedLoginCredentials_shouldAllowLoginWithoutPrompting()
        throws InterruptedException {
        ComboBox<String> repositorySelector = find("#repositorySelector");
        assertEquals("test/test", repositorySelector.getValue());
    }
}
