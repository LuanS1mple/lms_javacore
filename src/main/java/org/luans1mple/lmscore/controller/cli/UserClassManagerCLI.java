package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;
import org.luans1mple.lmscore.controller.service.IClassRoomService;
import org.luans1mple.lmscore.controller.service.IEnrollClassRoomSerivce;
import org.luans1mple.lmscore.controller.service.IUserService;
import org.luans1mple.lmscore.controller.service.impl.ClassRoomService;
import org.luans1mple.lmscore.controller.service.impl.EnrollClassRoomService;
import org.luans1mple.lmscore.controller.service.impl.UserService;
import org.luans1mple.lmscore.controller.ulties.Ulti;

import java.sql.Date;
import java.util.Scanner;

public class UserClassManagerCLI implements Runnable{
    private IUserService userService;
    private IClassRoomService classRoomService;
    private IEnrollClassRoomSerivce enrollClassRoomSerivce;
    public UserClassManagerCLI(){
        this.enrollClassRoomSerivce= new EnrollClassRoomService();
        this.classRoomService = new ClassRoomService();
        this.userService= new UserService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Lớp học của bạn");
            System.out.println("2. Tạo lớp học mới");
            System.out.println("3. Tham gia lớp học");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    UserEnrolledClassRoomCLI userEnrolledClassRoomCLI = new UserEnrolledClassRoomCLI();
                    userEnrolledClassRoomCLI.run();
                    break;
                case 2:
                    creatingClassRoom();
                    break;
                case 3:
                    joiningClassRoom();
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        } while (choice != 0);
    }
    public void creatingClassRoom(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Thông tin tin lớp học: ");
        System.out.println("Tên lớp: ");
        String className = sc.nextLine();
        Date now = new Date(System.currentTimeMillis());
        User user = userService.getById(SessionCLI.getInstance().getUserId());
        String inviteCode = Ulti.generateClassCode();
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(classRoomService.size()+1);
        classRoom.setCreateAt(now);
        classRoom.setCreateBy(user);
        classRoom.setInviteCode(inviteCode);
        classRoom.setclassName(className);
        classRoomService.add(classRoom);
        System.out.println("Tạo thành công lớp học");
    }
    public void joiningClassRoom(){
        System.out.println("Mã lớp học: ");
        Scanner sc = new Scanner(System.in);
        String codeClassRoom = sc.nextLine();
        if(enrollClassRoomSerivce.join(codeClassRoom,SessionCLI.getInstance().getUserId())){
            System.out.println("Bạn đã tham gia lớp học thành công");
        }
        else{
            System.out.println("Mã lớp học không hợp lệ hoặc bạn đã tham gia rồi");
        }
    }
}
