package org.luans1mple.lmscore.controller.service.impl;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.CourseTest;
import org.luans1mple.lmscore.controller.repositories.ICourseTestRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLCourseTestRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMCourseTestRepository;
import org.luans1mple.lmscore.controller.service.ICourseTestService;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class CourseTestService implements ICourseTestService {
    private ICourseTestRepository courseTestRepository;
    private static final float rateScorePass = 0.5f;
    public static float getRateScorePass(){
        return  rateScorePass;
    }
    public CourseTestService(){
//        courseTestRepository = MEMCourseTestRepository.getInstance();
        courseTestRepository = SQLCourseTestRepository.getInstance();
    }
    @Override
    public List<CourseTest> getByCourseId(int courseId) {
        return courseTestRepository.getByCourseId(courseId);
    }

    @Override
    public void downLoadCourseTest(CourseTest test) {
        System.out.println("Chuẩn bị tải: " + test.getTestTittle());

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
                Path sourcePath = Paths.get(test.getTestUrl());
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
    public List<CourseTest> getByTittle(String pattern, int courseId) {
        return courseTestRepository.getByCourseId(courseId).stream()
                .filter(courseTest ->courseTest.getTestTittle().toLowerCase().contains(pattern.toLowerCase())).toList();
    }

    @Override
    public void add(CourseTest test) {
        courseTestRepository.add(test);
    }

    @Override
    public int size() {
        return courseTestRepository.size();
    }

    @Override
    public String getCourseTestUrl() {
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

}
