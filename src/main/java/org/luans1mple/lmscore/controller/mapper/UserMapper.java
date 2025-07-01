package org.luans1mple.lmscore.controller.mapper;

import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.model.dto.request.UserRequest;

import java.sql.Date;

public class UserMapper {
    public static User userRequesttoUser(UserRequest request){
        User user = new User();
        user.setId(0);
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setAvatarUrl(null);
        user.setRole(1);
        user.setStatus(1);
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        return user;
    }
}
