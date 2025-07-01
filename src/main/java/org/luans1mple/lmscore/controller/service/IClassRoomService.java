package org.luans1mple.lmscore.controller.service;

import org.luans1mple.lmscore.controller.model.dbo.ClassRoom;

import java.util.List;

public interface IClassRoomService {
    public List<ClassRoom> getAll();
    public void add(ClassRoom c);
    public int size();
}
