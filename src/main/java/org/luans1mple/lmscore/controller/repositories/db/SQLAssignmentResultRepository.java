package org.luans1mple.lmscore.controller.repositories.db;

import org.luans1mple.lmscore.controller.model.dbo.Assignment;
import org.luans1mple.lmscore.controller.model.dbo.AssignmentResult;
import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IAssignmentRepository;
import org.luans1mple.lmscore.controller.repositories.IAssignmentResultRepository;
import org.luans1mple.lmscore.controller.repositories.IClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLAssignmentResultRepository implements IAssignmentResultRepository {
    private IUserRepository userRepository;
    private IClassRoomRepository classRoomRepository;
    private IAssignmentRepository assignmentRepository;
    private static final SQLAssignmentResultRepository instance = new SQLAssignmentResultRepository();
    private SQLAssignmentResultRepository(){
        this.classRoomRepository = SQLClassRoomRepository.getInstance();
        this.userRepository = SQLUserRepository.getInstance();
        this.assignmentRepository = SQLAssignmentRepository.getInstance();
    }
    public static SQLAssignmentResultRepository getInstance(){
        return instance;
    }
    @Override
    public List<AssignmentResult> getByAssignment(int assignmentId) {
        String sql = "select * from AssignmentResult where assignmentId = ?";
        List<AssignmentResult> results = new ArrayList<>();

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, assignmentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AssignmentResult result = new AssignmentResult();

                result.setUser(userRepository.getById(rs.getInt("userId")));
                result.setAssignment(getAssignmentById(assignmentId));

                result.setDoneAt(rs.getTimestamp("doneAt").toLocalDateTime());
                result.setSubmissionUrl(rs.getString("submissionUrl"));
                result.setMark(rs.getInt("mark"));
                result.setComment(rs.getString("comment"));
                result.setStatus(rs.getInt("status"));

                results.add(result);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách AssignmentResult theo assignmentId", e);
        }

        return results;
    }

    @Override
    public void mark(int score, String comment, AssignmentResult assignmentResult) {
        String sql = "update AssignmentResult set mark = ?, comment = ?" +
                "where userId = ? and assignmentId = ?";

        try (Connection connection = DbFacade.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, score);
            ps.setString(2, comment);
            ps.setInt(3, assignmentResult.getUser().getId());
            ps.setInt(4, assignmentResult.getAssignment().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(AssignmentResult assignmentResult) {
        String sql = "insert into AssignmentResult (userId, assignmentId, doneAt, submissionUrl, mark, comment, status) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, assignmentResult.getUser().getId());
            ps.setInt(2, assignmentResult.getAssignment().getId());
            ps.setTimestamp(3, Timestamp.valueOf(assignmentResult.getDoneAt()));
            ps.setString(4, assignmentResult.getSubmissionUrl());
            ps.setInt(5, assignmentResult.getMark());
            ps.setString(6, assignmentResult.getComment());
            ps.setInt(7, assignmentResult.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        String sql = "select count(*) from AssignmentResult";

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
    public Assignment getAssignmentById(int id) {
        String sql = "select * from Assignment where id = ?";

        try (Connection connection = DbFacade.getConnection()){
             PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("id"));
                assignment.setTittle(rs.getString("title"));
                assignment.setDescription(rs.getString("description"));
                assignment.setAssignmentUrl(rs.getString("assignmentUrl"));
                assignment.setStartAt(rs.getTimestamp("startAt").toLocalDateTime());
                assignment.setEndAt(rs.getTimestamp("endAt").toLocalDateTime());
                assignment.setMaxScore(rs.getInt("maxScore"));
                assignment.setAllowLate(rs.getBoolean("isAllowLate"));

                assignment.setClassRoom(classRoomRepository.getAll().stream()
                        .filter(classRoom -> classRoom.getId()==id).findFirst().get());


                assignment.setCreateBy(userRepository.getById(rs.getInt("createBy")));

                return assignment;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy Assignment theo ID", e);
        }

        return null;
    }

}
