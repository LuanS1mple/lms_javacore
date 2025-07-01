package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.CourseContent;
import org.luans1mple.lmscore.controller.model.dbo.CourseTest;
import org.luans1mple.lmscore.controller.service.ICourseContentService;
import org.luans1mple.lmscore.controller.service.ICourseTestService;
import org.luans1mple.lmscore.controller.service.impl.CourseContentService;
import org.luans1mple.lmscore.controller.service.impl.CourseTestService;

import java.util.List;
import java.util.Scanner;

public class UserSearchCourseMeterialCLI implements  Runnable{
    private Course currentCourse;
    private ICourseContentService courseContentService;
    private ICourseTestService courseTestService;
    public UserSearchCourseMeterialCLI(Course course){
        this.courseContentService= new CourseContentService();
        this.courseTestService= new CourseTestService();
        this.currentCourse = course;
    }
    @Override
    public void run() {
        Scanner sc= new Scanner(System.in);
        System.out.println("Điền từ khóa tìm kiếm học liệu: ");
        String pattern = sc.nextLine().trim();
        int courseId = this.currentCourse.getId();
        List<CourseContent> contents = courseContentService.getByTittle(pattern,courseId);
        List<CourseTest> tests = courseTestService.getByTittle(pattern,courseId);
        if(contents.isEmpty()&& tests.isEmpty()){
            System.out.println("Not found");
            return;
        }
        System.out.println(contents.size()+"    "+tests.size());
        int index;
        while (true) {
            index = 1;
            System.out.println("A. Tài liệu: ");
            for (int i = 0; i < contents.size(); i++) {
                System.out.println("    " + index + ". " + contents.get(i).toString());
                index++;
            }

            System.out.println("B. Các bài test: ");
            for (int i = 0; i < tests.size(); i++) {
                System.out.println("    " + index + ". " + tests.get(i).toString());
                index++;
            }

            System.out.print("Lựa chọn của bạn: ");
            int choice = sc.nextInt();

            if (choice >= 1 && choice <= contents.size()) {
                courseContentService.downLoadCourseContent(contents.get(choice - 1));
                break;
            } else if (choice > contents.size() && choice <= contents.size() + tests.size()) {
                courseTestService.downLoadCourseTest(tests.get(choice - contents.size() - 1));
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }

    }
}
