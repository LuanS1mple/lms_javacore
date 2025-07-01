package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.service.IEnrollCourseService;
import org.luans1mple.lmscore.controller.service.impl.EnrollCourseService;

import java.util.List;
import java.util.Scanner;

public class UserEnrolledCourseCLI implements Runnable{
    private IEnrollCourseService enrollCourseService;
    public UserEnrolledCourseCLI(){
        this.enrollCourseService = new EnrollCourseService();
    }
    @Override
    public void run() {
        int userId = SessionCLI.getInstance().getUserId();
        enrollCourseService.showCourseEnrolled(userId);

        List<Course> courses = enrollCourseService.getCoursesById(userId);
        System.out.println("0. Thoát");

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.print("Nhập lựa chọn của bạn: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice >= 0 && choice <= courses.size()) {
                    break;
                } else {
                    System.out.println("Vui lòng chọn số trong khoảng từ 0 đến " + courses.size());
                }
            } else {
                System.out.println("Vui lòng nhập một số nguyên.");
                sc.next();
            }
        }

        if (choice == 0) {
            return;
        } else {
            int selectedCourseInShow = choice - 1;
            Course selectedCourse = courses.get(selectedCourseInShow);
            UserDetailCourseCLI userDetailCourseCLI = new UserDetailCourseCLI(selectedCourse);
            userDetailCourseCLI.run();
        }

    }
}
