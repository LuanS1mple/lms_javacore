package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.service.IEnrollClassRoomSerivce;
import org.luans1mple.lmscore.controller.service.impl.EnrollClassRoomService;

import java.util.List;
import java.util.Scanner;

public class UserEnrolledClassRoomCLI implements Runnable{
    private IEnrollClassRoomSerivce enrollClassRoomSerivce;
    public UserEnrolledClassRoomCLI(){
        this.enrollClassRoomSerivce = new EnrollClassRoomService();
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        int useId = SessionCLI.getInstance().getUserId();
        List<ClassRoom> classesStudent = enrollClassRoomSerivce.getClassesUserAsStudent(useId);
        List<ClassRoom> classesTeacher = enrollClassRoomSerivce.getClassesUserAsTeacher(useId);
        if(classesTeacher.isEmpty() && classesStudent.isEmpty()){
            System.out.println("Not found class");
            return;
        }
        System.out.println("Các lớp đã tham gia");
        int index =1;
        System.out.println("Vai trò: GIÁO VIÊN:");
        for (int i = 0; i < classesTeacher.size(); i++) {
            System.out.println("     "+index+". "+ classesTeacher.get(i).getclassName());
            index++;
        }
        System.out.println("Vai trò: HỌC VIÊN:");
        for (int i = 0; i < classesStudent.size(); i++) {
            System.out.println("     "+index+". "+ classesStudent.get(i).getclassName());
            index++;
        }
        System.out.println("Lựa chọn của bạn: ");
        int choice = sc.nextInt();
        if(choice > 0 && choice <= classesTeacher.size()){
            ClassRoom classRoom = classesTeacher.get(choice-1);
            TeacherClassManagerCLI teacherClassManagerCLI = new TeacherClassManagerCLI(classRoom);
            teacherClassManagerCLI.run();
        }
        else if(choice > classesTeacher.size() && choice <= classesTeacher.size()+ classesStudent.size()){
            ClassRoom classRoom = classesStudent.get(choice- classesTeacher.size()-1);
            StudentClassManagerCLI studentClassManagerCLI = new StudentClassManagerCLI(classRoom);
            studentClassManagerCLI.run();
        }
        else {
            System.out.println("Không hợp lệ");
        }

    }
}
