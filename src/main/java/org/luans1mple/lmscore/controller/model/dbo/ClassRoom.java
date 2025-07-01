package org.luans1mple.lmscore.controller.model.dbo;

import java.util.Date;

public class ClassRoom {
    private int id;
    private String className;
    private Date createAt;
    private User createBy;
    private String inviteCode;

    public ClassRoom() {
    }

    public ClassRoom(int id, String className, Date createAt, User createBy, String inviteCode) {
        this.id = id;
        this.className = className;
        this.createAt = createAt;
        this.createBy = createBy;
        this.inviteCode = inviteCode;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getclassName() {
        return className;
    }

    public void setclassName(String class_name) {
        this.className = class_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
