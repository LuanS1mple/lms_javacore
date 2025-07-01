package org.luans1mple.lmscore.controller.repositories.mem;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.EnrollCourse;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IEnrollCourseRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MEMEnrollCouseRepository implements IEnrollCourseRepository {
    private static final MEMEnrollCouseRepository instance = new MEMEnrollCouseRepository();
    private List<EnrollCourse> enrollCourses = new ArrayList<>();
    private MEMEnrollCouseRepository(){
        Date now = new Date(System.currentTimeMillis());
        List<User> users = new ArrayList<>();
        users.add(new User(1, "luan", "123", "Alice Nguyen",
                "0909123456", "123 Le Loi, HCM", "avatar1.jpg", 1, 1, now));
        users.add(new User(2, "bob@example.com", "pass456", "Bob Tran",
                "0912123456", "456 Nguyen Trai, HN", "avatar2.jpg", 2, 1, now));

        // Courses (cũng giống như ở trên)
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "Java Fundamentals", "Học lập trình Java cơ bản",
                "Programming", "Beginner", 30, "active", "FPT", now, now));

        courses.add(new Course(2, "Web Development", "HTML, CSS và JavaScript cho người mới",
                "Web", "Beginner", 25, "active", "FPT", now, now));

        courses.add(new Course(3, "Database Design", "Thiết kế và tối ưu cơ sở dữ liệu",
                "Database", "Intermediate", 20, "inactive", "FPT", now, now));

        courses.add(new Course(4, "Spring Boot", "Xây dựng ứng dụng web với Spring Boot",
                "Backend", "Intermediate", 40, "active", "FPT", now, now));

        courses.add(new Course(5, "Machine Learning", "Giới thiệu về học máy và các thuật toán cơ bản",
                "AI", "Advanced", 50, "draft", "FPT", now, now));

        // Enrollments
        enrollCourses.add(new EnrollCourse(users.get(0), courses.get(0), now)); // Alice -> Java
        enrollCourses.add(new EnrollCourse(users.get(0), courses.get(1), now)); // Alice -> Web
        enrollCourses.add(new EnrollCourse(users.get(1), courses.get(1), now)); // Bob -> Web
        enrollCourses.add(new EnrollCourse(users.get(1), courses.get(2), now)); // Bob -> DB
    }
    public static MEMEnrollCouseRepository getInstance(){
        return instance;
    }
    @Override
    public List<Course> getCoursesByUserId(int id) {
        List<Course> enrolls = enrollCourses.stream()
                .filter(enrollCourse -> enrollCourse.getUser().getId() == id)
                .map(enrollCourse ->enrollCourse.getCourse()).collect(Collectors.toList());
        return enrolls;
    }

    @Override
    public void add(EnrollCourse enrollCourse) {
        enrollCourses.add(enrollCourse);
    }


}
