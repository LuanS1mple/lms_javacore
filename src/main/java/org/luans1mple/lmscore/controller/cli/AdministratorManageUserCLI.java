package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.service.IUserService;
import org.luans1mple.lmscore.controller.service.impl.UserService;

import java.util.List;
import java.util.Scanner;

public class AdministratorManageUserCLI implements Runnable{
    private IUserService userService;
    public AdministratorManageUserCLI(){
        this.userService = new UserService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.println("Quản lí người dùng: ");
            System.out.println("1. Danh sách người dùng");
            System.out.println("2. Tìm kiếm");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        browsingUsers();
                        break;
                    case 2:
                        searchingUsers();
                        break;
                    case 0:
                        System.out.println("Đã thoát");
                        return;
                    default:
                        System.out.println("Vui lòng chọn 0, 1 hoặc 2.");
                }
            } else {
                System.out.println("Vui lòng nhập số nguyên.");
                sc.nextLine();
            }
        }
    }
    public void browsingUsers(){
        Scanner sc = new Scanner(System.in);
        List<User> users = userService.getAll();

        while (true) {
            System.out.println("Danh sách người dùng: ");
            showUsers(users);
            System.out.println("Bấm A để sắp xếp theo tên  -  B để sắp xếp theo ngày tạo tài khoản - Chọn tài khoản để cấm - Q để thoát");
            System.out.println("Lựa chọn của bạn: ");

            String choice = sc.nextLine().trim();

            if (choice.equalsIgnoreCase("A")) {
                users = userService.orderByName();
                showUsers(users);
            } else if (choice.equalsIgnoreCase("B")) {
                users = userService.orderByCreateDate();
                showUsers(users);
            }
            else if(choice.equalsIgnoreCase("Q")){
                break;
            }else {
                try {
                    int index = Integer.parseInt(choice);
                    if (index >= 1 && index <= users.size()) {
                        User selectedUser = users.get(index - 1);
                        userService.remove(selectedUser);
                        System.out.println("Đã cấm người dùng: " + selectedUser.getFullName());
                    } else {
                        System.out.println("Số không nằm trong danh sách. Vui lòng chọn lại.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập A, B, Q hoặc số thứ tự.");
                }
            }

            System.out.println();
        }

    }
    public void searchingUsers(){
        System.out.println("Điền từ khóa: ");
        Scanner sc = new Scanner(System.in);
        String pattern = sc.nextLine();
        List<User> users = userService.search(pattern);
        if(users.isEmpty()){
            System.out.println("Không tìm thấy kết quả");
            return;
        }
        else{
            while (true) {
                showUsers(users);
                System.out.println("Chọn user để xóa - 0 để thoát");
                System.out.print("Lựa chọn của bạn: ");

                String input = sc.nextLine().trim();

                try {
                    int choice = Integer.parseInt(input);

                    if (choice == 0) {
                        break;
                    }

                    if (choice < 1 || choice > users.size()) {
                        System.out.println("Số không hợp lệ");
                        continue;
                    }
                    User selectedUser = users.get(choice - 1);
                    userService.remove(selectedUser);
                    System.out.println("Đã xóa người dùng");

                } catch (NumberFormatException e) {
                    System.out.println("Hãy nhập một số hợp lệ");
                }

                System.out.println();
                users =userService.search(pattern);
            }

        }
    }
    public void showUsers(List<User> users){
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i+1)+". "+users.get(i).toString());
        }
    }

}
