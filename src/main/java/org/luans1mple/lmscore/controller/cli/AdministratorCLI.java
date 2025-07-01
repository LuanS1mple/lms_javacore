package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.cli.appdata.SessionCLI;
import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.service.ICourseService;
import org.luans1mple.lmscore.controller.service.IUserService;
import org.luans1mple.lmscore.controller.service.impl.CourseService;
import org.luans1mple.lmscore.controller.service.impl.UserService;

import java.util.List;
import java.util.Scanner;

public class AdministratorCLI implements Runnable{
    private ICourseService courseService;
    private IUserService userService;
    public AdministratorCLI(){
        courseService = new CourseService();
        userService = new UserService();
    }
    @Override
    public void run() {
        boolean isAdmin = logginAdmin();
        if(!isAdmin){
            System.out.println("Sai tài khoản");
            return;
        }
        while(true) {
            System.out.println("=== MENU ===");
            System.out.println("1. Hiển thị danh sách khóa học");
            System.out.println("2. Thêm mới khóa học");
            System.out.println("3. Quản lí người dùng");
            System.out.println("0. Thoát/Logout");
            System.out.print("Nhập lựa chọn của bạn: ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    manageCourses();
                    break;
                case 2:
                    Course course = courseService.getInputCourse();
                    courseService.addCourse(course);
                    System.out.println("Thành công thêm khóa học");
                    break;
                case 3:
                    AdministratorManageUserCLI administratorManageUserCLI = new AdministratorManageUserCLI();
                    administratorManageUserCLI.run();
                    break;
                case 0:
                    SessionCLI.getInstance().setUserId(0);
                    System.out.println("Thoát chương trình");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }
    public void manageCourses(){
        while (true) {
            List<Course> courses = courseService.getAll();
            int index =1;
            System.out.println("Danh sách khóa học đang hoạt động: ");
            for (int i = 0; i < courses.size(); i++) {
                System.out.println(index + ". " + courses.get(i).toString());
                index++;
            }
            System.out.println("Danh sách khóa học đã dừng hoạt động(chọn để hoạt động lại): ");
            List<Course> inactiveCourses = courseService.inactiveCourses();
            for (int i = 0; i < inactiveCourses.size(); i++) {
                System.out.println((index + ". " + inactiveCourses.get(i).toString()));
            }
            System.out.println("0. Thoát");

            Scanner sc = new Scanner(System.in);
            int choice = -1;

            while (true) {
                System.out.print("Lựa chọn của bạn: ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice >= 0 && choice <= courses.size()+inactiveCourses.size()) {
                        break;
                    } else {
                        System.out.println("Vui lòng chọn số chính xác");
                    }
                } else {
                    System.out.println("Vui lòng nhập một số nguyên.");
                    sc.next();
                }
            }

            if (choice >= 1 && choice <= courses.size()) {
                Course course = courses.get(choice - 1);
                AdministratorManageCourseCLI administratorManageCourseCLI = new AdministratorManageCourseCLI(course);
                administratorManageCourseCLI.run();
            }
            else if(choice > courses.size() && choice <= inactiveCourses.size()+ courses.size()){
                Course course = inactiveCourses.get(choice-courses.size()-1);
                course.setStatus("active");
                courseService.update(course);
                System.out.println("Khôi phục thành công");
            }else if (choice == 0) {
                break;
            }
        }

    }
    public boolean logginAdmin(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Email: ");
        String email = sc.nextLine().trim();
        System.out.println("Password: ");
        String pass = sc.nextLine().trim();
        User user = userService.adminLoggin(email,pass);
        if(user!=null){
            SessionCLI.getInstance().setUserId(user.getId());
        }
        return user !=null;
    }
}
