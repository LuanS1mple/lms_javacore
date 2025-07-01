package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.EnrollCourse;
import org.luans1mple.lmscore.controller.repositories.IEnrollCourseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLEnrollCourseRepository implements IEnrollCourseRepository {
    private static final SQLEnrollCourseRepository instance = new SQLEnrollCourseRepository();
    private SQLEnrollCourseRepository(){

    }
    public static SQLEnrollCourseRepository getInstance(){
        return instance;
    }
    @Override
    public List<Course> getCoursesByUserId(int id) {
        String sql = "select c.* from EnrollCourse ec join Course c on ec.courseId = c.id where ec.userId = ?";
        List<Course> courses = new ArrayList<>();

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setTitle(rs.getString("title"));
                course.setDescription(rs.getString("description"));
                course.setCategory(rs.getString("category"));
                course.setLevel(rs.getString("level"));
                course.setDuration(rs.getInt("duration"));
                course.setStatus(rs.getString("status"));
                course.setAuthor(rs.getString("author"));
                course.setCreatedAt(rs.getTimestamp("createdAt"));
                course.setUpdatedAt(rs.getTimestamp("updatedAt"));
                courses.add(course);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courses;
    }

    @Override
    public void add(EnrollCourse enrollCourse) {
        String sql = "insert into EnrollCourse (userId, courseId, enrollAt) values (?, ?, ?)";

        try (Connection connection = DbFacade.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, enrollCourse.getUser().getId());
            ps.setInt(2, enrollCourse.getCourse().getId());
            ps.setTimestamp(3, new Timestamp(enrollCourse.getEnrollAt().getTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SQLEnrollCourseRepository dao = instance;
        System.out.println(instance.getCoursesByUserId(1).size());
    }
}
