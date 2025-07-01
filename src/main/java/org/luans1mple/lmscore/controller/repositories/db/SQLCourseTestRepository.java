package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.CourseTest;
import org.luans1mple.lmscore.controller.repositories.ICourseRepository;
import org.luans1mple.lmscore.controller.repositories.ICourseTestRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLCourseTestRepository implements ICourseTestRepository {
    private static final SQLCourseTestRepository instance = new SQLCourseTestRepository();
    private ICourseRepository courseRepository;
    private SQLCourseTestRepository(){
        this.courseRepository = SQLCourseRepository.getInstance();
    }
    public static SQLCourseTestRepository getInstance(){
        return instance;
    }
    @Override
    public List<CourseTest> getByCourseId(int courseId) {
        String sql = "select * from CourseTest where courseId = ?";
        List<CourseTest> tests = new ArrayList<>();

        try (Connection connection = DbFacade.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CourseTest test = new CourseTest();
                test.setId(rs.getInt("id"));

                test.setCourse(courseRepository.getById(courseId));

                test.setTestTittle(rs.getString("testTitle"));
                test.setTestDescription(rs.getString("testDescription"));
                test.setType(rs.getString("type"));
                test.setTestUrl(rs.getString("testUrl"));
                test.setMaxScore(rs.getInt("maxScore"));
                test.setDuration(rs.getInt("duration"));
                test.setStatus(rs.getInt("status"));

                tests.add(test);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tests;
    }

    @Override
    public CourseTest getById(int id) {
        String sql = "select * from CourseTest where id = ?";

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CourseTest test = new CourseTest();
                test.setId(rs.getInt("id"));

                test.setCourse(courseRepository.getById(rs.getInt("courseId")));

                test.setTestTittle(rs.getString("testTitle"));
                test.setTestDescription(rs.getString("testDescription"));
                test.setType(rs.getString("type"));
                test.setTestUrl(rs.getString("testUrl"));
                test.setMaxScore(rs.getInt("maxScore"));
                test.setDuration(rs.getInt("duration"));
                test.setStatus(rs.getInt("status"));

                return test;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void add(CourseTest courseTest) {
        String sql = "insert into CourseTest (courseId, testTitle, testDescription, type, testUrl, maxScore, duration, status) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, courseTest.getCourse().getId());
            ps.setString(2, courseTest.getTestTittle());
            ps.setString(3, courseTest.getTestDescription());
            ps.setString(4, courseTest.getType());
            ps.setString(5, courseTest.getTestUrl());
            ps.setInt(6, courseTest.getMaxScore());
            ps.setInt(7, courseTest.getDuration());
            ps.setInt(8, courseTest.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        String sql = "select count(*) from CourseTest";

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("lỗi khi đếm CourseTest", e);
        }

        return 0;
    }

    public static void main(String[] args) {
        SQLCourseTestRepository dao = instance;
        System.out.println(instance.size());
    }
}
