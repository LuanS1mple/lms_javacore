package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.Assignment;
import org.luans1mple.lmscore.controller.model.dbo.AssignmentResult;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.service.IAssignmentResultService;
import org.luans1mple.lmscore.controller.service.IAssignmentService;
import org.luans1mple.lmscore.controller.service.IEnrollClassRoomSerivce;
import org.luans1mple.lmscore.controller.service.impl.AssignmentResultSerivice;
import org.luans1mple.lmscore.controller.service.impl.AssignmentService;
import org.luans1mple.lmscore.controller.service.impl.EnrollClassRoomService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DetailAssignmentCLI implements Runnable {
    private Assignment assignment;
    private IEnrollClassRoomSerivce enrollClassRoomSerivce;
    private IAssignmentService assignmentService;
    private IAssignmentResultService assignmentResultService;
    public DetailAssignmentCLI(Assignment assignment){
        this.assignment= assignment;
        this.assignmentResultService = new AssignmentResultSerivice();
        this.assignmentService = new AssignmentService();
        enrollClassRoomSerivce = new EnrollClassRoomService();
    }
    @Override
    public void run() {
        int userId = SessionCLI.getInstance().getUserId();
        int roleId = enrollClassRoomSerivce.getRoleByUserId(userId,assignment.getClassRoom().getId());
        System.out.println("Thông tin bài tập: ");
        System.out.println("Tiêu đề:"+ assignment.toString());
        if(roleId == EnrollClassRoomService.getSTUDENT_ROLEID()){
            this.studentActivities();
        }
        else{
            this.teacherActivities();
        }
    }
    public void teacherActivities(){
        int index =1;
        Scanner sc = new Scanner(System.in);
        boolean isContainMaterial = !(assignment.getAssignmentUrl()==null ||assignment.getAssignmentUrl().isEmpty());
        if(!isContainMaterial){
            System.out.println((index++)+". Không có tài liệu");
        }
        else{
            System.out.println((index++)+". Tải tài liệu về máy");
        }
        System.out.println((index++)+". Kết quả đã chấm:");
        System.out.println((index++)+". Bài chưa chấm");
        System.out.println("0. Thoát");
        System.out.println("Lựa chọn của bạn: ");
        int choice = -1;
        try {
            choice = sc.nextInt();
            sc.nextLine();

            if (choice == 0) {
                return;
            }
            if (choice == 1 && isContainMaterial) {
                assignmentService.downLoad(assignment);
            } else if (choice == 2) {
               browsingMarkedAssignment();
            } else if (choice == 3) {
                browsingUnMarkedAssignment();
            } else {
                System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
            sc.nextLine();
        }
    }
    public void studentActivities() {
        int userId = SessionCLI.getInstance().getUserId();
        AssignmentResult result = assignmentResultService.getResult(userId,assignment.getId());
        if(result!=null && result.getMark()!=AssignmentResultSerivice.getUnMarkedNumber()){
            System.out.println("Điểm đã chấm: "+result.getMark());
        }
        int index = 1;
        boolean isContainMaterial = assignment.getAssignmentUrl() != null && !assignment.getAssignmentUrl().isEmpty();

        if (!isContainMaterial) {
            System.out.println((index++) + ". Không có tài liệu");
        } else {
            System.out.println((index++) + ". Tải tài liệu về máy");
        }
        System.out.println((index++) + ". Nộp bài");
        System.out.println("0. Thoát");

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.print("Nhập lựa chọn: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                if (choice == 0) {
                    System.out.println("Đã thoát.");
                    break;
                } else if (choice == 1) {
                    if (isContainMaterial) {
                        assignmentService.downLoad(assignment);
                    }
                    break;
                } else if (choice == 2) {
                    assignmentResultService.submit(assignment);
                    break;
                } else {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                }
            } else {
                System.out.println("Vui lòng nhập một số.");
                sc.next(); // bỏ dữ liệu không hợp lệ
            }
        }
    }


    public  void browsingMarkedAssignment(){
        Scanner sc =new Scanner(System.in);
        List<AssignmentResult> assignmentResults = assignmentResultService.getMarkedAssignment(assignment.getId());
        System.out.println("Danh sách đã chấm: ");
        for (int i = 0; i < assignmentResults.size(); i++) {
            AssignmentResult assignmentResult = assignmentResults.get(i);
            User member =assignmentResults.get(i).getUser();
            System.out.println((i+1)+". "+member.getFullName()+" - "+ assignmentResult.getMark());
        }
        System.out.println("0. Thoát");
        while (true) {
            try {
                int input = sc.nextInt();
                sc.nextLine();
                if (input == 0) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Vui lòng chỉ nhập");
                sc.nextLine();
            }
        }
    }
    public void browsingUnMarkedAssignment(){
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        List<AssignmentResult> results;

        while (true) {
            System.out.println("Bạn muốn:");
            System.out.println("1. Tìm kiếm");
            System.out.println("2. Duyệt toàn bộ");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    System.out.print("Nhập thông tin tìm kiếm: ");
                    String pattern = sc.nextLine();
                    results = assignmentResultService.search(pattern, assignment.getId());
                    break;
                } else if (choice == 2) {
                    results = assignmentResultService.getUnMarkedAssignment(assignment.getId());
                    break;
                } else {
                    System.out.println("Vui lòng chỉ chọn 1 hoặc 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Vui lòng nhập số hợp lệ (1 hoặc 2).");
                sc.nextLine();
            }
        }
        if(results.isEmpty()){
            System.out.println("Chưa có bài nào nộp");
            return;
        }
        for (int i = 0; i < results.size(); i++) {
            AssignmentResult result = results.get(i);
            System.out.println((i+1)+". "+result.getUser().getFullName()+" - SUBMIT AT: "+result.getDoneAt());
        }
        int select = -1;
        while (true) {
            System.out.print("Chọn bài nộp để xem chi tiết: ");
            try {
                select = sc.nextInt();
                sc.nextLine();

                if (select >= 1 && select <= results.size()) {
                    break;
                } else {
                    System.out.println("Vui lòng chọn số từ 1 đến " + results.size());
                }
            } catch (InputMismatchException e) {
                System.out.println("Vui lòng nhập một số hợp lệ.");
                sc.nextLine();
            }
        }

        AssignmentResult selectedSubmission = results.get(select - 1);
        SubmissionCLI submissionCLI = new SubmissionCLI(selectedSubmission);
        submissionCLI.run();
    }
}
