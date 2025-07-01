package org.luans1mple.lmscore.controller.service;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.EnrollClassRoom;

import java.util.List;

public interface IEnrollCourseService {
    public List<Course> getCoursesById(int id);
    public void showCourseEnrolled(int userId);
    public List<Course> getUnEnrollCoursesById(int id);
    public void showUnEnrollCourses(int id);
    public void add(int userId,int coureId);
    public List<Course> getEnrolledCourseByTitle(String title,int userId);
    public List<Course> getUnEnrollCourseByTittle(String title, int userId);
}
