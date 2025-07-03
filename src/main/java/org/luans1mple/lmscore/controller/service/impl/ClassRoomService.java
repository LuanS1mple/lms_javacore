package org.luans1mple.lmscore.controller.service.impl;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;
import org.luans1mple.lmscore.controller.model.dbo.EnrollClassRoom;
import org.luans1mple.lmscore.controller.repositories.IClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.IEnrollClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.db.SQLEnrollClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMClassRoomRepository;
import org.luans1mple.lmscore.controller.repositories.mem.MEMEnrollClassRoomRepository;
import org.luans1mple.lmscore.controller.service.IClassRoomService;

import java.sql.Date;
import java.util.List;

public class ClassRoomService implements IClassRoomService {
    private IClassRoomRepository classRoomRepository;
    private IEnrollClassRoomRepository enrollClassRoomRepository;
    public ClassRoomService(){
//        classRoomRepository= MEMClassRoomRepository.getInstance();
        classRoomRepository = SQLClassRoomRepository.getInstance();
//        enrollClassRoomRepository = MEMEnrollClassRoomRepository.getInstance();
        enrollClassRoomRepository = SQLEnrollClassRoomRepository.getInstance();
    }
    @Override
    public List<ClassRoom> getAll() {
        return classRoomRepository.getAll();
    }

    @Override
    public void add(ClassRoom c) {
        ClassRoom classRoom =classRoomRepository.add(c);
        Date now = new Date(System.currentTimeMillis());
        EnrollClassRoom enrollClassRoom = new EnrollClassRoom();
        enrollClassRoom.setUser(c.getCreateBy());
        enrollClassRoom.setClassRoom(classRoom);
        enrollClassRoom.setEnrollAt(now);
        enrollClassRoom.setRole(EnrollClassRoomService.getTEACHER_ROLEID());
        enrollClassRoomRepository.add(enrollClassRoom);
    }

    @Override
    public int size() {
        return classRoomRepository.getAll().size();
    }
}
