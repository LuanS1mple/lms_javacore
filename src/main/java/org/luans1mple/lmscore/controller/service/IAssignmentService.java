package org.luans1mple.lmscore.controller.service;

import org.luans1mple.lmscore.controller.model.dbo.Assignment;

import java.util.List;

public interface IAssignmentService {
    public List<Assignment> getExpireAssignment(int classId);
    public List<Assignment> getGoingAssignment(int classId);
    public boolean isExpireAssignment(Assignment assignment);
    public List<Assignment> getAssignment(int classId);
    public void downLoad(Assignment assignment);
    public int size();
    public String uploadMeterial();
    public void add(Assignment assignment);
}
