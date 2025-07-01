package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.EnrollClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.IEnrollClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLEnrollClassRoomRepository implements IEnrollClassRoomRepository {
    private IUserRepository userRepository;
    private IClassRoomRepository classRoomRepository;
    private static final SQLEnrollClassRoomRepository instance = new SQLEnrollClassRoomRepository();
    private SQLEnrollClassRoomRepository(){
        this.classRoomRepository = SQLClassRoomRepository.getInstance();
        this.userRepository = SQLUserRepository.getInstance();
    }
    public static SQLEnrollClassRoomRepository getInstance(){
        return instance;
    }
    @Override
    public List<ClassRoom> getClassesByUserId(int userId) {
        String sql = "select c.* from EnrollClassRoom ec " +
                "join ClassRoom c on ec.classRoomId = c.id " +
                "where ec.userId = ?";

        List<ClassRoom> classRooms = new ArrayList<>();

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ClassRoom classRoom = new ClassRoom();
                classRoom.setId(rs.getInt("id"));
                classRoom.setclassName(rs.getString("className"));
                classRoom.setCreateAt(rs.getTimestamp("createAt"));
                classRoom.setInviteCode(rs.getString("inviteCode"));
                classRoom.setCreateBy(userRepository.getById(rs.getInt("createBy")));
                classRooms.add(classRoom);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return classRooms;
    }

    @Override
    public List<EnrollClassRoom> getEnrollClassesByUserId(int userId) {
        String sql = "select * from EnrollClassRoom where userId = ?";
        List<EnrollClassRoom> list = new ArrayList<>();

        try (Connection connection = DbFacade.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EnrollClassRoom e = new EnrollClassRoom();


                e.setUser(userRepository.getById(userId));
                int classId =rs.getInt("classRoomId");
                e.setClassRoom(classRoomRepository.getAll().stream()
                        .filter(classRoom -> classRoom.getId()==classId).findFirst().get());

                e.setEnrollAt(rs.getDate("enrollAt"));
                e.setRole(rs.getInt("role"));

                list.add(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public List<EnrollClassRoom> getByClassId(int classId) {
        String sql = "select * from EnrollClassRoom where classRoomId = ?";
        List<EnrollClassRoom> list = new ArrayList<>();

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EnrollClassRoom e = new EnrollClassRoom();

                e.setUser(userRepository.getById(rs.getInt("userId")));

                e.setClassRoom(classRoomRepository.getAll().stream()
                        .filter(classRoom -> classRoom.getId()==classId).findFirst().get());

                e.setEnrollAt(rs.getDate("enrollAt"));
                e.setRole(rs.getInt("role"));

                list.add(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public void remove(EnrollClassRoom enrollClassRoom) {
        String sql = "delete from EnrollClassRoom where userId = ? and classRoomId = ?";

        try (Connection connection = DbFacade.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, enrollClassRoom.getUser().getId());
            ps.setInt(2, enrollClassRoom.getClassRoom().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(EnrollClassRoom enrollClassRoom) {
        String sql = "insert into EnrollClassRoom (userId, classRoomId, enrollAt, role) values (?, ?, ?, ?)";

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, enrollClassRoom.getUser().getId());
            ps.setInt(2, enrollClassRoom.getClassRoom().getId());
            ps.setTimestamp(3, new Timestamp(enrollClassRoom.getEnrollAt().getTime()));
            ps.setInt(4, enrollClassRoom.getRole());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

    }
}
