package org.luans1mple.lmscore.controller.service;

import org.luans1mple.lmscore.controller.model.dbo.Course;

import java.util.List;

public interface ICourseService {
    public void addCourse(Course course);
    public List<Course> getAll();
    public Course getInputCourse();
    public void showCourses();
    public Course getById(int id);
    public void update(Course course);
    public void remove(Course course);
    public List<Course> inactiveCourses();
}
