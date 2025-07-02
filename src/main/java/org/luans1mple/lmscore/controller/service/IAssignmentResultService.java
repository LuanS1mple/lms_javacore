package org.luans1mple.lmscore.controller.service;

import org.luans1mple.lmscore.controller.model.dbo.Assignment;
import org.luans1mple.lmscore.controller.model.dbo.AssignmentResult;

import java.time.LocalDateTime;
import java.util.List;

public interface IAssignmentResultService {
    public List<AssignmentResult> getByAssignment(int assignmentId);
    public List<AssignmentResult> getUnMarkedAssignment(int assignmentId);
    public List<AssignmentResult> getMarkedAssignment(int assignmentId);
    public List<AssignmentResult> search(String pattern,int assignmentId);
    public void mark(int score,String comment, AssignmentResult assignmentResult);
    public void downLoadSubmission(AssignmentResult assignmentResult);
    public boolean submit(Assignment assignment);
    public String getSubmissionUrl();
    public int isLateSubmit(LocalDateTime submitTime);
    public AssignmentResult getResult(int userId, int assignmentId);
    public boolean isSubmitBefore(AssignmentResult assignmentResult);
    public boolean isMarkedBefore(AssignmentResult assignmentResult);
    public void replaceSubmit(AssignmentResult assignmentResult);
}
