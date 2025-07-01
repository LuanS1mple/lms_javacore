package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.User;

import java.util.List;

public interface IUserRepository {
    public List<User> getAll();
    public User findByEmail(String email);
    public void add(User user);
    public User getById(int id);
    public void remove(User user);
}
