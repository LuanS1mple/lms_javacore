package org.luans1mple.lmscore.controller.service.impl;

import org.luans1mple.lmscore.controller.model.dbo.CourseTest;
import org.luans1mple.lmscore.controller.model.dbo.CourseTestResult;
import org.luans1mple.lmscore.controller.repositories.ICourseTestRepository;
import org.luans1mple.lmscore.controller.repositories.ICourseTestResultRepository;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLCourseTestRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLCourseTestResultRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLUserRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMCourseTestRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMCourseTestResultRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMUserRepository;
import org.luans1mple.lmscore.controller.service.ICourseTestResultService;

import javax.swing.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class CourseTestResultService implements ICourseTestResultService {
    private ICourseTestResultRepository courseTestResultRepository;
    private IUserRepository userRepository;
    private ICourseTestRepository courseTestRepository;
    private static final int statusPassNumber =1;

    public CourseTestResultService(){
//        this.courseTestResultRepository = MEMCourseTestResultRepository.getInstance();
        this.courseTestResultRepository = SQLCourseTestResultRepository.getInstance();
//        this.userRepository = MEMUserRepository.getInstance();
        this.userRepository = SQLUserRepository.getInstance();
//        this.courseTestRepository = MEMCourseTestRepository.getInstance();
        this.courseTestRepository = SQLCourseTestRepository.getInstance();
    }
    public static int getStatusPassNumber(){
        return statusPassNumber;
    }
    @Override
    public void add(CourseTestResult result) {
        courseTestResultRepository.add(result);
    }

    @Override
    public List<CourseTestResult> getResults(int userId,int courseTestId) {
        return courseTestResultRepository.getResults(userId,courseTestId);
    }

    @Override
    public String getSubmitUrl() {
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

    @Override
    public void submit(int userId,int testId) {
        String url  = getSubmitUrl();
        int mark = autoMark(url,testId);
        CourseTest test = courseTestRepository.getById(testId);
        int status =(float) mark > test.getMaxScore() * CourseTestService.getRateScorePass()? 1:0;
        CourseTestResult courseTestResult = new CourseTestResult();
        courseTestResult.setUser(userRepository.getById(userId));
        courseTestResult.setCourseTest(test);
        courseTestResult.setMark(mark);
        courseTestResult.setDoneAt(LocalDateTime.now());
        courseTestResult.setStatus(status);
        courseTestResultRepository.add(courseTestResult);
    }

    @Override
    public int autoMark(String submitUrl, int testId) {
        return 100;
    }

    public static void main(String[] args) {
        CourseTestResultService courseTestResultService = new CourseTestResultService();
        courseTestResultService.getSubmitUrl();
    }
}
