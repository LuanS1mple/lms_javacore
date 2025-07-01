package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.repositories.IClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLClassRoomRepository implements IClassRoomRepository {
    private static final SQLClassRoomRepository instance = new SQLClassRoomRepository();
    private IUserRepository userRepository;
    private SQLClassRoomRepository(){
        userRepository = SQLUserRepository.getInstance();
    }
    public static SQLClassRoomRepository getInstance(){
        return instance;
    }
    @Override
    public List<ClassRoom> getAll() {
        String sql = "select * from ClassRoom";
        List<ClassRoom> classRooms = new ArrayList<>();

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ClassRoom classRoom = new ClassRoom();
                classRoom.setId(rs.getInt("id"));
                classRoom.setclassName(rs.getString("className"));
                classRoom.setCreateAt(rs.getTimestamp("createAt"));
                classRoom.setInviteCode(rs.getString("inviteCode"));

                int userId = rs.getInt("createBy");
                classRoom.setCreateBy(userRepository.getById(userId));

                classRooms.add(classRoom);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return classRooms;
    }

    @Override
    public void add(ClassRoom c) {
        String sql = "insert into ClassRoom(className, createAt, createBy, inviteCode) values (?, ?, ?, ?)";

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, c.getclassName());
            ps.setTimestamp(2, new Timestamp(c.getCreateAt().getTime()));
            ps.setInt(3, c.getCreateBy().getId());
            ps.setString(4, c.getInviteCode());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClassRoom> getByUserId(int userId) {
        return List.of();
    }

    @Override
    public ClassRoom getByCode(String inviteCode) {
        String sql = "select * from ClassRoom where inviteCode = ?";

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, inviteCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ClassRoom classRoom = new ClassRoom();
                classRoom.setId(rs.getInt("id"));
                classRoom.setclassName(rs.getString("className"));
                classRoom.setCreateAt(rs.getTimestamp("createAt"));
                classRoom.setInviteCode(rs.getString("inviteCode"));

                int creatorId = rs.getInt("createBy");
                classRoom.setCreateBy(userRepository.getById(creatorId));

                return classRoom;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static void main(String[] args) {
        SQLClassRoomRepository dao = instance;
        System.out.println(instance.getByCode("JAVA123").getclassName());
    }
}
