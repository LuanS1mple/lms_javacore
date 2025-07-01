package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.CourseContent;
import org.luans1mple.lmscore.controller.model.dbo.CourseTest;
import org.luans1mple.lmscore.controller.model.dbo.CourseTestResult;
import org.luans1mple.lmscore.controller.service.ICourseContentService;
import org.luans1mple.lmscore.controller.service.ICourseTestResultService;
import org.luans1mple.lmscore.controller.service.ICourseTestService;
import org.luans1mple.lmscore.controller.service.impl.CourseContentService;
import org.luans1mple.lmscore.controller.service.impl.CourseTestResultService;
import org.luans1mple.lmscore.controller.service.impl.CourseTestService;

import java.util.List;
import java.util.Scanner;

public class UserDetailCourseCLI implements Runnable{
    private Course currentCourse;
    private ICourseTestService courseTestService;
    private ICourseContentService courseContentService;
    private ICourseTestResultService courseTestResultService;
    public UserDetailCourseCLI(Course course){
        this.currentCourse=course;
        courseTestResultService = new CourseTestResultService();
        courseTestService= new CourseTestService();
        courseContentService = new CourseContentService();
    }
    @Override
    public void run() {
        int courseId = currentCourse.getId();
        int index =1;
        Scanner sc = new Scanner(System.in);
        List<CourseContent> contents = courseContentService.getByCourseId(courseId);
        List<CourseTest> tests = courseTestService.getByCourseId(courseId);
        flag:
        System.out.println("Tài liệu khóa học: ");
        for (int i = 0; i < contents.size(); i++) {
            System.out.println("    "+index+". "+contents.get(i).toString());
            index++;
        }
        System.out.println("Bài kiểm tra của khóa học: ");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println("    "+index+". "+tests.get(i).toString());
            index++;
        }
        System.out.println(index+". Tìm kiếm học liệu");
        System.out.println("0. Thoát");
        System.out.println("Nhập lựa chọn của bạn: ");
        int choice = sc.nextInt();



        if(choice > 0 && choice <= contents.size()){
            CourseContent selected = contents.get(choice-1);
            courseContentService.downLoadCourseContent(selected);
        }
        else if(choice> contents.size() && choice <= contents.size()+tests.size()){
            CourseTest test = tests.get(choice- contents.size()-1);
            detailTestCourse(test);
        }
        else if(choice==index){
            UserSearchCourseMeterialCLI userSearchCourseMeterialCLI = new UserSearchCourseMeterialCLI(this.currentCourse);
            userSearchCourseMeterialCLI.run();
        }
        else if(choice==0){
            return;
        }
        else{
            System.out.println("Lựa chọn không hợp lệ");
        }
    }
    public void detailTestCourse(CourseTest test){
        System.out.println("1. Tải tài bài test");
        System.out.println("2. Nộp bài test");
        System.out.println("3. Kết quả: ");
        System.out.println("0. Thoát");

        int userId = SessionCLI.getInstance().getUserId();
        List<CourseTestResult> results = courseTestResultService.getResults(userId, test.getId());
        for (int i = 0; i < results.size(); i++) {
            CourseTestResult current = results.get(i);
            System.out.println(current.toString());
        }

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.print("Nhập lựa chọn của bạn: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice >= 0 && choice <= 3) {
                    break;
                } else {
                    System.out.println("Vui lòng chọn số trong khoảng từ 0 đến 3.");
                }
            } else {
                System.out.println("Vui lòng nhập một số nguyên.");
                sc.next();
            }
        }

        if (choice == 1) {
            courseTestService.downLoadCourseTest(test);
        } else if (choice == 2) {
            courseTestResultService.submit(userId, test.getId());
        } else if (choice == 0) {
            return;
        }

    }
}
