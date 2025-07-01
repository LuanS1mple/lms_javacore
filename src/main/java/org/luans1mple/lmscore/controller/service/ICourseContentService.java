package org.luans1mple.lmscore.controller.service;

import org.luans1mple.lmscore.controller.model.dbo.CourseContent;

import java.util.List;

public interface ICourseContentService {
    public List<CourseContent> getByCourseId(int courseId);
    public void downLoadCourseContent(CourseContent courseContent);
    public List<CourseContent> getByTittle(String tittle,int courseid);
    public String getContentUrl();
    public void add(CourseContent courseContent);
    public int size();
}
