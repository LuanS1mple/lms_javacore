package org.luans1mple.lmscore.controller.model.dbo;

import java.time.LocalDateTime;

public class AssignmentResult {
    private User user;
    private Assignment assignment;
    private LocalDateTime doneAt;
    private String submissionUrl;
    private int mark;
    private String comment;
    private int status;

    public String getSubmissionUrl() {
        return submissionUrl;
    }

    public void setSubmissionUrl(String submissionUrl) {
        this.submissionUrl = submissionUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AssignmentResult() {
    }

    public AssignmentResult(User user, Assignment assignment, LocalDateTime doneAt, String submissionUrl, int mark, String comment, int status) {
        this.user = user;
        this.assignment = assignment;
        this.doneAt = doneAt;
        this.submissionUrl = submissionUrl;
        this.mark = mark;
        this.comment = comment;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public LocalDateTime getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(LocalDateTime doneAt) {
        this.doneAt = doneAt;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
