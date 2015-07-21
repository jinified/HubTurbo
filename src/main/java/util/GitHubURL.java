package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ui.UI;

public class GitHubURL {
    private static final Logger logger = LogManager.getLogger(GitHubURL.class.getName());

    public static final String VERSION_NUMBER = UI.getCurrentVersion();
    public static final String LOGIN_PAGE = "https://github.com/login";
    public static final String DOCS_PAGE = "https://github.com/HubTurbo/HubTurbo/blob/%s/docs/Getting-Started.md";
    public static final String CHANGELOG_PAGE = "https://github.com/HubTurbo/HubTurbo/blob/%s/docs/Changelog.md";
    public static final String CHANGELOG_PAGE_FORMAT =
        "https://github.com/HubTurbo/HubTurbo/blob/%s/docs/Changelog.md#v%d%d%d";

    public static String getPathForAllIssues(String repoId) {
        return String.format("https://github.com/%s/issues", repoId);
    }

    public static String getPathForIssue(String repoId, int id) {
        return String.format("https://github.com/%s/issues/%d", repoId, id);
    }

    public static String getPathForPullRequest(String repoId, int id) {
        return String.format("https://github.com/%s/pull/%d", repoId, id);
    }

    public static String getPathForNewIssue(String repoId) {
        return String.format("https://github.com/%s/issues/new", repoId);
    }

    public static String getPathForNewLabel(String repoId) {
        return String.format("https://github.com/%s/labels", repoId);
    }

    public static String getPathForNewMilestone(String repoId) {
        return String.format("https://github.com/%s/milestones/new", repoId);
    }

    public static String getPathForDocsPage() {
        return String.format(DOCS_PAGE, VERSION_NUMBER);
    }

//    public static String getChangelogForVersion(String version) {
//        Optional<int[]> numbers = Utility.parseVersionNumber(version);
//        if (numbers.isPresent()) {
//            int major = numbers.get()[0];
//            int minor = numbers.get()[1];
//            int patch = numbers.get()[2];
//            return String.format(CHANGELOG_PAGE_FORMAT, VERSION_NUMBER);
//        } else {
//            logger.error("Invalid version string format " + version + "; going to generic changelog page");
//            return String.format(CHANGELOG_PAGE, VERSION_NUMBER);
//        }
//    }

    public static String getPathForPullRequests(String repoId) {
        return String.format("https://github.com/%s/pulls", repoId);
    }

    public static String getPathForKeyboardShortcuts() {
        return "https://github.com/HubTurbo/HubTurbo/blob/V3.0.0/docs/Keyboard-Shortcuts";
    }

    public static String getPathForMilestones(String repoId) {
        return String.format("https://github.com/%s/milestones", repoId);
    }

    public static String getPathForContributors(String repoId) {
        return String.format("https://github.com/%s/graphs/contributors", repoId);
    }

    public static boolean isUrlIssue(String url) {
        return url.matches("https://github.com/([^/]+)/([^/]+)/(issues|pull)/([0-9]+)([/commits,/files]*)");
    }
}
