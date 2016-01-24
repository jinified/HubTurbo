package ui.components.issue_creators;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ui.UI;

public class IssueCreatorPresenter extends BorderPane {

    private UI ui;
    
    //FXML injection
    
    @FXML
    Label promptLabel;
    
    @FXML
    TextField issueTitle;

    @FXML
    TextArea issueBody;
    
    @FXML
    Button confirmBtn;

    public IssueCreatorPresenter(UI ui) {
        this.ui = ui;
        initView();
    }

    private void initView() {
        FXMLLoader loader = new FXMLLoader(UI.class.getResource(
                "fxml/IssueCreatorView.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
