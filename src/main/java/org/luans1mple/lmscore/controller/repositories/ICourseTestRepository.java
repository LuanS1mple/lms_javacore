package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.CourseTest;

import java.util.List;


public interface ICourseTestRepository {
    public List<CourseTest> getByCourseId(int courseId);
    public CourseTest getById(int id);
    public void add(CourseTest courseTest);
    public int size();
}
