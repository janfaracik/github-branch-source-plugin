package org.jenkinsci.plugins.github_branch_source;

import edu.umd.cs.findbugs.annotations.Nullable;
import hudson.model.Actionable;
import jenkins.model.Detail;
import jenkins.model.DetailGroup;
import jenkins.scm.api.SCMRevision;
import jenkins.scm.api.SCMRevisionAction;

public class GitHubBranchDetail extends Detail {
    public GitHubBranchDetail(Actionable object) {
        super(object);
    }

    @Nullable
    @Override
    public String getIconClassName() {
        return "symbol-git-pull-request-outline plugin-ionicons-api";
    }

    @Nullable
    @Override
    public String getDisplayName() {
        SCMRevisionAction scmRevisionAction = getObject().getAction(SCMRevisionAction.class);
        SCMRevision revision = scmRevisionAction.getRevision();
        var head = revision.getHead();

        System.out.println(head.getOrigin().getClass().getName());
        System.out.println(head.getOrigin());
        System.out.println(head.getClass().getName());

        return head.getName();
    }

    @Override
    public String getUrl() {
        SCMRevisionAction scmRevisionAction = getObject().getAction(SCMRevisionAction.class);
        SCMRevision revision = scmRevisionAction.getRevision();

        if (revision instanceof PullRequestSCMRevision pullRequestSCMRevision) {
            PullRequestSCMHead head = (PullRequestSCMHead) pullRequestSCMRevision.getHead();
            String sourceOwner = head.getSourceOwner();
            String sourceRepo = head.getSourceRepo();

            return "https://github.com/" + sourceOwner + "/" + sourceRepo + "/tree/" + getDisplayName());
        }

        // How to construct this from a branch?
        return null;
    }

    @Override
    public boolean isApplicable() {
        SCMRevisionAction scmRevisionAction = getObject().getAction(SCMRevisionAction.class);
        return scmRevisionAction != null;
    }

    @Override
    public DetailGroup getGroup() {
        return DetailGroup.SCM;
    }
}
