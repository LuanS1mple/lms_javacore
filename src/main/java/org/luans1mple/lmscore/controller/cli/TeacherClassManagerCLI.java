package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.Assignment;
import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMUserRepository;
import org.luans1mple.lmscore.controller.service.IAssignmentService;
import org.luans1mple.lmscore.controller.service.IEnrollClassRoomSerivce;
import org.luans1mple.lmscore.controller.service.IEnrollCourseService;
import org.luans1mple.lmscore.controller.service.impl.AssignmentService;
import org.luans1mple.lmscore.controller.service.impl.EnrollClassRoomService;
import org.luans1mple.lmscore.controller.service.impl.EnrollCourseService;
import org.luans1mple.lmscore.controller.ulties.Ulti;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeacherClassManagerCLI implements Runnable{
    private ClassRoom currentClass;
    private IEnrollClassRoomSerivce enrollClassRoomSerivce;
    private IAssignmentService assignmentService;
    private IUserRepository userRepository;
    public TeacherClassManagerCLI(ClassRoom classRoom){
        this.currentClass=classRoom;
        userRepository= MEMUserRepository.getInstance();
        enrollClassRoomSerivce = new EnrollClassRoomService();
        assignmentService = new AssignmentService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Thông tin lớp: "+ currentClass.getclassName());
        System.out.println("Mã lớp: "+ currentClass.getInviteCode());
        System.out.println("1. Quản lí bài tập");
        System.out.println("2. Quản lí học sinh");
        System.out.println("0. Thoát");
        int choice  = sc.nextInt();
        switch (choice){
            case 1:
                manageAssignment();
                break;
            case 2:
                manageStudent();
                break;
            case 0: return;
        }
    }
    public void manageAssignment(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Thông tin các bài tập");
        System.out.println("Các bài tập: ");
        List<Assignment> assignments = assignmentService.getAssignment(currentClass.getId());
        System.out.println((assignments.size() + 1) + ". Thêm bài tập");
        if (assignments.isEmpty()) {
            System.out.println("Không có bài tập");
        }

        for (int i = 0; i < assignments.size(); i++) {
            System.out.println((i + 1) + ". " + assignments.get(i).getTittle() + " - Hết hạn vào: " + assignments.get(i).getEndAt());
        }
        System.out.println("0. Thoát");

        int select;
        while (true) {
            System.out.print("Chọn một số (0 để thoát): ");
            try {
                select = Integer.parseInt(sc.nextLine());
                if (select >= 0 && select <= assignments.size() + 1) {
                    break;
                } else {
                    System.out.println("Vui lòng chọn từ 0 đến " + (assignments.size() + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ.");
            }
        }

        if (select == 0) {
            System.out.println("Đã quay lại.");
        }
        else if (select == assignments.size() + 1) {
            creatingAssignment();
        }
        else if (select >= 1 && select <= assignments.size()) {
            Assignment selectedAssignment = assignments.get(select - 1);
            DetailAssignmentCLI detailAssignmentCLI = new DetailAssignmentCLI(selectedAssignment);
            detailAssignmentCLI.run();
        }
    }
    public void creatingAssignment(){
        Scanner sc= new Scanner(System.in);
        Assignment assignment = new Assignment();
        assignment.setId(assignmentService.size()+1);
        assignment.setClassRoom(currentClass);
        System.out.println("Tiêu đề: ");
        assignment.setTittle(sc.nextLine());
        System.out.println("Mô tả chi tiết: ");
        assignment.setDescription(sc.nextLine());
        System.out.println("Chọn thời gian kết thúc: ");
        sc.nextLine();
        assignment.setEndAt(Ulti.getDatetimeByUI());
        assignment.setStartAt(LocalDateTime.now());
        int maxScore;
        while (true) {
            System.out.print("Điểm tối đa: ");
            if (sc.hasNextInt()) {
                maxScore = sc.nextInt();
                if (maxScore > 0) {
                    break;
                } else {
                    System.out.println(" Điểm tối đa phải lớn hơn 0.");
                }
            } else {
                System.out.println(" Vui lòng nhập một số nguyên.");
                sc.next();
            }
        }
        assignment.setMaxScore(maxScore);
        assignment.setCreateBy(userRepository.getById(SessionCLI.getInstance().getUserId()));
        assignment.setAllowLate(true);
        System.out.println("Tài liệu: ");
        assignment.setAssignmentUrl(assignmentService.uploadMeterial());
        assignmentService.add(assignment);
    }
    public void manageStudent(){
        Scanner sc = new Scanner(System.in);
        int classId = currentClass.getId();
        List<User> teachers = enrollClassRoomSerivce.getTeacherInClass(classId);
        List<User> students = enrollClassRoomSerivce.getStudentInClass(classId);
        if(students.isEmpty()&& teachers.isEmpty()){
            System.out.println("Not found user");
            return;
        }
        int index =1;
        System.out.println("A. Giáo viên: ");
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println("    "+index+". "+teachers.get(i).getFullName());
            index++;
        }
        System.out.println("A. Học sinh: ");
        for (int i = 0; i < students.size(); i++) {
            System.out.println("    "+index+". "+students.get(i).getFullName());
            index++;
        }
        List<User> members = new ArrayList<>();
        members.addAll(teachers);
        members.addAll(students);
        int selectIndex = sc.nextInt();
        if(selectIndex>0 && selectIndex <=members.size()){
            User selectedUser = members.get(selectIndex-1);
            DetailMemberClassCLI detailMemberClassCLI = new DetailMemberClassCLI(selectedUser,currentClass);
            detailMemberClassCLI.run();
        }
    }
}
