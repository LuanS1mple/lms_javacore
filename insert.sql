INSERT INTO [User] (email, password, fullName, phone, address, avatarUrl, role, status)
VALUES 
('alice@example.com', '123456', 'Alice Nguyen', '0901234567', 'Hanoi', NULL, 1, 1),
('bob@example.com', '123456', 'Bob Tran', '0907654321', 'HCMC', NULL, 0, 1);


INSERT INTO Course (title, description, category, level, duration, status, author)
VALUES 
('Java Basics', 'Introduction to Java programming', 'Programming', 'Beginner', 60, 'active', 'Alice Nguyen'),
('Web Development', 'Learn HTML, CSS, JS', 'Web', 'Intermediate', 90, 'active', 'Bob Tran');

INSERT INTO ClassRoom (className, createBy, inviteCode)
VALUES 
('Java Class A', 1, 'JAVA123'),
('Web Class B', 2, 'WEB456');
INSERT INTO CourseContent (courseId, title, type, contentUrl, duration, orderIndex)
VALUES 
(1, 'Intro to Java', 'video', 'C:\Users\thanh\OneDrive\Documents\test.txt', 15, 1),
(1, 'Java Variables', 'document', 'C:\Users\thanh\OneDrive\Documents\test.txt', NULL, 2),
(2, 'HTML Basics', 'video', 'C:\Users\thanh\OneDrive\Documents\test.txt', 20, 1);

INSERT INTO CourseTest (courseId, testTitle, testDescription, type, testUrl, maxScore, duration, status)
VALUES 
(1, 'Java Quiz 1', 'Basic Java concepts', 'quiz', 'C:\Users\thanh\OneDrive\Documents\test.txt', 10, 15, 1),
(2, 'Web Quiz', 'HTML/CSS Basics', 'quiz','C:\Users\thanh\OneDrive\Documents\test.txt', 15, 20, 1);

INSERT INTO CourseTestResult (userId, courseTestId, mark, doneAt, status)
VALUES 
(1, 1, 8, GETDATE(), 1),
(2, 2, 12, GETDATE(), 1);

INSERT INTO EnrollCourse (userId, courseId)
VALUES 
(1, 1),
(2, 2);

INSERT INTO EnrollClassRoom (userId, classRoomId, role)
VALUES 
(1, 1, 0), -- Alice là học viên
(2, 2, 1); -- Bob là giáo viên

INSERT INTO Assignment (classRoomId, title, description, assignmentUrl, startAt, endAt, maxScore, createBy, isAllowLate)
VALUES 
(1, 'Java Homework 1', 'Complete exercises 1-5', 'C:\Users\thanh\OneDrive\Documents\test.txt', GETDATE(), DATEADD(DAY, 7, GETDATE()), 100, 2, 1),
(2, 'Web Homework 1', 'Create a webpage', 'C:\Users\thanh\OneDrive\Documents\test.txt', GETDATE(), DATEADD(DAY, 5, GETDATE()), 100, 2, 0);

INSERT INTO AssignmentResult (userId, assignmentId, doneAt, submissionUrl, mark, comment, status)
VALUES 
(1, 1, GETDATE(), 'C:\Users\thanh\OneDrive\Documents\test.txt', 95, 'Good job', 1),
(2, 2, GETDATE(), 'C:\Users\thanh\OneDrive\Documents\test.txt', 88, 'Well done', 1);
