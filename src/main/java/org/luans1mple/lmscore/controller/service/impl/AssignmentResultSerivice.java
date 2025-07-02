package org.luans1mple.lmscore.controller.service.impl;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.Assignment;
import org.luans1mple.lmscore.controller.model.dbo.AssignmentResult;
import org.luans1mple.lmscore.controller.repositories.IAssignmentResultRepository;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLAssignmentResultRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLUserRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMAssignmentResultRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMUserRepository;
import org.luans1mple.lmscore.controller.service.IAssignmentResultService;
import org.luans1mple.lmscore.controller.service.IUserService;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class AssignmentResultSerivice implements IAssignmentResultService {
    private IAssignmentResultRepository assignmentResultRepository;
    private IUserRepository userRepository;
    private static final int unMarkedNumber = -1;
    private static final int lateNumber =1;
    public AssignmentResultSerivice(){
//        this.userRepository = MEMUserRepository.getInstance();
        this.userRepository = SQLUserRepository.getInstance();
//        assignmentResultRepository = MEMAssignmentResultRepository.getInstance();
        this.assignmentResultRepository = SQLAssignmentResultRepository.getInstance();
    }
    public static int getUnMarkedNumber(){
        return unMarkedNumber;
    }
    public static int getLateNumber(){
        return lateNumber;
    }
    @Override
    public List<AssignmentResult> getByAssignment(int assignmentId) {
        return assignmentResultRepository.getByAssignment(assignmentId);
    }

    @Override
    public List<AssignmentResult> getUnMarkedAssignment(int assignmentId) {
        return getByAssignment(assignmentId).stream()
                .filter(assignmentResult -> assignmentResult.getMark()==unMarkedNumber).toList();
    }

    @Override
    public List<AssignmentResult> getMarkedAssignment(int assignmentId) {
        return getByAssignment(assignmentId).stream()
                .filter(assignmentResult -> assignmentResult.getMark()!=unMarkedNumber).toList();
    }

    @Override
    public List<AssignmentResult> search(String pattern,int assignmentId) {
        return getUnMarkedAssignment(assignmentId).stream()
                .filter(result ->
                        result.getUser().getFullName().toLowerCase().contains(pattern.toLowerCase())).toList();
    }

    @Override
    public void mark(int score,String commnet, AssignmentResult assignmentResult) {
        assignmentResultRepository.mark(score,commnet,assignmentResult);
    }

    @Override
    public void downLoadSubmission(AssignmentResult assignmentResult) {
        System.out.println("Chuẩn bị tải bài làm của: " + assignmentResult.getUser().getFullName());

        SwingUtilities.invokeLater(() -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn thư mục để lưu tài liệu");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            // Tạo frame ẩn để đảm bảo JFileChooser hiện ra trước
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(false);

            int returnValue = chooser.showSaveDialog(frame);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = chooser.getSelectedFile();
                Path sourcePath = Paths.get(assignmentResult.getSubmissionUrl());
                Path destinationPath = selectedDirectory.toPath().resolve(sourcePath.getFileName());

                if (!Files.exists(sourcePath)) {
                    JOptionPane.showMessageDialog(null, "File nguồn không tồn tại: " + sourcePath);
                    return;
                }

                try {
                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(null, "Đã tải file về: " + destinationPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi khi sao chép: " + e.getMessage());
                }
            } else {
                System.out.println("Người dùng đã hủy chọn thư mục.");
            }
        });
    }

    @Override
    public boolean submit( Assignment assignment) {
        AssignmentResult assignmentResult = new AssignmentResult();
        int userId = SessionCLI.getInstance().getUserId();
        assignmentResult.setUser(userRepository.getById(userId));
        assignmentResult.setAssignment(assignment);
        assignmentResult.setSubmissionUrl(getSubmissionUrl());
        assignmentResult.setDoneAt(LocalDateTime.now());
        assignmentResult.setMark(AssignmentResultSerivice.getUnMarkedNumber());
        assignmentResult.setComment(null);
        assignmentResult.setStatus(isLateSubmit(assignment.getEndAt()));

        if(this.isSubmitBefore(assignmentResult) && this.isMarkedBefore(assignmentResult)){
            return false;
        }
        if(this.isSubmitBefore(assignmentResult) && !this.isMarkedBefore(assignmentResult)){
            this.replaceSubmit(assignmentResult);
            return true;
        }
        assignmentResultRepository.add(assignmentResult);
        return  true;
    }

    @Override
    public String getSubmissionUrl() {
        final String[] resultPath = {null};

        try {
            // Bắt buộc gọi từ EDT để an toàn
            SwingUtilities.invokeAndWait(() -> {
                JFrame frame = new JFrame();
                frame.setAlwaysOnTop(true);
                frame.setUndecorated(true);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn một file");

                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    resultPath[0] = selectedFile.getAbsolutePath();
                }

                frame.dispose(); // Giải phóng frame ẩn
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultPath[0];
    }
    public int isLateSubmit(LocalDateTime submitTime){
        if(LocalDateTime.now().isAfter(submitTime)) return 1;
        else return 0;
    }

    @Override
    public AssignmentResult getResult(int userId, int assignmentId) {
        try {
            return this.getByAssignment(assignmentId).stream()
                    .filter(assignmentResult -> assignmentResult.getUser().getId() == userId)
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public boolean isSubmitBefore(AssignmentResult assignmentResult) {
        AssignmentResult result =this.getResult(assignmentResult.getUser().getId(),assignmentResult.getAssignment().getId());
        return result!=null;
    }

    @Override
    public boolean isMarkedBefore(AssignmentResult assignmentResult) {
        AssignmentResult result =this.getResult(assignmentResult.getUser().getId(),assignmentResult.getAssignment().getId());
        return (result!=null && result.getMark()!= AssignmentResultSerivice.getUnMarkedNumber());
    }

    @Override
    public void replaceSubmit(AssignmentResult assignmentResult) {
        assignmentResultRepository.update(assignmentResult);
    }
}
