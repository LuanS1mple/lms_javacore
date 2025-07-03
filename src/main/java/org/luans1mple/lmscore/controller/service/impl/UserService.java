package org.luans1mple.lmscore.controller.service.impl;

import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLUserRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMUserRepository;
import org.luans1mple.lmscore.controller.service.IUserService;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class UserService implements IUserService {
    private IUserRepository userRepository;
    public UserService(){
//        this.userRepository = MEMUserRepository.getInstance();
        this.userRepository = SQLUserRepository.getInstance();
    }
    @Override
    public boolean isCorrectAccount(String email, String password) {
        User user = getByEmail(email);
        if(user==null) return false;
        return password.equals(user.getPassword()) && user.getStatus()==1;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void add(User user) {
        userRepository.add(user);
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll().stream()
                .filter(user -> user.getStatus()==1).toList();
    }

    @Override
    public List<User> orderByName() {
        return userRepository.getAll().stream()
                .sorted(Comparator.comparing(User::getFullName))
                .filter(user -> user.getStatus()==1).toList();
    }

    @Override
    public List<User> orderByCreateDate() {
        return userRepository.getAll().stream()
                .sorted(Comparator.comparing(User::getCreatedAt))
                .filter(user -> user.getStatus()==1).toList();
    }

    @Override
    public void remove(User user) {
        userRepository.remove(user);
    }

    @Override
    public List<User> search(String pattern) {
        return getAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(pattern.toLowerCase().trim())||
                        user.getFullName().toLowerCase().contains(pattern.toLowerCase().trim())).toList();
    }

    @Override
    public User adminLoggin(String email, String password) {
        try{
            return userRepository.getAll().stream()
                    .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password) && user.getRole()==2).findFirst().get();
        }
        catch (NoSuchElementException e){
            return  null;
        }
    }

    @Override
    public boolean isExistEmail(String email) {
        try {
            userRepository.getAll().stream()
                    .filter(user -> user.getEmail().toLowerCase().equals(email.toLowerCase())).findFirst().get();
            return true;
        }
        catch (NoSuchElementException e){
            return false;
        }
    }

}
