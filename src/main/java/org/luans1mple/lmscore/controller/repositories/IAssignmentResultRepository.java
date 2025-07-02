package org.luans1mple.lmscore.controller.repositories;

import org.luans1mple.lmscore.controller.model.dbo.AssignmentResult;

import java.util.List;

public interface IAssignmentResultRepository {
    public List<AssignmentResult> getByAssignment(int assignmentId);
    public void mark(int score,String comment, AssignmentResult assignmentResult);
    public void add(AssignmentResult assignmentResult);
    public int size();
    public void update(AssignmentResult assignmentResult);
}
