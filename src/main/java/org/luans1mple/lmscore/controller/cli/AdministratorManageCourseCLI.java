package org.luans1mple.lmscore.controller.cli;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.CourseContent;
import org.luans1mple.lmscore.controller.model.dbo.CourseTest;
import org.luans1mple.lmscore.controller.service.ICourseContentService;
import org.luans1mple.lmscore.controller.service.ICourseService;
import org.luans1mple.lmscore.controller.service.ICourseTestService;
import org.luans1mple.lmscore.controller.service.impl.CourseContentService;
import org.luans1mple.lmscore.controller.service.impl.CourseService;
import org.luans1mple.lmscore.controller.service.impl.CourseTestService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AdministratorManageCourseCLI implements Runnable{
    private Course course;
    private ICourseService courseService;
    private ICourseTestService courseTestService;
    private ICourseContentService courseContentService;
    public AdministratorManageCourseCLI(Course c){
        this.course = c;
        this.courseTestService= new CourseTestService();
        this.courseService = new CourseService();
        this.courseContentService= new CourseContentService();
    }
    @Override
    public void run() {
        while (true) {
            System.out.println("Khóa học " + course.getTitle() + " :");
            System.out.println("1. Cập nhật thông tin");
            System.out.println("2. Thêm tài liệu");
            System.out.println("3. Thêm bài kiểm tra");
            System.out.println("4. Xóa khóa học");
            System.out.println("5. Chi tiết khóa học");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn: ");

            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    updateCourseInfo();
                    break;
                case 2:
                    updateCourseContent();
                    break;
                case 3:
                    updateCourseTest();
                    break;
                case 4:
                    removeCourse();
                    return;
                case 5:
                    detailCourse();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }

    }
    public void updateCourseContent(){
        CourseContent content = new CourseContent();
        content.setId(courseContentService.size()+1);
        content.setCourse(course);
        System.out.println("Nhập thông tin tài liệu: ");
        System.out.print("Nhập tiêu đề (title): ");
        Scanner sc= new Scanner(System.in);
        String title = sc.nextLine().trim();
        while (title.isEmpty()) {
            System.out.print("Tiêu đề không được để trống. Nhập lại: ");
            title = sc.nextLine().trim();
        }
        content.setTitle(title);

        System.out.print("Nhập loại nội dung (type - video/document/slide): ");
        String type = sc.nextLine().trim().toLowerCase();
        while (!type.equals("video") && !type.equals("document")&& !type.equals("slide")) {
            System.out.print("Chỉ được nhập 'video' hoặc 'document' hoặc 'slide'. Nhập lại: ");
            type = sc.nextLine().trim().toLowerCase();
        }
        content.setType(type);

        System.out.print("Nhập đường dẫn nội dung (contentUrl): ");
        String url = courseContentService.getContentUrl();
        content.setContentUrl(url);

        System.out.print("Nhập thời lượng (duration - phút, có thể để trống): ");
        String durationInput = sc.nextLine().trim();
        if (!durationInput.isEmpty()) {
            try {
                int duration = Integer.parseInt(durationInput);
                if (duration <= 0) {
                    System.out.println("Thời lượng phải là số nguyên dương. Bỏ qua.");
                } else {
                    content.setDuration(duration);
                }
            } catch (NumberFormatException e) {
                System.out.println("Sai định dạng thời lượng. Bỏ qua.");
            }
        }

        System.out.print("Nhập thứ tự trong khóa: ");
        while (true) {
            try {
                int index = Integer.parseInt(sc.nextLine().trim());
                if (index < 0) {
                    System.out.print("Thứ tự phải >= 0. Nhập lại: ");
                    continue;
                }
                content.setOrderIndex(index);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Phải nhập số nguyên. Nhập lại: ");
            }
        }

        content.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));

        courseContentService.add(content);
    }
    public void updateCourseTest(){
        System.out.println("Điền thông tin test: ");
        Scanner sc = new Scanner(System.in);
        CourseTest test = new CourseTest();
        System.out.print("Nhập tiêu đề bài kiểm tra: ");
        String title = sc.nextLine().trim();
        while (title.isEmpty()) {
            System.out.print("Không được để trống. Nhập lại tiêu đề: ");
            title = sc.nextLine().trim();
        }
        test.setTestTittle(title);

        System.out.print("Nhập mô tả bài kiểm tra: ");
        String desc = sc.nextLine().trim();
        while (desc.isEmpty()) {
            System.out.print("Không được để trống. Nhập lại mô tả: ");
            desc = sc.nextLine().trim();
        }
        test.setTestDescription(desc);

        System.out.print("Nhập loại kiểm tra (type - quiz/assignment/exam): ");
        String type = sc.nextLine().trim().toLowerCase();
        while (!type.equals("quiz") && !type.equals("assignment") && !type.equals("exam")) {
            System.out.print("Chỉ được nhập 'quiz', 'assignment' hoặc 'exam'. Nhập lại: ");
            type = sc.nextLine().trim().toLowerCase();
        }
        test.setType(type);

        System.out.print("Đường dẫn bài kiểm tra (testUrl): ");
        String url = courseTestService.getCourseTestUrl();
        test.setTestUrl(url);

        System.out.print("Nhập điểm tối đa (maxScore): ");
        while (true) {
            try {
                int score = Integer.parseInt(sc.nextLine().trim());
                if (score <= 0) {
                    System.out.print("Điểm tối đa phải > 0. Nhập lại: ");
                    continue;
                }
                test.setMaxScore(score);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Phải nhập số nguyên. Nhập lại điểm tối đa: ");
            }
        }

        System.out.print("Nhập thời lượng (duration - phút): ");
        while (true) {
            try {
                int duration = Integer.parseInt(sc.nextLine().trim());
                if (duration < 0) {
                    System.out.print("Thời lượng phải >= 0. Nhập lại: ");
                    continue;
                }
                test.setDuration(duration);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Phải nhập số nguyên. Nhập lại thời lượng: ");
            }
        }

        test.setStatus(1);
        test.setId(courseTestService.size()+1);
        test.setCourse(course);
        courseTestService.add(test);
        System.out.println("Thêm test thành công");
    }
    public void removeCourse(){
        courseService.remove(course);
        System.out.println("Xóa khóa học thành công");
    }
    public void updateCourseInfo(){
        Scanner sc = new Scanner(System.in);
        Course updatedCourse = new Course();

        // Giữ nguyên các trường không nhập
        updatedCourse.setId(course.getId());
        updatedCourse.setStatus(course.getStatus());
        updatedCourse.setCreatedAt(course.getCreatedAt());

        // Nhập các trường mới
        System.out.print("Nhập tiêu đề: ");
        updatedCourse.setTitle(sc.nextLine());

        System.out.print("Nhập mô tả: ");
        updatedCourse.setDescription(sc.nextLine());

        System.out.print("Nhập danh mục: ");
        updatedCourse.setCategory(sc.nextLine());

        System.out.print("Nhập trình độ: ");
        updatedCourse.setLevel(sc.nextLine());

        System.out.print("Nhập thời lượng (duration - số giờ): ");
        try {
            updatedCourse.setDuration(Integer.parseInt(sc.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println("Thời lượng không hợp lệ, dùng giá trị cũ.");
            updatedCourse.setDuration(course.getDuration());
        }

        System.out.print("Nhập tác giả : ");
        updatedCourse.setAuthor(sc.nextLine());
        updatedCourse.setUpdatedAt(new Date());
        courseService.update(updatedCourse);
        System.out.println("Đã cập nhập khóa học");
    }
    public void detailCourse(){
        List<CourseContent> contents = courseContentService.getByCourseId(course.getId());
        List<CourseTest> tests = courseTestService.getByCourseId(course.getId());
        int index=1;
        System.out.println("Tài liệu khóa học: ");
        for (int i = 0; i < contents.size(); i++) {
            System.out.println("    "+index+". "+contents.get(i).toString());
            index++;
        }
        System.out.println("Bài kiểm tra của khóa học: ");
        for (int i = 0; i < tests.size(); i++) {
            System.out.println("    "+index+". "+tests.get(i).toString());
            index++;
        }
        System.out.println("========");
    }
}
