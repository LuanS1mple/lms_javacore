package org.luans1mple.lmscore.controller.service.impl;

import org.luans1mple.lmscore.controller.mapper.CourseMapper;
import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dto.request.CourseRequest;
import org.luans1mple.lmscore.controller.repositories.ICourseRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLCourseRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMCoureRepository;
import org.luans1mple.lmscore.controller.service.ICourseService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CourseService implements ICourseService {
    private CourseMapper courseMapper;
    private ICourseRepository courseRepository;

    public CourseService(){
//        this.courseRepository = MEMCoureRepository.getInstance();
        this.courseRepository = SQLCourseRepository.getInstance();
        this.courseMapper = new CourseMapper();
    }
    @Override
    public void addCourse(Course course) {
        courseRepository.addCoure(course);
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.getAll().stream()
                .filter(course -> course.getStatus().equals("active")).toList();
    }

    @Override
    public Course getInputCourse() {
        CourseRequest course = new CourseRequest();
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập tiêu đề khóa học: ");
        String title = sc.nextLine();
        course.setTitle(title);
        System.out.print("Nhập mô tả khóa học: ");
        String des = sc.nextLine();
        course.setDescription(des);
        System.out.print("Nhập danh mục khóa học: ");
        String category = sc.nextLine();
        course.setCategory(category);
        System.out.print("Nhập cấp độ khóa học: ");
        String level = sc.nextLine();
        course.setLevel(level);
        System.out.print("Nhập thời lượng khóa học: ");
        int duration = Integer.parseInt(sc.nextLine());
        course.setDuration(duration);
        System.out.print("Nhập tác giả: ");
        String author = sc.nextLine().trim();
        course.setAuthor(author);
        return courseMapper.fromCourseRequesst(course);
    }

    @Override
    public void showCourses() {
        List<Course> courses = this.getAll();
        if(courses.isEmpty()){
            System.out.println("No course founded");
            return;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.println(courses.get(i).toString());
        }
    }

    @Override
    public Course getById(int id) {
        return courseRepository.getById(id);
    }

    @Override
    public void update(Course course) {
        courseRepository.update(course);
    }

    @Override
    public void remove(Course course) {
        courseRepository.remove(course);
    }

    @Override
    public List<Course> inactiveCourses() {
        return courseRepository.inactiveCourses();
    }
}
