package org.luans1mple.lmscore.controller.repositories.mem;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IClassRoomRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MEMClassRoomRepository implements IClassRoomRepository {
    private static final MEMClassRoomRepository instance = new MEMClassRoomRepository();
    private  List<ClassRoom> classes = new ArrayList<>();
    private MEMClassRoomRepository(){
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
    }
    public static MEMClassRoomRepository getInstance(){
        return instance;
    }
    @Override
    public List<ClassRoom> getAll() {
        return classes;
    }

    @Override
    public void add(ClassRoom c) {
        classes.add(c);
    }

    @Override
    public List<ClassRoom> getByUserId(int userId) {
        return List.of();
    }

    @Override
    public ClassRoom getByCode(String inviteCode) {
        try{
            return classes.stream()
                    .filter(classRoom -> classRoom.getInviteCode().equals(inviteCode))
                    .findFirst().get();
        }
        catch (NoSuchElementException e){
            return null;
        }
    }
}
