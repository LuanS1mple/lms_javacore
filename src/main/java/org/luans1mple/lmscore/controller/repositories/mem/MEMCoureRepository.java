package org.luans1mple.lmscore.controller.repositories.mem;

import org.luans1mple.lmscore.controller.mapper.CourseMapper;
import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.repositories.ICourseRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MEMCoureRepository implements ICourseRepository {
    private List<Course> courses = new ArrayList<>();
    private static final MEMCoureRepository instance = new MEMCoureRepository();
    private MEMCoureRepository(){
        Date now = new Date(System.currentTimeMillis());

        courses.add(new Course(1, "Java Fundamentals", "Học lập trình Java cơ bản",
                "Programming", "Beginner", 30, "active", "FPT", now, now));

        courses.add(new Course(2, "Web Development", "HTML, CSS và JavaScript cho người mới",
                "Web", "Beginner", 25, "active", "FPT", now, now));

        courses.add(new Course(3, "Database Design", "Thiết kế và tối ưu cơ sở dữ liệu",
                "Database", "Intermediate", 20, "inactive", "FPT", now, now));

        courses.add(new Course(4, "Spring Boot", "Xây dựng ứng dụng web với Spring Boot",
                "Backend", "Intermediate", 40, "active", "FPT", now, now));

        courses.add(new Course(5, "Machine Learning", "Giới thiệu về học máy và các thuật toán cơ bản",
                "AI", "Advanced", 50, "active", "FPT", now, now));

    }
    public static  MEMCoureRepository getInstance(){
        return  instance;
    }
    @Override
    public void addCoure(Course course) {
        courses.add(course);
    }

    @Override
    public List<Course> getAll() {
        return  courses;
    }

    @Override
    public Course getById(int id) {
        for (int i = 0; i < courses.size(); i++) {
            if(courses.get(i).getId()==id) return courses.get(i);
        }
        return null;
    }

    @Override
    public void update(Course course) {
        Course target = courses.stream()
                .filter(cs -> cs.getId()==course.getId()).findFirst().get();
        CourseMapper.mapCourse(course,target);
    }

    @Override
    public void remove(Course course) {
        course.setStatus("inactive");
    }

    @Override
    public List<Course> inactiveCourses() {
        return courses.stream()
                .filter(course -> course.getStatus().equals("inactive")).toList();

    }


}
