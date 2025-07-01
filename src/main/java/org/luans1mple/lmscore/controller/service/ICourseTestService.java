package org.luans1mple.lmscore.controller.service;

import org.luans1mple.lmscore.controller.model.dbo.Course;
import org.luans1mple.lmscore.controller.model.dbo.CourseTest;

import java.util.List;

public interface ICourseTestService {
        public List<CourseTest> getByCourseId(int courseId);
        public void downLoadCourseTest(CourseTest test);
        public List<CourseTest> getByTittle(String pattern, int courseId);
        public void add(CourseTest test);
        public int size();
        public String getCourseTestUrl();
}
