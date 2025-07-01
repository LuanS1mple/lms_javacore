package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.mapper.UserMapper;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.model.dto.request.UserRequest;
import org.luans1mple.lmscore.controller.service.IUserService;
import org.luans1mple.lmscore.controller.service.impl.UserService;

import java.util.Scanner;

public class UserRegisterCLI implements Runnable{
    private IUserService userService;
    public UserRegisterCLI(){
        this.userService = new UserService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter full name:");
        String fullName = sc.nextLine();
        System.out.println("Enter email");
        String email = sc.nextLine();
        while (userService.isExistEmail(email)) {
            System.out.println("Email đã dùng rồi, hãy nhập mail khác");
            email = sc.nextLine();
        }
        System.out.println("Enter password");
        String password = sc.nextLine();
        System.out.println("Enter phone");
        String phone = sc.nextLine();
        System.out.println("Enter address");
        String address= sc.nextLine();
        UserRequest userRequest = new UserRequest(fullName,email,password,phone,address,null);
        User user = UserMapper.userRequesttoUser(userRequest);
        userService.add(user);
        System.out.println("Thêm tài khoản thành công");
    }
}
