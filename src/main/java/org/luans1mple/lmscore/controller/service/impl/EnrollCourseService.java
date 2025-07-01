package org.luans1mple.lmscore.controller.service.impl;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.EnrollClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.EnrollCourse;
import org.luans1mple.lmscore.controller.repositories.IEnrollCourseRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLEnrollCourseRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMEnrollCouseRepository;
import org.luans1mple.lmscore.controller.service.ICourseService;
import org.luans1mple.lmscore.controller.service.IEnrollCourseService;
import org.luans1mple.lmscore.controller.service.IUserService;

import java.sql.Date;
import java.util.List;

public class EnrollCourseService implements IEnrollCourseService {
    private IEnrollCourseRepository enrollCourseRepository;
    private ICourseService courseService;
    private IUserService userService;
    public EnrollCourseService(){
//        this.enrollCourseRepository= MEMEnrollCouseRepository.getInstance();
        this.enrollCourseRepository= SQLEnrollCourseRepository.getInstance();
        this.courseService = new CourseService();
        this.userService= new UserService();
    }
    @Override
    public List<Course> getCoursesById(int id) {
        return enrollCourseRepository.getCoursesByUserId(id);
    }

    @Override
    public void showCourseEnrolled(int userId) {
        List<Course> courses = this.getCoursesById(userId);
        if(courses.isEmpty()){
            System.out.println("No course founded");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i+1)+". 1"+courses.get(i).toString());
        }
    }

    @Override
    public List<Course> getUnEnrollCoursesById(int id) {
        List<Course> all = courseService.getAll();
        List<Course> enrolled = this.getCoursesById(id);
        return all.stream().
                filter(course -> enrolled.stream().noneMatch(e->e.getId()== course.getId())).toList();
    }

    @Override
    public void showUnEnrollCourses(int id) {
        List<Course> courses = this.getUnEnrollCoursesById(id);
        if(courses.isEmpty()){
            System.out.println("No course founded");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i+1)+". "+courses.get(i).getTitle());
        }
    }

    @Override
    public void add(int userId, int coureId) {
        EnrollCourse enrollCourse = new EnrollCourse();
        enrollCourse.setUser(userService.getById(userId));
        enrollCourse.setCourse(courseService.getById(coureId));
        Date now = new Date(System.currentTimeMillis());
        enrollCourse.setEnrollAt(now);
        enrollCourseRepository.add(enrollCourse);
    }

    @Override
    public List<Course> getEnrolledCourseByTitle(String title,int userId) {
        return enrollCourseRepository.getCoursesByUserId(userId).stream()
                .filter(course-> course.getTitle().toLowerCase().contains(title.toLowerCase())).toList();
    }

    @Override
    public List<Course> getUnEnrollCourseByTittle(String title,int userId) {
        return this.getUnEnrollCoursesById(userId).stream()
                .filter(couse -> couse.getTitle().toLowerCase().contains(title.toLowerCase())).toList();
    }


}
