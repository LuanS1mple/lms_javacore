package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.EnrollCourse;

import java.util.List;

public interface IEnrollCourseRepository {
    public List<Course> getCoursesByUserId(int id);
    public void add(EnrollCourse enrollCourse);
}
