package org.luans1mple.lmscore.controller.model.dbo;

public class CourseTest {
    private int id;
    private Course course;
    private String testTittle;
    private String testDescription;
    private String type;
    private String testUrl;
    private int maxScore;
    private int duration;
    private int status;

    public CourseTest() {
    }

    public CourseTest(int id, Course course, String testTittle, String testDescription, String type, String testUrl, int maxScore, int duration, int status) {
        this.id = id;
        this.course = course;
        this.testTittle = testTittle;
        this.testDescription = testDescription;
        this.type = type;
        this.testUrl = testUrl;
        this.maxScore = maxScore;
        this.duration = duration;
        this.status = status;
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

    public String getTestTittle() {
        return testTittle;
    }

    public void setTestTittle(String testTittle) {
        this.testTittle = testTittle;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTestUrl() {
        return testUrl;
    }

    public void setTestUrl(String testUrl) {
        this.testUrl = testUrl;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseTest{" +
                "course=" + course +
                ", testTittle='" + testTittle + '\'' +
                ", testDescription='" + testDescription + '\'' +
                ", type='" + type + '\'' +
                ", maxScore=" + maxScore +
                ", duration=" + duration +
                '}';
    }
}
