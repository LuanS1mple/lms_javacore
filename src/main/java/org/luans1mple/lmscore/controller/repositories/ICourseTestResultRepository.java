package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.CourseTest;
import org.luans1mple.lmscore.controller.model.dbo.CourseTestResult;

import java.util.List;

public interface ICourseTestResultRepository {
    public void add(CourseTestResult result);
    public List<CourseTestResult> getResults(int userId,int courseTestId);
}
