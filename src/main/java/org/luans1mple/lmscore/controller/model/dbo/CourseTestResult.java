package org.luans1mple.lmscore.controller.model.dbo;

import org.luans1mple.lmscore.controller.service.impl.CourseTestResultService;

import java.time.LocalDateTime;

public class CourseTestResult {
    private User user;
    private CourseTest courseTest;
    private int mark;
    private LocalDateTime doneAt;
    private int status;

    public CourseTestResult() {
    }

    public CourseTestResult(User user, CourseTest courseTest, int mark, LocalDateTime doneAt, int status) {
        this.user = user;
        this.courseTest = courseTest;
        this.mark = mark;
        this.doneAt = doneAt;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CourseTest getCourseTest() {
        return courseTest;
    }

    public void setCourseTest(CourseTest courseTest) {
        this.courseTest = courseTest;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public LocalDateTime getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(LocalDateTime doneAt) {
        this.doneAt = doneAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String statusText;
        if(status == CourseTestResultService.getStatusPassNumber()){
            statusText ="Đạt";
        }
        else{
            statusText ="Chưa đạt";
        }
        return "Thời gian: "+this.getDoneAt()+" - Điểm: "+this.getMark() + " - Trạng thái: " +status;
    }
}
