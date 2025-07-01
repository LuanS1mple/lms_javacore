package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.Assignment;

import java.util.List;

public interface IAssignmentRepository {
    public List<Assignment> getAssignmentByClass(int classId);
    public int size();
    public void add(Assignment assignment);
}
