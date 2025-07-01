package org.luans1mple.lmscore.controller.repositories.mem;

import org.luans1mple.lmscore.controller.model.dbo.Assignment;
import org.luans1mple.lmscore.controller.model.dbo.AssignmentResult;
import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IAssignmentResultRepository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MEMAssignmentResultRepository implements IAssignmentResultRepository {
    private List<AssignmentResult> assignmentResults  =new ArrayList<>();
    private static final MEMAssignmentResultRepository instance = new MEMAssignmentResultRepository();
    private MEMAssignmentResultRepository(){
        List<User> users= new ArrayList<>();
        Date now = new Date(System.currentTimeMillis());
        users.add(new User(1, "luan", "123", "Alice Nguyen",
                "0909123456", "123 Le Loi, HCM", "avatar1.jpg", 1, 1, now));
        users.add(new User(2, "bob@example.com", "pass456", "Bob Tran",
                "0912123456", "456 Nguyen Trai, HN", "avatar2.jpg", 2, 1, now));
        users.add(new User(3, "charlie@example.com", "pass789", "Charlie Le",
                "0933123456", "789 Vo Thi Sau, HCM", "avatar3.jpg", 1, 0, now));
        users.add(new User(4, "diana@example.com", "passabc", "Diana Pham",
                "0977123456", "321 Dien Bien Phu, HN", "avatar4.jpg", 2, 1, now));
        users.add(new User(5, "edward@example.com", "passxyz", "Edward Do",
                "0988123456", "654 Tran Hung Dao, HCM", "avatar5.jpg", 1, 1, now));

        List<ClassRoom> classes = new ArrayList<>();
        classes.add(new ClassRoom(1, "Lớp Java Cơ Bản", now, users.get(0), "JAVA123"));
        classes.add(new ClassRoom(2, "Lớp Thiết Kế Web", now, users.get(0), "WEB789"));
        classes.add(new ClassRoom(3, "Lớp SQL và Cơ Sở Dữ Liệu", now, users.get(0), "SQL456"));
        classes.add(new ClassRoom(4, "Lớp Git & Github", now, users.get(0), "GIT001"));
        classes.add(new ClassRoom(5, "Lớp Java nâng cao", now, users.get(0), "JAVA999"));
        classes.add(new ClassRoom(6, "Lớp Python cơ bản", now, users.get(1), "PY101"));
        classes.add(new ClassRoom(7, "Lớp C++ cho người mới", now, users.get(2), "CPP123"));
        classes.add(new ClassRoom(8, "Lớp Phân tích dữ liệu", now, users.get(3), "DATA456"));
        classes.add(new ClassRoom(9, "Lớp Machine Learning", now, users.get(4), "ML789"));
        classes.add(new ClassRoom(10, "Lớp An toàn thông tin", now, users.get(1), "SEC2025"));

        LocalDateTime time = LocalDateTime.now();
        List<Assignment> assignments = new ArrayList<>();
        assignments.add(new Assignment(1, classes.get(0), "Bài tập OOP cơ bản",
                "Tạo lớp, kế thừa và ghi đè phương thức.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(5), time.plusDays(5),
                10, users.get(0), true));

        assignments.add(new Assignment(2, classes.get(1), "Bài tập thiết kế layout",
                "Tạo giao diện web bằng HTML và CSS.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(3), time.plusDays(4),
                15, users.get(0), false));

        assignments.add(new Assignment(3, classes.get(2), "Truy vấn SQL cơ bản",
                "Viết các câu lệnh SELECT, WHERE, JOIN.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(7), time.plusDays(3),
                20, users.get(0), true));

        assignments.add(new Assignment(4, classes.get(3), "Lệnh git cơ bản",
                "Clone, commit, push dự án lên GitHub.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(2), time.plusDays(7),
                10, users.get(0), true));

        assignments.add(new Assignment(5, classes.get(4), "OOP nâng cao",
                "Áp dụng interface và abstract class.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(1), time.plusDays(10),
                25, users.get(0), false));

        assignments.add(new Assignment(6, classes.get(5), "Python: Vòng lặp và hàm",
                "Viết hàm và sử dụng for/while.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(4), time.plusDays(6),
                10, users.get(1), true));

        assignments.add(new Assignment(7, classes.get(6), "C++: Quản lý bộ nhớ",
                "Thực hành con trỏ, cấp phát động.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(3), time.plusDays(7),
                20, users.get(2), false));

        assignments.add(new Assignment(8, classes.get(7), "Phân tích dữ liệu: Excel & BI",
                "Thống kê, biểu đồ, pivot table.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(6), time.plusDays(2),
                15, users.get(3), true));

        assignments.add(new Assignment(9, classes.get(8), "ML: Linear Regression",
                "Huấn luyện mô hình đơn giản bằng sklearn.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(4), time.plusDays(5),
                30, users.get(4), false));

        assignments.add(new Assignment(10, classes.get(9), "An toàn thông tin: Mã hóa",
                "Áp dụng thuật toán Caesar và Vigenère.",
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt",
                time.minusDays(2), time.plusDays(8),
                20, users.get(1), true));
        assignmentResults.add(new AssignmentResult(users.get(1), assignments.get(0),
                time.plusDays(2),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", -1,
                "Làm tốt, chú ý cú pháp Java.", 0));

        assignmentResults.add(new AssignmentResult(users.get(2), assignments.get(0),
                time.plusDays(6),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 75,
                "Cần cải thiện phần kế thừa.", 1));

        assignmentResults.add(new AssignmentResult(users.get(3), assignments.get(1),
                time.plusDays(3),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", -1,
                "", 0)); // chưa chấm

        assignmentResults.add(new AssignmentResult(users.get(4), assignments.get(1),
                time.plusDays(5),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 85,
                "Giao diện rõ ràng, cần responsive hơn.", 1));

        assignmentResults.add(new AssignmentResult(users.get(0), assignments.get(2),
                time.plusDays(1),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 70,
                "Viết đúng cú pháp, còn thiếu JOIN nâng cao.", 0));

        assignmentResults.add(new AssignmentResult(users.get(1), assignments.get(2),
                time.plusDays(4),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 50,
                "Sai cú pháp SQL, xem lại WHERE.", 1));

        assignmentResults.add(new AssignmentResult(users.get(2), assignments.get(3),
                time.plusDays(3),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 95,
                "Tốt, đầy đủ các thao tác Git.", 0));

        assignmentResults.add(new AssignmentResult(users.get(3), assignments.get(3),
                time.plusDays(8),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 40,
                "Nộp muộn và thiếu commit chi tiết.", 1));

        assignmentResults.add(new AssignmentResult(users.get(4), assignments.get(4),
                time.plusDays(2),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", 100,
                "Xuất sắc, hiểu rõ interface và abstract.", 0));

        assignmentResults.add(new AssignmentResult(users.get(2), assignments.get(4),
                time.plusDays(11),
                "C:\\Users\\thanh\\Downloads\\SU25-Semester6\\JAVA_CORE\\Project\\LMS_Core\\test.txt", -1,
                "", 1)); // trễ hạn, chưa chấm
    }
    public static MEMAssignmentResultRepository getInstance(){
        return  instance;
    }

    @Override
    public List<AssignmentResult> getByAssignment(int assignmentId) {
        return assignmentResults.stream()
                .filter(assignmentResult -> assignmentResult.getAssignment().getId()==assignmentId).toList();
    }

    @Override
    public void mark(int score,String comment, AssignmentResult assignmentResult) {
        assignmentResult.setMark(score);
        assignmentResult.setComment(comment);
    }

    @Override
    public void add(AssignmentResult assignmentResult) {
        assignmentResults.add(assignmentResult);
    }

    @Override
    public int size() {
        return  assignmentResults.size();
    }

}
