package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.model.dbo.Assignment;
import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.service.IAssignmentService;
import org.luans1mple.lmscore.controller.service.IEnrollClassRoomSerivce;
import org.luans1mple.lmscore.controller.service.impl.AssignmentService;
import org.luans1mple.lmscore.controller.service.impl.EnrollClassRoomService;

import java.util.List;
import java.util.Scanner;

public class StudentClassManagerCLI implements Runnable{
    private ClassRoom currentClass;
    private IEnrollClassRoomSerivce enrollClassRoomSerivce;
    private IAssignmentService assignmentService;
    public StudentClassManagerCLI(ClassRoom classRoom){
        this.currentClass=classRoom;
        this.enrollClassRoomSerivce = new EnrollClassRoomService();
        assignmentService = new AssignmentService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Lớp: " + currentClass.getclassName());
            System.out.println("1. Xem bài tập");
            System.out.println("2. Xem thành viên");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        homeworkManage();
                        break;
                    case 2:
                        memberShowing();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Vui lòng chọn 0, 1 hoặc 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: bạn phải nhập một số.");
            }
            System.out.println();
        }

    }
    public void homeworkManage(){
        System.out.println("Thông tin các bài tập");
        System.out.println("Các bài tập: ");
        List<Assignment> assignmentOngoing = assignmentService.getGoingAssignment(currentClass.getId());
        List<Assignment> assignmentExpired = assignmentService.getExpireAssignment(currentClass.getId());

        if (assignmentOngoing.isEmpty() && assignmentExpired.isEmpty()) {
            System.out.println("Không có bài tập");
            return;
        }
        System.out.println("Các bài tập đang có: ");
        for (int i = 0; i < assignmentOngoing.size(); i++) {
            System.out.println((i + 1) + ". " + assignmentOngoing.get(i).getTittle() + " - Hết hạn vào: " + assignmentOngoing.get(i).getEndAt());
        }
        System.out.println("Các bài tập hết hạn: ");
        for (int i = 0; i < assignmentExpired.size(); i++) {
            System.out.println((i + 1) + ". " + assignmentExpired.get(i).getTittle() + " - Hết hạn vào: " + assignmentExpired.get(i).getEndAt());
        }
        System.out.println("0. Thoát");
        System.out.println("F. Tìm kiếm");

        int select =-1;
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Chọn một số (0 để thoát): ");
            String raw =sc.nextLine();
            try {
                select = Integer.parseInt(raw);
                if (select >= 0 && select <= assignmentOngoing.size() + assignmentExpired.size()) {
                    break;
                } else {
                    System.out.println("Vui lòng chọn từ 0 đến " + (assignmentOngoing.size() + assignmentExpired.size() + 1));
                }
            } catch (NumberFormatException e) {
                if(raw.equalsIgnoreCase("f")){
                    searchingHomeWork();
                    break;
                }
                System.out.println("Vui lòng nhập một số hợp lệ.");
            }
        }

        if (select == 0) {
            System.out.println("Đã quay lại.");
        } else if (select >= 1 && select <= assignmentOngoing.size()) {
            Assignment selectedAssignment = assignmentOngoing.get(select - 1);
            DetailAssignmentCLI detailAssignmentCLI = new DetailAssignmentCLI(selectedAssignment);
            detailAssignmentCLI.run();
        } else if(select > assignmentOngoing.size() && select <= assignmentOngoing.size() + assignmentExpired.size()){
            Assignment selectedAssignment = assignmentExpired.get(select - assignmentOngoing.size()-1);
            DetailAssignmentCLI detailAssignmentCLI = new DetailAssignmentCLI(selectedAssignment);
            detailAssignmentCLI.run();
        }
    }
    public void searchingHomeWork(){
        System.out.println("Nhập từ khóa tìm kiếm: ");
        Scanner sc = new Scanner(System.in);
        String pattern = sc.nextLine().trim();
        List<Assignment> rs  = assignmentService.search(pattern,currentClass.getId());
        if(rs.isEmpty()){
            System.out.println("Không tìm thấy kết quả");
            return;
        }
        System.out.println("Kết quả tìm kiếm: ");
        for (int i = 0; i < rs.size(); i++) {
            System.out.println((i+1)+". "+rs.get(i).toString());
        }
        System.out.println("0. Thoát");

        int select =-1;
        while (true) {
            System.out.print("Lựa chọn: ");
            String raw =sc.nextLine();
            try {
                select = Integer.parseInt(raw);
                if (select >= 0 && select <= rs.size()) {
                    break;
                } else {
                    System.out.println("Vui lòng chọn từ 0 đến " + (rs.size()));
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ.");
            }
        }
        if (select == 0) {
            System.out.println("Đã quay lại.");
        } else if (select >= 1 && select <= rs.size()) {
            Assignment selectedAssignment = rs.get(select - 1);
            DetailAssignmentCLI detailAssignmentCLI = new DetailAssignmentCLI(selectedAssignment);
            detailAssignmentCLI.run();
        }
    }
    public void memberShowing(){
        List<User> teachers = enrollClassRoomSerivce.getTeacherInClass(currentClass.getId());
        List<User> students = enrollClassRoomSerivce.getStudentInClass(currentClass.getId());

        System.out.println("Giáo viên: ");
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println("    " + (i+1) + ". " + teachers.get(i).getFullName());
        }

        System.out.println("Học Sinh: ");
        for (int i = 0; i < students.size(); i++) {
            System.out.println("    " + (i+1) + ". " + students.get(i).getFullName());
        }
    }
}
