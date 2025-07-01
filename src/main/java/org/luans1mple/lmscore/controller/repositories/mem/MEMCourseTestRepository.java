package org.luans1mple.lmscore.controller.repositories.mem;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.CourseTest;
import org.luans1mple.lmscore.controller.repositories.ICourseTestRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MEMCourseTestRepository implements ICourseTestRepository {
    private List<CourseTest> courseTests = new ArrayList<>();
    private static final MEMCourseTestRepository instance = new MEMCourseTestRepository();
    private MEMCourseTestRepository(){
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

        courseTests.add(new CourseTest(1, courses.get(0), "Bài kiểm tra Java cơ bản", "Kiểm tra kiến thức nhập môn Java", "quiz", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 100, 30, 1));
        courseTests.add(new CourseTest(2, courses.get(0), "OOP trong Java", "Đánh giá kiến thức OOP", "assignment", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 100, 45, 1));

        courseTests.add(new CourseTest(3, courses.get(1), "HTML & CSS Test", "Bài kiểm tra tổng hợp HTML và CSS", "quiz", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 100, 25, 1));
        courseTests.add(new CourseTest(4, courses.get(1), "JavaScript Basic", "Kiểm tra kiến thức JS cơ bản", "quiz", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 100, 30, 1));

        courseTests.add(new CourseTest(5, courses.get(2), "Kiểm tra mô hình dữ liệu", "Bài test ERD và normalization", "quiz", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 100, 40, 1));

        courseTests.add(new CourseTest(6, courses.get(3), "REST API Test", "Kiểm tra về cách xây dựng API với Spring Boot", "quiz", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 100, 30, 1));

        courseTests.add(new CourseTest(7, courses.get(4), "ML Fundamentals", "Bài kiểm tra kiến thức học máy cơ bản", "quiz", "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 100, 45, 1));

    }
    public static MEMCourseTestRepository getInstance(){
        return instance;
    }
    @Override
    public List<CourseTest> getByCourseId(int courseId) {
        return courseTests.stream().filter(courseTest -> courseTest.getCourse().getId()==courseId).toList();
    }

    @Override
    public CourseTest getById(int id) {

        return courseTests.stream()
                .filter(courseTests -> courseTests.getId()==id)
                .findFirst().get();
    }

    @Override
    public void add(CourseTest courseTest) {
        courseTests.add(courseTest);
    }

    @Override
    public int size() {
        return courseTests.size();
    }
}
