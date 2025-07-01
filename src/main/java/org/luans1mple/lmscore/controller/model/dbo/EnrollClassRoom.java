package org.luans1mple.lmscore.controller.model.dbo;

import java.sql.Date;

public class EnrollClassRoom {
    private User user;
    private ClassRoom classRoom;
    private Date enrollAt;
    private int role;

    public EnrollClassRoom() {
    }

    public EnrollClassRoom(User user, ClassRoom classRoom, Date enrollAt, int role) {
        this.user = user;
        this.classRoom = classRoom;
        this.enrollAt = enrollAt;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Date getEnrollAt() {
        return enrollAt;
    }

    public void setEnrollAt(Date enrollAt) {
        this.enrollAt = enrollAt;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
