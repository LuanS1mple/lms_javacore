package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.service.ICourseService;
import org.luans1mple.lmscore.controller.service.IEnrollCourseService;
import org.luans1mple.lmscore.controller.service.impl.CourseService;
import org.luans1mple.lmscore.controller.service.impl.EnrollCourseService;

import java.util.List;
import java.util.Scanner;

public class UserSearchCourseCLI implements Runnable{
    private ICourseService courseService;
    private IEnrollCourseService enrollCourseService;
    public UserSearchCourseCLI(){
        this.courseService=new CourseService();
        this.enrollCourseService=new EnrollCourseService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Điền từ khóa tìm kiếm khóa học: ");
        String pattern = sc.nextLine().trim();
        int userId = SessionCLI.getInstance().getUserId();
        List<Course> unEnrollCourses = enrollCourseService.getUnEnrollCourseByTittle(pattern, userId);
        List<Course> enrolledCourses = enrollCourseService.getEnrolledCourseByTitle(pattern,userId);
        if(unEnrollCourses.isEmpty() && enrolledCourses.isEmpty()){
            System.out.println("Not found");
            return;
        }
        while (true) {
            System.out.println("Kết quả tìm kiếm: ");
            System.out.println("A. Các khóa học đã đăng kí");
            int index = 1;
            for (int i = 0; i < enrolledCourses.size(); i++) {
                System.out.println(index + ". " + enrolledCourses.get(i).toString());
                index++;
            }
            System.out.println("B. Các khóa học chưa đăng kí:");
            for (int i = 0; i < unEnrollCourses.size(); i++) {
                System.out.println(index + ". " + unEnrollCourses.get(i).toString());
                index++;
            }

            System.out.println("Lựa chọn của bạn: ");
            int choice = sc.nextInt();

            if (choice >= 1 && choice <= enrolledCourses.size()) {
                Course selectedCourse = enrolledCourses.get(choice - 1);
                UserDetailCourseCLI userDetailCourseCLI = new UserDetailCourseCLI(selectedCourse);
                userDetailCourseCLI.run();
                break;
            } else if (choice > enrolledCourses.size() && choice <= enrolledCourses.size() + unEnrollCourses.size()) {
                Course selectedCourse = unEnrollCourses.get(choice - enrolledCourses.size() - 1);
                enrollCourseService.add(userId, selectedCourse.getId());
                System.out.println("Đăng kí thành công");
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }
}
