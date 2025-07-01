package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.service.ICourseService;
import org.luans1mple.lmscore.controller.service.IEnrollCourseService;
import org.luans1mple.lmscore.controller.service.impl.CourseService;
import org.luans1mple.lmscore.controller.service.impl.EnrollCourseService;
import org.luans1mple.lmscore.controller.ulties.Ulti;

import java.util.List;
import java.util.Scanner;

public class UserBrowsingCourseCLI implements Runnable{
    private ICourseService courseService;
    private IEnrollCourseService enrollCourseService;
    public UserBrowsingCourseCLI(){
        this.courseService = new CourseService();
        this.enrollCourseService=new EnrollCourseService();
    }
    @Override
    public void run() {
        System.out.println("Các khóa học chưa đăng kí:");
        int userId = SessionCLI.getInstance().getUserId();
        List<Course> courses= enrollCourseService.getUnEnrollCoursesById(userId);
        enrollCourseService.showUnEnrollCourses(userId);
        System.out.println("0. Thoát");
        System.out.println("Nhập khóa học muốn đăng kí:");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(choice==0) return;
        else{
            int selectedCourseInShow = choice-1;
            Course selectedCourse = courses.get(selectedCourseInShow);
            enrollCourseService.add(userId,selectedCourse.getId());
            System.out.println("Đăng kí thành công");
        }
    }
}
