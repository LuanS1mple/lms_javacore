package org.luans1mple.lmscore.controller.service;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.EnrollClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;

import java.util.List;

public interface IEnrollClassRoomSerivce {
    public List<ClassRoom> getClassesByUserId(int userId);
    public List<ClassRoom> getClassesUserAsTeacher(int userId);
    public List<ClassRoom> getClassesUserAsStudent(int userId);
    public List<User> getStudentInClass(int classId);
    public List<User> getTeacherInClass(int classId);
    public int getRoleByUserId(int userId, int classId);
    public void switchRole(int userId,int classId);
    public void removeMember(int userId,int classId);
    public EnrollClassRoom getEnrollClassRoom(int userId,int classId);
    public void add(EnrollClassRoom enrollClassRoom);
    public boolean join(String inviteCode,int userId);
    public boolean isEnrolled(int classRoomId, int userId);
}
