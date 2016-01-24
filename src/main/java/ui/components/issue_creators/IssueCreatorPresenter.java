package ui.components.issue_creators;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.UI;

public class IssueCreatorPresenter extends BorderPane {

    private UI ui;
    private Stage stage;
    
    //FXML injection
    @FXML
    Label promptLabel;
    @FXML
    TextField issueTitle;
    @FXML
    TextArea issueBody;
    @FXML
    Button confirmBtn;

    public IssueCreatorPresenter(UI ui, Stage stage) {
        this.ui = ui;
        this.stage = stage;
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
    
    @FXML
    protected void submit(ActionEvent event) {
        System.out.println("click");
        stage.close();
    }

}
