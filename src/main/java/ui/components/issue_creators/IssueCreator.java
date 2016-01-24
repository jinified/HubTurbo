package ui.components.issue_creators;

import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.Scene;
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
        Stage issueStage = new Stage();
        issueStage.setScene(new Scene(new IssueCreatorPresenter(ui, issueStage)));
        issueStage.setTitle("Issue Creator");
        issueStage.show();
    }

}
