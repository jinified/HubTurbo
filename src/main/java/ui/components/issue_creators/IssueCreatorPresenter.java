package ui.components.issue_creators;

import github.GitHubClientEx;
import github.IssueServiceEx;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.Label;

import com.sun.corba.se.spi.activation.Repository;

import backend.resource.Model;
import backend.resource.TurboIssue;
import backend.resource.TurboLabel;
import backend.resource.TurboMilestone;
import backend.resource.TurboUser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.UI;

public class IssueCreatorPresenter extends BorderPane {

    // UI 
    private UI ui;
    private Stage stage;
    
    // Main assets
    private Model repo;
    private String currentUser;
    private ArrayList<TurboLabel> labels;
    private ArrayList<TurboUser> users;
    private ArrayList<TurboIssue> issues;
    private ArrayList<TurboMilestone> milestones;

    // Current issue assets
    private List<TurboLabel> selectedLabels;
    private TurboMilestone selectedMilestone;
    private TurboUser selectedAssignee;
    
    // GitHub communication
    private final GitHubClientEx client = new GitHubClientEx();
    private final IssueServiceEx issueService = new IssueServiceEx(client);

    //FXML injection
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
        initAssets();
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
    
    private void initAssets() {
        String defaultRepoId = ui.logic.getDefaultRepo();
        repo = ui.logic.getRepo(defaultRepoId);
        currentUser = UI.prefs.getLastLoginUsername();
        labels = new ArrayList<>(repo.getLabels());
        users = new ArrayList<>(repo.getUsers());
        issues = new ArrayList<>(repo.getIssues());
        milestones = new ArrayList<>(repo.getMilestones());
    }


    @FXML
    protected void submit(ActionEvent event) {
        System.out.println("Sending");
        Platform.runLater(this::createIssue);
    }
    
    // Utility functions 

    private List<Label> createLabels(List<TurboLabel> oldLabels) {
        return oldLabels.stream()
            .map(label -> new Label().setName(label.getActualName())).collect(
            Collectors.toList());
    }

    private void createIssue() {
       String title = issueTitle.getText();
       String body = issueBody.getText();
       Issue newIssue = new Issue()
           .setTitle(title)
           .setBody(body)
           .setUser(new User().setLogin(currentUser))
           .setCreatedAt(Date.from(Instant.now()))
           .setState("open")
           .setLabels((List<Label>) createLabels(labels));
           
       ui.logic.createIssue(repo.getRepoId(), newIssue);
    }

}
