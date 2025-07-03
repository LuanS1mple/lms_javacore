package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;

import java.util.List;

public interface IClassRoomRepository {
    public List<ClassRoom> getAll();
    public ClassRoom add(ClassRoom c);
    public List<ClassRoom> getByUserId(int userId);
    public ClassRoom getByCode(String inviteCode);
    public ClassRoom getById(int classId);
}
