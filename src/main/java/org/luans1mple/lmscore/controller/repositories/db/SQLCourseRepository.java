package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.repositories.ICourseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCourseRepository implements ICourseRepository {
    private static final SQLCourseRepository instance= new SQLCourseRepository();
    private SQLCourseRepository(){}
    public static SQLCourseRepository getInstance(){
        return instance;
    }
    @Override
    public void addCoure(Course course) {
        String sql = "insert into course(title, description, category, level, duration, status, author, createdAt, updatedAt) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setString(3, course.getCategory());
            ps.setString(4, course.getLevel());
            ps.setInt(5, course.getDuration());
            ps.setString(6, course.getStatus());
            ps.setString(7, course.getAuthor());
            ps.setTimestamp(8, new Timestamp(course.getCreatedAt().getTime()));
            ps.setTimestamp(9, new Timestamp(course.getUpdatedAt().getTime()));

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> getAll() {
        String sql = "select * from course where status ='active'";
        try(Connection connection = DbFacade.getConnection()){
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Course> courses = new ArrayList<>();
            while(rs.next()){
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
            return courses;
        }
        catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public Course getById(int id) {
        String sql = "select * from course where id =?";
        try(Connection connection = DbFacade.getConnection()){
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
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
                return course;
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Course course) {
        String sql = "update [Course] set title = ?, description = ?, category = ?, level = ?, duration = ?, status = ?, author = ?, createdAt = ?, updatedAt = ? where id = ?";

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setString(3, course.getCategory());
            ps.setString(4, course.getLevel());
            ps.setInt(5, course.getDuration());
            ps.setString(6, course.getStatus());
            ps.setString(7, course.getAuthor());
            ps.setTimestamp(8, new Timestamp(course.getCreatedAt().getTime()));
            ps.setTimestamp(9, new Timestamp(course.getUpdatedAt().getTime()));
            ps.setInt(10, course.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Course course) {
        String sql = "update [Course] set status = ? where id = ?";

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, "inactive");
            ps.setInt(2, course.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> inactiveCourses() {
        String sql = "select * from course where status ='inactive'";
        try(Connection connection = DbFacade.getConnection()){
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Course> courses = new ArrayList<>();
            while(rs.next()){
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
            return courses;
        }
        catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        SQLCourseRepository dao = instance;
        System.out.println(dao.getAll().size());
    }
}
