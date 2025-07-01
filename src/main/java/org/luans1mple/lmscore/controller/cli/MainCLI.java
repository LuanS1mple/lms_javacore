package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.service.ICourseService;
import org.luans1mple.lmscore.controller.service.impl.CourseService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainCLI implements Runnable{
    private ICourseService courseService;
    public MainCLI(){
        courseService = new CourseService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. User");
            System.out.println("2. Administrator");
            System.out.println("0. Thoát");

            int choice = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Nhập lựa chọn của bạn: ");
                try {
                    choice = sc.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Vui lòng nhập một số nguyên.");
                    sc.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    UserCLI userCLI = new UserCLI();
                    userCLI.run();
                    break;
                case 2:
                    AdministratorCLI administratorCLI = new AdministratorCLI();
                    administratorCLI.run();
                    break;
                case 0:
                    System.out.println("Shutdown....");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }
}
