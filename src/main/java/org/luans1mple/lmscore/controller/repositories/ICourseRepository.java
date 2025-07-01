package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.Course;

import java.util.List;

public interface ICourseRepository {
    public void addCoure(Course course);
    public List<Course> getAll();
    public Course getById(int id);
    public void update(Course course);
    public void remove(Course course);
    public List<Course> inactiveCourses();
}
