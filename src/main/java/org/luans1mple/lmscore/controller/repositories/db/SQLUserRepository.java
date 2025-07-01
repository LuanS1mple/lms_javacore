package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserRepository implements IUserRepository {
    private static final SQLUserRepository INSTANCE = new SQLUserRepository();

    private SQLUserRepository() {
    }

    public static SQLUserRepository getInstance() {
        return INSTANCE;
    }
    @Override
    public List<User> getAll() {
        String sql ="select * from [user]";
        try (Connection connection = DbFacade.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setAvatarUrl(rs.getString("avatarUrl"));
                user.setRole(rs.getInt("role"));
                user.setStatus(rs.getInt("status"));
                user.setCreatedAt(rs.getDate("createdAt"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByEmail(String email) {
        String sql ="select * from [user] where email =?";
        try (Connection connection = DbFacade.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setAvatarUrl(rs.getString("avatarUrl"));
                user.setRole(rs.getInt("role"));
                user.setStatus(rs.getInt("status"));
                user.setCreatedAt(rs.getDate("createdAt"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO [dbo].[User] " +
                "([email], [password], [fullName], [phone], [address], [avatarUrl], [role], [status], [createdAt]) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getAvatarUrl());
            ps.setInt(7, user.getRole());
            ps.setInt(8, user.getStatus());
            ps.setTimestamp(9, new Timestamp(user.getCreatedAt().getTime()));

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User getById(int id) {
        String sql ="select * from [user] where id =?";
        try (Connection connection = DbFacade.getConnection()) {
            // step 2.1: Statement -> execute -> ResultSet
            // Statement stmt = conn.createStatement();
            // ResultSet rs = stmt.executeQuery("select * from user");

            // step 2.2: PreparedStatement -> execute -> ResultSet
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // step 3: map ResultSet -> POJO
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setAvatarUrl(rs.getString("avatarUrl"));
                user.setRole(rs.getInt("role"));
                user.setStatus(rs.getInt("status"));
                user.setCreatedAt(rs.getDate("createdAt"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }

    @Override
    public void remove(User user) {
        String sql ="update [user] set status =0 where id =?";
        try (Connection connection = DbFacade.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SQLUserRepository dao = getInstance();
        System.out.println(dao.findByEmail("alice@example.com").toString());
    }
}
