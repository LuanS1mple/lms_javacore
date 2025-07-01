package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.EnrollClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.User;

import java.util.List;

public interface IEnrollClassRoomRepository {
    public List<ClassRoom> getClassesByUserId(int userId);
    public List<EnrollClassRoom> getEnrollClassesByUserId(int userId);
    public List<EnrollClassRoom>  getByClassId(int classId);
    public void remove(EnrollClassRoom enrollClassRoom);
    public void add(EnrollClassRoom enrollClassRoom);
}
