package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.service.IUserService;
import org.luans1mple.lmscore.controller.service.impl.UserService;

import java.util.Scanner;

public class UserCourseManagerCLI implements Runnable{
    private IUserService userService;
    public UserCourseManagerCLI(){
        this.userService = new UserService();
    }
    @Override
    public void run() {
        int userId = SessionCLI.getInstance().getUserId();
        User user = userService.getById(userId);
        Scanner sc = new Scanner(System.in);
        System.out.println("Quản lí khóa học của "+ user.getFullName()+":");
        System.out.println("1. Các khóa học chưa đăng kí");
        System.out.println("2. Khóa học đã đăng kí");
        System.out.println("3. Tìm kiếm khóa học");
        System.out.println("0. Thoát");
        int choice = sc.nextInt();
        switch (choice){
            case 1: UserBrowsingCourseCLI userCourseManagerCLI = new UserBrowsingCourseCLI();
                userCourseManagerCLI.run();
                break;
            case 2: UserEnrolledCourseCLI userEnrolledCourseCLI = new UserEnrolledCourseCLI();
                userEnrolledCourseCLI.run();
                break;
            case 3:
                UserSearchCourseCLI userSearchCourseCLI = new UserSearchCourseCLI();
                userSearchCourseCLI.run();
                break;
            case 0:
                System.out.println("Shutdown");
                System.exit(0);
        }
    }
}
