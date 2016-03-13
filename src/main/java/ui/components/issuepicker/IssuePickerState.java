package ui.components.issuepicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import backend.resource.TurboIssue;

/**
 * This class is used to represent the state of the issue picker. 
 */
public class IssuePickerState {

    private List<TurboIssue> selectedIssues;
    private List<TurboIssue> suggestedIssues;

    private final List<TurboIssue> allIssues;

    public IssuePickerState(List<TurboIssue> allIssues, String userInput) {
        this(allIssues, new ArrayList<>(), new ArrayList<>());
        update(userInput);
    }

    private IssuePickerState(List<TurboIssue> allIssues, List<TurboIssue> selectedIssues, 
                             List<TurboIssue> suggestedIssues) {
        this.selectedIssues = selectedIssues;
        this.suggestedIssues = suggestedIssues;
        this.allIssues = allIssues;
    }

    public List<TurboIssue> getSelectedIssues() {
        return selectedIssues;
    }

    public List<TurboIssue> getSuggestedIssues() {
        return suggestedIssues;
    }

    public Optional<TurboIssue> getCurrentSuggestion() {
        if (suggestedIssues.isEmpty()) return Optional.empty();
        return Optional.of(suggestedIssues.get(0));
    }

    /**
     * Updates current state based on given user input
     * @param userInput 
     */
    private final void update(String userInput) {
        if (userInput.isEmpty()) return;

        List<String> confirmedKeywords = getConfirmedKeywords(userInput);
        for (String confirmedKeyword : confirmedKeywords) {
            // Update selected issues with first match of query with all issues
            updateSelectedIssues(Optional.ofNullable(TurboIssue.getMatchedIssues(allIssues, confirmedKeyword).get(0)));
        }

        Optional<String> keywordInProgress = getKeywordInProgress(userInput);
        if (keywordInProgress.isPresent()) {
            updateSuggestedIssues(allIssues, keywordInProgress.get());
        }
    }


    /**
     * Updates selected issues if issue is present
     * @param issue
     */
    private void updateSelectedIssues(Optional<TurboIssue> issue) {
        if (!issue.isPresent()) return;
       
        TurboIssue selectedIssue = issue.get();
        if (selectedIssues.contains(selectedIssue)) {
            selectedIssues.remove(selectedIssue);
        } else {
            selectedIssues.add(selectedIssue);
        }
    }

    /**
     * Updates suggested issues with given query
     * @param issue
     */
    private void updateSuggestedIssues(List<TurboIssue> issues, String query) {
        suggestedIssues.clear();
        suggestedIssues.addAll(TurboIssue.getMatchedIssues(issues, query));
    }

    /**
     * @param userInput
     * @return list of confirmed keywords based on given userInput
     */
    private static List<String> getConfirmedKeywords(String userInput) {
        ArrayList<String> confirmedKeywords = new ArrayList<>();

        String[] keywords = userInput.split("\\s+");
        for (int i = 0; i < keywords.length; i++) {
            if (isConfirmedKeyword(userInput, i)) {
                confirmedKeywords.add(keywords[i]);
            }
        }

        return confirmedKeywords;
    }

    /**
     * If userInput does not end with space, split by space and return last word.
     * @param userInput
     * @return the keyword in progress based on the userInput
     */
    private static Optional<String> getKeywordInProgress(String userInput) {
        String[] keywords = userInput.split("\\s+");
        if (keywords.length == 0) return Optional.empty();

        if (isConfirmedKeyword(userInput, keywords.length - 1)) return Optional.empty();

        return Optional.of(keywords[keywords.length - 1]);
    }

    /**
     * Determines if the keywordIndex-th keyword is confirmed i.e. user has typed a space after it
     * Assumption: userInput has at least keywordIndex+1 keywords, separated by whitespace.
     * @param userInput
     * @param keywordIndex
     * @return
     */
    private static boolean isConfirmedKeyword(String userInput, int keywordIndex) {
        return keywordIndex != userInput.split("\\s+").length - 1 || userInput.endsWith(" ");
    }
}