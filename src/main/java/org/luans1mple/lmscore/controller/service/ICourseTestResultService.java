package org.luans1mple.lmscore.controller.service;

import org.luans1mple.lmscore.controller.model.dbo.CourseTestResult;

import java.util.List;

public interface ICourseTestResultService {
    public void add(CourseTestResult result);
    public List<CourseTestResult> getResults(int userId,int courseTestId);
    public String getSubmitUrl();
    public void submit(int userId,int testId);
    public int autoMark(String submitUrl, int testId);
}
