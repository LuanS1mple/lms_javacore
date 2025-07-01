package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;

import java.util.Scanner;

public class UserCLI implements Runnable{
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng kí");
            System.out.println("0. Thoát/Logout");
            System.out.print("Nhập lựa chọn của bạn: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Hãy nhập chính xác");
                continue;
            }

            switch (choice) {
                case 1:
                    UserLoginCLI userLoginCLI = new UserLoginCLI();
                    userLoginCLI.run();
                    break;
                case 2:
                    UserRegisterCLI userRegisterCLI = new UserRegisterCLI();
                    userRegisterCLI.run();
                    break;
                case 0:
                    if(SessionCLI.getInstance().getUserId()==0)
                    {
                        System.out.println("Shutdown");
                        return;
                    }
                    else{
                        SessionCLI.getInstance().setUserId(0);
                        return;
                    }
                default:
                    System.out.println("Hãy nhập lại");
            }
            System.out.println();
        }
    }
}
