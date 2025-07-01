package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.model.dbo.Assignment;
import org.luans1mple.lmscore.controller.model.dbo.AssignmentResult;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.service.impl.AssignmentResultSerivice;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SubmissionCLI implements Runnable{
    private AssignmentResult assignmentResult;
    private AssignmentResultSerivice assignmentResultSerivice;
    public SubmissionCLI(AssignmentResult result){
        this.assignmentResult= result;
        this.assignmentResultSerivice= new AssignmentResultSerivice();
    }
    @Override
    public void run() {
        User user = assignmentResult.getUser();
        Assignment assignment = assignmentResult.getAssignment();
        System.out.println("Bạn đang chấm bài của " + user.getFullName() + " - Bài Tập: " + assignment.getTittle());

        String status = (assignmentResult.getStatus() == AssignmentResultSerivice.getLateNumber()) ? "Nộp muộn" : "Nộp đúng hạn";
        System.out.println("Tình trạng nộp bài: " + status);

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.println("1. Tải bài làm về máy");
            System.out.println("2. Chấm và cho nhận xét");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = sc.nextInt();
                sc.nextLine();

                if (choice == 0) {
                    return;
                } else if (choice == 1) {
                    assignmentResultSerivice.downLoadSubmission(assignmentResult);
                    break;
                } else if (choice == 2) {
                    int score = -1;
                    while (true) {
                        System.out.print("Điểm: ");
                        try {
                            score = sc.nextInt();
                            if(score > assignment.getMaxScore() || score < 0) throw new InputMismatchException();
                            sc.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Vui lòng nhập một số nguyên hợp lệ cho điểm.");
                            sc.nextLine();
                        }
                    }

                    System.out.print("Nhận xét: ");
                    String comment = sc.nextLine();

                    assignmentResultSerivice.mark(score, comment, assignmentResult);
                    break;
                } else {
                    System.out.println("Vui lòng chọn 0, 1 hoặc 2");
                }

            } catch (InputMismatchException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
                sc.nextLine();
            }
        }
    }

}
