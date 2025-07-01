package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.Assignment;
import org.luans1mple.lmscore.controller.repositories.IAssignmentRepository;
import org.luans1mple.lmscore.controller.repositories.IClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLAssignmentRepository implements IAssignmentRepository {
    private static final SQLAssignmentRepository instance = new SQLAssignmentRepository();
    private IClassRoomRepository classRoomRepository;
    private IUserRepository userRepository;
    private SQLAssignmentRepository(){
        userRepository = SQLUserRepository.getInstance();
        classRoomRepository = SQLClassRoomRepository.getInstance();
    }
    public static SQLAssignmentRepository getInstance(){
        return instance;
    }
    @Override
    public List<Assignment> getAssignmentByClass(int classId) {
        String sql = "select * from Assignment where classRoomId =?";
        try(Connection connection = DbFacade.getConnection()){
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,classId);
            ResultSet rs =st.executeQuery();
            List<Assignment> assignments = new ArrayList<>();
            while(rs.next()){
                Assignment a = new Assignment();
                a.setId(rs.getInt("id"));
                a.setClassRoom(classRoomRepository.getAll().stream()
                        .filter(classRoom -> classRoom.getId()==classId).findFirst().get());
                a.setTittle(rs.getString("title"));
                a.setDescription(rs.getString("description"));
                a.setAssignmentUrl(rs.getString("assignmentUrl"));
                a.setStartAt(rs.getTimestamp("startAt").toLocalDateTime());
                a.setEndAt(rs.getTimestamp("endAt").toLocalDateTime());
                a.setMaxScore(rs.getInt("maxScore"));
                a.setCreateBy(userRepository.getById(rs.getInt("createBy")));
                a.setAllowLate(rs.getBoolean("isAllowLate"));
                assignments.add(a);
            }
            return assignments;
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        String sql ="select count(*) from Assignment";
        try(Connection connection=DbFacade.getConnection()){
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return  0;
    }

    @Override
    public void add(Assignment assignment) {
        String sql = "insert into Assignment (classRoomId, title, description, assignmentUrl, startAt, endAt, maxScore, createBy, isAllowLate) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, assignment.getClassRoom().getId());
            ps.setString(2, assignment.getTittle());
            ps.setString(3, assignment.getDescription());
            ps.setString(4, assignment.getAssignmentUrl());
            ps.setTimestamp(5, Timestamp.valueOf(assignment.getStartAt()));
            ps.setTimestamp(6, Timestamp.valueOf(assignment.getEndAt()));
            ps.setInt(7, assignment.getMaxScore());
            ps.setInt(8, assignment.getCreateBy().getId());
            ps.setBoolean(9, assignment.isAllowLate());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SQLAssignmentRepository dao = instance;
        System.out.println(instance.size());
    }
}
