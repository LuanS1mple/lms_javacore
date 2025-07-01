package org.luans1mple.lmscore.controller.model.dbo;

import java.util.Date;

public class EnrollCourse {
    private User user;
    private Course course;
    private Date enrollAt;

    public EnrollCourse() {
    }

    public EnrollCourse(User user, Course course, Date enrollAt) {
        this.user = user;
        this.course = course;
        this.enrollAt = enrollAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getEnrollAt() {
        return enrollAt;
    }

    public void setEnrollAt(Date enrollAt) {
        this.enrollAt = enrollAt;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
