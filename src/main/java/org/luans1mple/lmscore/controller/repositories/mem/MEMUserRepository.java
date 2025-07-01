package org.luans1mple.lmscore.controller.repositories.mem;

import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MEMUserRepository implements IUserRepository {
    private static MEMUserRepository instance = new MEMUserRepository();
    private List<User> users = new ArrayList<>();
    private MEMUserRepository(){
        Date now = new Date(System.currentTimeMillis());
        users.add(new User(1, "luan", "123", "Alice Nguyen",
                "0909123456", "123 Le Loi, HCM", "avatar1.jpg", 1, 1, now));
        users.add(new User(2, "1", "1", "Bob Tran",
                "0912123456", "456 Nguyen Trai, HN", "avatar2.jpg", 2, 1, now));
        users.add(new User(3, "charlie@example.com", "pass789", "Charlie Le",
                "0933123456", "789 Vo Thi Sau, HCM", "avatar3.jpg", 1, 0, now));
        users.add(new User(4, "diana@example.com", "passabc", "Diana Pham",
                "0977123456", "321 Dien Bien Phu, HN", "avatar4.jpg", 2, 1, now));
        users.add(new User(5, "edward@example.com", "passxyz", "Edward Do",
                "0988123456", "654 Tran Hung Dao, HCM", "avatar5.jpg", 1, 1, now));
    }
    public static MEMUserRepository getInstance(){
        return instance;
    }
    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User findByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getEmail().equals(email) && users.get(i).getRole()==1){
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        user.setId(users.size());
        users.add(user);
    }

    @Override
    public User getById(int id) {
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId()==id) return users.get(i);
        }
        return null;
    }

    @Override
    public void remove(User user) {
        user.setStatus(0);
    }
}
