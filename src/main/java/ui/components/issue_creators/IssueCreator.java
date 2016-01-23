package ui.components.issue_creators;

import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.stage.Stage;
import ui.UI;
import util.events.ShowIssueCreatorEventHandler;

public class IssueCreator {

    private final UI ui;
    private final Stage stage;

    // A LabelPicker is created by trigger a ShowLabelPickerEvent.
    public IssueCreator(UI ui, Stage stage) {
        this.ui = ui;
        this.stage = stage;
        ui.registerEvent((ShowIssueCreatorEventHandler) e -> Platform.runLater(
                () -> showIssueCreator()));
    }

    private void showIssueCreator() {
        // create new IssueCreatorDialog
        IssueCreatorDialog issueCreatorDialog = new IssueCreatorDialog(stage);
        Optional<List<String>> result = issueCreatorDialog.showAndWait();
        stage.show(); // ensures stage is showing after label picker is closed (mostly for tests)
    }

}
