package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.service.IEnrollClassRoomSerivce;
import org.luans1mple.lmscore.controller.service.impl.EnrollClassRoomService;

import java.util.Scanner;

public class DetailMemberClassCLI implements Runnable{
    private User member;
    private ClassRoom classRoom;
    private IEnrollClassRoomSerivce enrollClassRoomSerivce;
    public DetailMemberClassCLI(User mem, ClassRoom classRoom){
        this.member= mem;
        this.classRoom=classRoom;
        enrollClassRoomSerivce = new EnrollClassRoomService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        int role = enrollClassRoomSerivce.getRoleByUserId(member.getId(), classRoom.getId());
        String roleText = (role == EnrollClassRoomService.getSTUDENT_ROLEID()) ? "Học Sinh" : "Giáo viên";

        System.out.println("Thành viên: " + member.getFullName() + " - vai trò: " + roleText);

        while (true) {
            try {
                System.out.println("1. Đổi vai trò");
                System.out.println("2. Kick thành viên");
                System.out.println("0. Thoát");
                System.out.print("Nhập lựa chọn: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        enrollClassRoomSerivce.switchRole(member.getId(), classRoom.getId());
                        System.out.println("Đã đổi vai trò.");
                        return;
                    case 2:
                        enrollClassRoomSerivce.removeMember(member.getId(), classRoom.getId());
                        System.out.println("Đã xoá thành viên.");
                        return;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ.");
            }
        }

    }
}
