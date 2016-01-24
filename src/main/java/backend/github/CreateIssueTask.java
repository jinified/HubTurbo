package backend.github;

import java.io.IOException;

import org.eclipse.egit.github.core.Issue;

import backend.interfaces.Repo;
import backend.interfaces.TaskRunner;
import backend.resource.TurboIssue;

public class CreateIssueTask extends GitHubRepoTask<Issue> {

    private final String repoId;
    private Issue newIssue;

    public CreateIssueTask(TaskRunner taskRunner, Repo repo, String repoId, 
            Issue newIssue) {
        super(taskRunner, repo);
        this.repoId = repoId;
        this.newIssue = newIssue;
    }

    @Override
    public void run() {
        try {
            response.complete(repo.setIssue(repoId, newIssue));
        } catch (IOException e) {
            response.completeExceptionally(e);
        }
    }

}
