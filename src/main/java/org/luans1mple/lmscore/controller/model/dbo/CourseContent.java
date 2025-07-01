package org.luans1mple.lmscore.controller.model.dbo;

import java.sql.Date;

public class CourseContent {
    private int id;
    private Course course;
    private String title;
    private String type; // 'video', 'document', etc.
    private String contentUrl;
    private Integer duration;
    private int orderIndex;
    private Date createdAt;

    public CourseContent() {
    }

    public CourseContent(int id, Course course, String title, String type, String contentUrl, Integer duration, int orderIndex, Date createdAt) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.type = type;
        this.contentUrl = contentUrl;
        this.duration = duration;
        this.orderIndex = orderIndex;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CourseContent{" +
                "course=" + course +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", duration=" + duration +
                ", orderIndex=" + orderIndex +
                '}';
    }
}
