package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.CourseContent;
import org.luans1mple.lmscore.controller.repositories.ICourseContentRepository;
import org.luans1mple.lmscore.controller.repositories.ICourseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCourseContentRepository implements ICourseContentRepository {
    private static final SQLCourseContentRepository instance = new SQLCourseContentRepository();
    private ICourseRepository courseRepository;
    private SQLCourseContentRepository(){
        this.courseRepository = SQLCourseRepository.getInstance();
    }
    public static SQLCourseContentRepository getInstance(){
        return instance;
    }
    @Override
    public List<CourseContent> getByCourseId(int courseId) {
        String sql = "select * from CourseContent where courseId = ?";
        List<CourseContent> contents = new ArrayList<>();

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CourseContent content = new CourseContent();
                content.setId(rs.getInt("id"));

                content.setCourse(courseRepository.getById(courseId));

                content.setTitle(rs.getString("title"));
                content.setType(rs.getString("type"));
                content.setContentUrl(rs.getString("contentUrl"));
                content.setDuration(rs.getInt("duration"));
                content.setOrderIndex(rs.getInt("orderIndex"));
                content.setCreatedAt(rs.getDate("createdAt"));

                contents.add(content);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contents;
    }

    @Override
    public int size() {
        String sql = "select count(*) from CourseContent";

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public void add(CourseContent courseContent) {
        String sql = "insert into CourseContent (courseId, title, type, contentUrl, duration, orderIndex, createdAt) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, courseContent.getCourse().getId());
            ps.setString(2, courseContent.getTitle());
            ps.setString(3, courseContent.getType());
            ps.setString(4, courseContent.getContentUrl());
            ps.setObject(5, courseContent.getDuration());
            ps.setInt(6, courseContent.getOrderIndex());
            ps.setTimestamp(7, new Timestamp(courseContent.getCreatedAt().getTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("lỗi khi thêm CourseContent", e);
        }
    }

    public static void main(String[] args) {
        SQLCourseContentRepository dao = instance;
        System.out.println(instance.getByCourseId(1).size());
    }
}
