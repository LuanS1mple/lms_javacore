package org.luans1mple.lmscore.controller.repositories.mem;

import org.luans1mple.lmscore.controller.model.dbo.CourseTestResult;
import org.luans1mple.lmscore.controller.repositories.ICourseTestResultRepository;

import java.util.ArrayList;
import java.util.List;

public class MEMCourseTestResultRepository implements ICourseTestResultRepository {
    private static final MEMCourseTestResultRepository instance = new MEMCourseTestResultRepository();
    private List<CourseTestResult> results = new ArrayList<>();
    private MEMCourseTestResultRepository(){

    }
    public static MEMCourseTestResultRepository getInstance(){
        return instance;
    }
    @Override
    public void add(CourseTestResult result) {
        results.add(result);
    }

    @Override
    public List<CourseTestResult> getResults(int userId,int courseTestId) {
        return  results.stream()
                .filter(result -> result.getCourseTest().getId()==courseTestId
                        && result.getUser().getId()==userId).toList();
    }
}
