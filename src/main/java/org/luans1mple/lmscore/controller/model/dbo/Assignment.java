package org.luans1mple.lmscore.controller.model.dbo;

import java.sql.Time;
import java.time.LocalDateTime;

public class Assignment {
    private int id;
    private ClassRoom classRoom;
    private String tittle;
    private String description;
    private String assignmentUrl;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private int maxScore;
    private User createBy;
    private boolean isAllowLate;

    public Assignment() {
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", startAt=" + startAt +
                ", createBy=" + createBy +
                ", maxScore=" + maxScore +
                ", endAt=" + endAt +
                '}';
    }

    public Assignment(int id, ClassRoom classRoom, String tittle, String description, String assignmentUrl, LocalDateTime startAt, LocalDateTime endAt, int maxScore, User createBy, boolean isAllowLate) {
        this.id = id;
        this.classRoom = classRoom;
        this.tittle = tittle;
        this.description = description;
        this.assignmentUrl = assignmentUrl;
        this.startAt = startAt;
        this.endAt = endAt;
        this.maxScore = maxScore;
        this.createBy = createBy;
        this.isAllowLate = isAllowLate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignmentUrl() {
        return assignmentUrl;
    }

    public void setAssignmentUrl(String assignmentUrl) {
        this.assignmentUrl = assignmentUrl;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public boolean isAllowLate() {
        return isAllowLate;
    }

    public void setAllowLate(boolean allowLate) {
        isAllowLate = allowLate;
    }
}
