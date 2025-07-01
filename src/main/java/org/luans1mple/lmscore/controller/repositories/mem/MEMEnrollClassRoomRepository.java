package org.luans1mple.lmscore.controller.repositories.mem;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.EnrollClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IEnrollClassRoomRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MEMEnrollClassRoomRepository implements IEnrollClassRoomRepository {
    private static final MEMEnrollClassRoomRepository instance = new MEMEnrollClassRoomRepository();
    private List<EnrollClassRoom> enrolls = new ArrayList<>();
    private MEMEnrollClassRoomRepository(){
        List<User> users = new ArrayList<>();
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

        enrolls.add(new EnrollClassRoom(users.get(0), classes.get(0), now, 1)); // luan tạo lớp 1
        enrolls.add(new EnrollClassRoom(users.get(1), classes.get(5), now, 1)); // bob tạo lớp 6
        enrolls.add(new EnrollClassRoom(users.get(2), classes.get(6), now, 1)); // charlie tạo lớp 7
        enrolls.add(new EnrollClassRoom(users.get(3), classes.get(7), now, 1)); // diana tạo lớp 8
        enrolls.add(new EnrollClassRoom(users.get(4), classes.get(8), now, 1)); // edward tạo lớp 9

// Người học (role = 0)
        enrolls.add(new EnrollClassRoom(users.get(0), classes.get(0), now, 0)); // bob học lớp 1
        enrolls.add(new EnrollClassRoom(users.get(2), classes.get(1), now, 0)); // charlie học lớp 2
        enrolls.add(new EnrollClassRoom(users.get(3), classes.get(2), now, 0)); // diana học lớp 3
        enrolls.add(new EnrollClassRoom(users.get(4), classes.get(3), now, 0)); // edward học lớp 4
        enrolls.add(new EnrollClassRoom(users.get(0), classes.get(6), now, 0)); // luan học lớp 7
    }
    public static MEMEnrollClassRoomRepository getInstance(){
        return  instance;
    }

    @Override
    public List<ClassRoom> getClassesByUserId(int userId) {
        return enrolls.stream()
                .filter(enrollClassRoom -> enrollClassRoom.getUser().getId()==userId)
                .map(enrollClassRoom -> enrollClassRoom.getClassRoom())
                .toList();
    }

    @Override
    public List<EnrollClassRoom> getEnrollClassesByUserId(int userId) {
        return enrolls.stream()
                .filter(enrollClassRoom -> enrollClassRoom.getUser().getId()==userId).toList();
    }

    @Override
    public List<EnrollClassRoom> getByClassId(int classId) {
        return enrolls.stream()
                .filter(enrollClassRoom -> enrollClassRoom.getClassRoom().getId()==classId).toList();
    }

    @Override
    public void remove(EnrollClassRoom enrollClassRoom) {
        enrolls.remove(enrollClassRoom);
    }

    @Override
    public void add(EnrollClassRoom enrollClassRoom) {
        enrolls.add(enrollClassRoom);
    }
}
