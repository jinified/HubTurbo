package ui.components.issue_creators;

import backend.resource.TurboIssue;
import backend.resource.TurboLabel;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IssueCreatorDialog extends Dialog<List<String>> {

    private static final int VBOX_SPACING = 105; // seems like some magic number
    private static final int ELEMENT_MAX_WIDTH = 400;

    private final IssueCreatorUILogic uiLogic;
    private final TextField textField;
    private final FlowPane topPane;
    private final VBox bottomBox;

    IssueCreatorDialog(Stage stage) {
        // UI creation
        initialiseDialog(stage);
        VBox vBox = createVBox();
        topPane = createTopPane();
        textField = createTextField();
        bottomBox = createBottomBox();

        uiLogic = new IssueCreatorUILogic();

        vBox.getChildren().addAll(topPane, textField, bottomBox);
        getDialogPane().setContent(vBox);

        Platform.runLater(textField::requestFocus);
    }

    private void initialiseDialog(Stage stage) {
        initOwner(stage);
        initModality(Modality.APPLICATION_MODAL); // TODO change to NONE for multiple dialogs
        setTitle("Create new issue");
    }

    public final void positionDialog(Stage stage) {
        if (!Double.isNaN(getHeight())) {
            setX(stage.getX() + stage.getScene().getX());
            setY(stage.getY() +
                 stage.getScene().getY() +
                 (stage.getScene().getHeight() - getHeight()) / 2);
        }
    }

    private VBox createVBox() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setPrefHeight(1);
        vBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            setHeight(newValue.intValue() + VBOX_SPACING); // dialog box should auto-resize
        });
        return vBox;
    }

    private FlowPane createTopPane() {
        FlowPane topPane = new FlowPane();
        topPane.setPadding(new Insets(20, 0, 10, 0));
        topPane.setHgap(5);
        topPane.setVgap(5);
        return topPane;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.setId("labelPickerTextField");
        textField.setPrefColumnCount(30);
        return textField;
    }

    private VBox createBottomBox() {
        VBox bottomBox = new VBox();
        bottomBox.setPadding(new Insets(10, 0, 0, 0));
        return bottomBox;
    }

}
