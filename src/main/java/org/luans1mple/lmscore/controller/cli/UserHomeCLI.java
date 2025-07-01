package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.service.IUserService;
import org.luans1mple.lmscore.controller.service.impl.UserService;

import java.util.Scanner;

public class UserHomeCLI implements Runnable{
    private IUserService userService;
    public UserHomeCLI(){
        this.userService = new UserService();
    }
    @Override
    public void run() {
        int userId = SessionCLI.getInstance().getUserId();
        User user = userService.getById(userId);
        Scanner sc = new Scanner(System.in);

        System.out.println("Hello " + user.getFullName());

        while (true) {
            System.out.println("Các chức năng:");
            System.out.println("1. Quản lý khóa học");
            System.out.println("2. Quản lí lớp học");
            System.out.println("0. Thoát");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1:
                    UserCourseManagerCLI userCourseManagerCLI = new UserCourseManagerCLI();
                    userCourseManagerCLI.run();
                    break;
                case 2:
                    UserClassManagerCLI userClassManagerCLI = new UserClassManagerCLI();
                    userClassManagerCLI.run();
                    break;
                case 0:
                    System.out.println("Shutdown");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
            if(choice==0) break;
        }

    }
}
