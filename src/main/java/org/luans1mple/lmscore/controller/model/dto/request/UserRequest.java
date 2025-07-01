package org.luans1mple.lmscore.controller.model.dto.request;

public class UserRequest {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String avatarUrl;

    public UserRequest(String fullName, String email, String password, String phone, String address, String avatarUrl) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
