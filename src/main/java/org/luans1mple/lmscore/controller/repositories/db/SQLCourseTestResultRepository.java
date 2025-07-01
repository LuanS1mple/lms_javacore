package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.CourseTest;
import org.luans1mple.lmscore.controller.model.dbo.CourseTestResult;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.ICourseTestRepository;
import org.luans1mple.lmscore.controller.repositories.ICourseTestResultRepository;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCourseTestResultRepository implements ICourseTestResultRepository {
    private IUserRepository userRepository;
    private ICourseTestRepository courseTestRepository;
    private static final SQLCourseTestResultRepository instance = new SQLCourseTestResultRepository();
    private SQLCourseTestResultRepository(){
        userRepository = SQLUserRepository.getInstance();
        this.courseTestRepository = SQLCourseTestRepository.getInstance();
    }
    public static SQLCourseTestResultRepository getInstance(){
        return instance;
    }
    @Override
    public void add(CourseTestResult result) {
        String sql = "insert into CourseTestResult (userId, courseTestId, mark, doneAt, status) values (?, ?, ?, ?, ?)";

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, result.getUser().getId());
            ps.setInt(2, result.getCourseTest().getId());
            ps.setInt(3, result.getMark());
            ps.setTimestamp(4, Timestamp.valueOf(result.getDoneAt()));
            ps.setInt(5, result.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CourseTestResult> getResults(int userId, int courseTestId) {
        String sql = "select * from CourseTestResult where userId = ? and courseTestId = ?";
        List<CourseTestResult> results = new ArrayList<>();

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setInt(2, courseTestId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CourseTestResult result = new CourseTestResult();

                result.setUser(userRepository.getById(rs.getInt("userId")));
                result.setCourseTest(courseTestRepository.getById(rs.getInt("courseTestId")));

                result.setMark(rs.getInt("mark"));
                result.setDoneAt(rs.getTimestamp("doneAt").toLocalDateTime());
                result.setStatus(rs.getInt("status"));

                results.add(result);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    public static void main(String[] args) {
        SQLCourseTestResultRepository dao =instance;
        System.out.println(instance.getResults(1,1).size());
    }
}
