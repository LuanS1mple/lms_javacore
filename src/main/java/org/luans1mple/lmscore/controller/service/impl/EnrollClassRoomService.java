package org.luans1mple.lmscore.controller.service.impl;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.EnrollClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;
import org.luans1mple.lmscore.controller.repositories.IClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.IEnrollClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.IUserRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLEnrollClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMEnrollClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMUserRepository;
import org.luans1mple.lmscore.controller.service.IEnrollClassRoomSerivce;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class EnrollClassRoomService implements IEnrollClassRoomSerivce {
    private IEnrollClassRoomRepository enrollClassRoomRepository;
    private IClassRoomRepository classRoomRepository;
    private IUserRepository userRepository;
    private static final int TEACHER_ROLEID = 1;
    private static final int STUDENT_ROLEID= 0;
    public EnrollClassRoomService(){
//        this.classRoomRepository = MEMClassRoomRepository.getInstance();
        this.classRoomRepository = SQLClassRoomRepository.getInstance();
//        this.enrollClassRoomRepository= MEMEnrollClassRoomRepository.getInstance();
        this.enrollClassRoomRepository= SQLEnrollClassRoomRepository.getInstance();
//        this.userRepository = MEMUserRepository.getInstance();
        this.userRepository = MEMUserRepository.getInstance();
    }

    public static int getTEACHER_ROLEID() {
        return TEACHER_ROLEID;
    }

    public static int getSTUDENT_ROLEID() {
        return STUDENT_ROLEID;
    }

    @Override
    public List<ClassRoom> getClassesByUserId(int userId) {
        return enrollClassRoomRepository.getClassesByUserId(userId);
    }

    @Override
    public List<ClassRoom> getClassesUserAsTeacher(int userId) {
        return enrollClassRoomRepository.getEnrollClassesByUserId(userId).stream()
                .filter(enrollClassRoom -> enrollClassRoom.getRole()==TEACHER_ROLEID)
                .map(enrollClassRoom -> enrollClassRoom.getClassRoom())
                .toList();
    }

    @Override
    public List<ClassRoom> getClassesUserAsStudent(int userId) {
        return enrollClassRoomRepository.getEnrollClassesByUserId(userId).stream()
                .filter(enrollClassRoom -> enrollClassRoom.getRole()==STUDENT_ROLEID)
                .map(enrollClassRoom -> enrollClassRoom.getClassRoom())
                .toList();
    }

    @Override
    public List<User> getStudentInClass(int classId) {
        return  enrollClassRoomRepository.getByClassId(classId).stream()
                .filter(enrollClassRoom -> enrollClassRoom.getRole()== STUDENT_ROLEID)
                .map(enrollClassRoom -> enrollClassRoom.getUser())
                .toList();
    }

    @Override
    public List<User> getTeacherInClass(int classId) {
        return  enrollClassRoomRepository.getByClassId(classId).stream()
                .filter(enrollClassRoom -> enrollClassRoom.getRole()== TEACHER_ROLEID)
                .map(enrollClassRoom -> enrollClassRoom.getUser())
                .toList();
    }

    @Override
    public int getRoleByUserId(int userId, int classId) {
        return this.getEnrollClassRoom(userId, classId).getRole();
    }

    @Override
    public void switchRole(int userId, int classId) {
        int currentRole = this.getRoleByUserId(userId,classId);
        if(currentRole == EnrollClassRoomService.getSTUDENT_ROLEID()){
            currentRole = EnrollClassRoomService.getTEACHER_ROLEID();
        }
        else{
            currentRole = EnrollClassRoomService.getSTUDENT_ROLEID();
        }
        this.getEnrollClassRoom(userId,classId).setRole(currentRole);
    }

    @Override
    public void removeMember(int userId, int classId) {
        EnrollClassRoom enrollClassRoom = this.getEnrollClassRoom(userId,classId);
        enrollClassRoomRepository.remove(enrollClassRoom);
    }

    @Override
    public EnrollClassRoom getEnrollClassRoom(int userId, int classId) {
        return enrollClassRoomRepository.getEnrollClassesByUserId(userId).stream()
                .filter(enrollClassRoom -> enrollClassRoom.getClassRoom().getId()==classId)
                .findFirst().get();
    }

    @Override
    public void add(EnrollClassRoom enrollClassRoom) {
        enrollClassRoomRepository.add(enrollClassRoom);
    }

    @Override
    public boolean join(String inviteCode,int userId) {
        ClassRoom classRoom = classRoomRepository.getByCode(inviteCode);
        if(isEnrolled(classRoom.getId(),userId)){
            return false;
        }
        if(classRoom !=null){
            EnrollClassRoom enrollClassRoom = new EnrollClassRoom();
            enrollClassRoom.setUser(userRepository.getById(userId));
            enrollClassRoom.setClassRoom(classRoom);
            enrollClassRoom.setRole(EnrollClassRoomService.getSTUDENT_ROLEID());
            enrollClassRoom.setEnrollAt(new Date(System.currentTimeMillis()));
            enrollClassRoomRepository.add(enrollClassRoom);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean isEnrolled(int classRoomId, int userId) {
        try{
            enrollClassRoomRepository.getClassesByUserId(userId).stream()
                    .filter(classRoom -> classRoom.getId()==classRoomId).findFirst().get();
            return true;
        }
        catch (NoSuchElementException e){
            return false;
        }
    }
}
