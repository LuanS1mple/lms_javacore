package org.luans1mple.lmscore.controller.repositories.mem;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.CourseContent;
import org.luans1mple.lmscore.controller.repositories.ICourseContentRepository;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MEMCourseContentRepository implements ICourseContentRepository {
    private List<CourseContent> courseContents = new ArrayList<>();
    private static final MEMCourseContentRepository instance = new MEMCourseContentRepository();
    private MEMCourseContentRepository(){
        List<Course> courses = new ArrayList<>();
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
                "AI", "Advanced", 50, "draft", "FPT", now, now));


        courseContents.add(new CourseContent(1, courses.get(0), "Giới thiệu Java", "video", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 10, 1, now));
        courseContents.add(new CourseContent(2, courses.get(0), "Cấu trúc chương trình Java", "document", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", null, 2, now));
        courseContents.add(new CourseContent(3, courses.get(0), "Biến và kiểu dữ liệu", "video", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 15, 3, now));

        courseContents.add(new CourseContent(4, courses.get(1), "HTML cơ bản", "video", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 12, 1, now));
        courseContents.add(new CourseContent(5, courses.get(1), "CSS cho người mới", "video", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 14, 2, now));
        courseContents.add(new CourseContent(6, courses.get(1), "Tài liệu tham khảo CSS", "document", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", null, 3, now));

        courseContents.add(new CourseContent(7, courses.get(2), "Mô hình ER", "video", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 20, 1, now));
        courseContents.add(new CourseContent(8, courses.get(2), "Chuẩn hóa dữ liệu", "document", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", null, 2, now));

        courseContents.add(new CourseContent(9, courses.get(3), "Cấu trúc Spring Boot", "video", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 25, 1, now));
        courseContents.add(new CourseContent(10, courses.get(3), "REST API với Spring", "video", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 30, 2, now));

        courseContents.add(new CourseContent(11, courses.get(4), "ML là gì?", "video", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 18, 1, now));
        courseContents.add(new CourseContent(12, courses.get(4), "Thuật toán học có giám sát", "document", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", null, 2, now));


    }
    public static MEMCourseContentRepository getInstance(){
        return instance;
    }
    @Override
    public List<CourseContent> getByCourseId(int courseId) {
        return courseContents.stream().filter(courseContent -> courseContent.getCourse().getId()==courseId).toList();
    }

    @Override
    public int size() {
        return courseContents.size();
    }

    @Override
    public void add(CourseContent courseContent) {
        courseContents.add(courseContent);
    }



}
