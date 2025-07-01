package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.CourseContent;

import java.util.List;

public interface ICourseContentRepository {
    public List<CourseContent> getByCourseId(int courseId);
    public int size();
    public void add(CourseContent courseContent);
}
