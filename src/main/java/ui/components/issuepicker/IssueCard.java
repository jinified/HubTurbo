package ui.components.issuepicker;

import java.util.Optional;

import backend.resource.TurboIssue;
import backend.resource.TurboLabel;
import backend.resource.TurboMilestone;
import backend.resource.TurboUser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ui.GuiElement;

/**
 * Represents an individual issue card not linked to any panel
 */
public class IssueCard extends VBox {

    private static final String OCTICON_PULL_REQUEST = "\uf009";
    private static final int CARD_WIDTH = 350;
    private static final String OCTICON_COMMENT = "\uf02b";
    private static final String OCTICON_ARROW_RIGHT = "\uf03e";

    private final GuiElement guiElement;
    private final FlowPane issueDetails = new FlowPane();
    private final HBox authorAssigneeBox = new HBox();

    public IssueCard(GuiElement guiElement){
        this.guiElement = guiElement;
        setup();
    }

    private void setup() {
        TurboIssue issue = guiElement.getIssue();
        Text issueTitle = new Text("#" + issue.getId() + " " + issue.getTitle());
        issueTitle.setWrappingWidth(CARD_WIDTH);
        issueTitle.getStyleClass().add("issue-panel-name");

        if (issue.isCurrentlyRead()) {
            issueTitle.getStyleClass().add("issue-panel-name-read");
        }

        if (!issue.isOpen()) {
            issueTitle.getStyleClass().add("issue-panel-closed");
        }

        setupIssueDetailsBox();
        setupAuthorAssigneeBox();
        updateDetails();

        setPadding(new Insets(0, 0, 0, 0));
        setSpacing(1);

        getChildren().addAll(issueTitle, issueDetails, authorAssigneeBox);
    }

    private void setupIssueDetailsBox() {
        issueDetails.setMaxWidth(CARD_WIDTH);
        issueDetails.setPrefWrapLength(CARD_WIDTH);
        issueDetails.setHgap(3);
        issueDetails.setVgap(3);
    }

    private void setupAuthorAssigneeBox() {
        authorAssigneeBox.setPrefWidth(CARD_WIDTH);
        authorAssigneeBox.setPadding(new Insets(0, 0, 1, 0));
    }

    private void updateDetails() {
        issueDetails.getChildren().clear();
        TurboIssue issue = guiElement.getIssue();

        if (issue.isPullRequest()) {
            Label icon = new Label(OCTICON_PULL_REQUEST);
            icon.getStyleClass().addAll("octicon", "issue-pull-request-icon");
            issueDetails.getChildren().add(icon);
        }

        if (issue.getCommentCount() > 0){
            Label commentIcon = new Label(OCTICON_COMMENT);
            commentIcon.getStyleClass().addAll("octicon", "comments-label-button");
            Label commentCount = new Label(Integer.toString(issue.getCommentCount()));

            issueDetails.getChildren().add(commentIcon);
            issueDetails.getChildren().add(commentCount);
        }

        for (TurboLabel label : guiElement.getLabels()) {
            issueDetails.getChildren().add(label.getNode());
        }

        if (issue.getMilestone().isPresent() && guiElement.getMilestone().isPresent()) {
            TurboMilestone milestone = guiElement.getMilestone().get();
            issueDetails.getChildren().add(new Label(milestone.getTitle()));
        }

        if (issue.isPullRequest()) {
            HBox authorBox = createDisplayUserBox(guiElement.getAuthor(), issue.getCreator());
            authorAssigneeBox.getChildren().add(authorBox);
            if (issue.getAssignee().isPresent()) {
                Label rightArrow = new Label(OCTICON_ARROW_RIGHT);
                rightArrow.getStyleClass().addAll("octicon", "pull-request-assign-icon");
                authorAssigneeBox.getChildren().add(rightArrow);
            }
        }

        if (issue.getAssignee().isPresent()) {
            HBox assigneeBox = createDisplayUserBox(guiElement.getAssignee(), issue.getAssignee().get());
            authorAssigneeBox.getChildren().add(assigneeBox);
        }
    }

    /**
     * Creates a box that displays a label of userName
     * The avatar that belongs to the user will be prepended if TurboUser has it
     * @param user
     * @param userName
     * @return
     */
    private HBox createDisplayUserBox(Optional<TurboUser> user, String userName) {
        HBox userBox = setupUserBox();
        Label authorNameLabel = new Label(userName);
        addAvatarIfPresent(userBox, user);
        userBox.getChildren().addAll(authorNameLabel);
        return userBox;
    }

    private void addAvatarIfPresent(HBox userBox, Optional<TurboUser> user) {
        if (!user.isPresent()) return;
        ImageView userAvatar = getAvatar(user.get());
        userBox.getChildren().add(userAvatar);
    }

    private HBox setupUserBox() {
        HBox userBox = new HBox();
        userBox.setAlignment(Pos.BASELINE_CENTER);
        return userBox;
    }

    /**
     * Attempts to get the TurboUser's avatar
     * @param user
     * @return ImageView that contains the avatar image if it exists or an empty ImageView if it doesn't exist
     */
    private ImageView getAvatar(TurboUser user) {
        ImageView userAvatar = new ImageView();
        Image userAvatarImage = user.getAvatarImage();
        if (userAvatarImage != null) {
            userAvatar.setImage(userAvatarImage);
        }
        return userAvatar;
    }

}