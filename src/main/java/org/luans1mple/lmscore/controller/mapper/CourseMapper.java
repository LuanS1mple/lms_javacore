package org.luans1mple.lmscore.controller.mapper;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dto.request.CourseRequest;

import java.time.LocalDateTime;
import java.util.Date;

public class CourseMapper {
    public static Course fromCourseRequesst(CourseRequest request){
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setCategory(request.getCategory());
        course.setLevel(request.getLevel());
        course.setDuration(request.getDuration());
        course.setAuthor(request.getAuthor());

        course.setStatus("active");
        course.setCreatedAt(new Date());
        course.setUpdatedAt(new Date());

        return course;
    }
    public static void mapCourse(Course source, Course target) {
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        target.setCategory(source.getCategory());
        target.setLevel(source.getLevel());
        target.setDuration(source.getDuration());
        target.setStatus(source.getStatus());
        target.setAuthor(source.getAuthor());
        target.setCreatedAt(source.getCreatedAt());
        target.setUpdatedAt(new Date());
    }
}
