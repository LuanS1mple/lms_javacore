package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.service.IUserService;
import org.luans1mple.lmscore.controller.service.impl.UserService;

import java.util.Scanner;

public class UserLoginCLI implements Runnable {
    private IUserService userService;
    public UserLoginCLI(){
        this.userService= new UserService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email");
        String email = sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();
        if(userService.isCorrectAccount(email,password)){
            System.out.println("Đăng nhập thành công");
            User user = userService.getByEmail(email);
            SessionCLI.getInstance().setUserId(user.getId());
            UserHomeCLI userHomeCLI = new UserHomeCLI();
            userHomeCLI.run();
        }
        else{
            System.out.println("Sai tài khoản hoặc mật khẩu ");
        }
    }
}
