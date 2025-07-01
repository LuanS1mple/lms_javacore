package org.luans1mple.lmscore.controller.model.dbo;

import java.sql.Date;

public class User {
        private int id;
        private String email;
        private String password;
        private String fullName;
        private String phone;
        private String address;
        private String avatarUrl;
        private int role;
        private int status;
        private Date createdAt;

        public User() {
        }

        public User(int id, String email, String password, String fullName, String phone, String adddress, String avatarUrl, int role, int status, Date createdAt) {
                this.id = id;
                this.email = email;
                this.password = password;
                this.fullName = fullName;
                this.phone = phone;
                this.address = adddress;
                this.avatarUrl = avatarUrl;
                this.role = role;
                this.status = status;
                this.createdAt = createdAt;
        }

        public String getAvatarUrl() {
                return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
        }

        public int getRole() {
                return role;
        }

        public void setRole(int role) {
                this.role = role;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String adddress) {
                this.address = adddress;
        }




        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public int getStatus() {
                return status;
        }

        public void setStatus(int status) {
                this.status = status;
        }

        public Date getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
                this.createdAt = createdAt;
        }

        @Override
        public String toString() {
                return "Full Name: "+fullName+" - CreatedAt: "+createdAt;
        }
}
