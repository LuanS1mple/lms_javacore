use lms_cpl
CREATE TABLE [User](
    id INT PRIMARY KEY IDENTITY(1,1),
    email NVARCHAR(255)  UNIQUE,
    password NVARCHAR(255) ,
    fullName NVARCHAR(255),
    phone NVARCHAR(20),
    address NVARCHAR(255),
    avatarUrl NVARCHAR(500),
    role INT ,
    status INT ,
    createdAt DATETIME DEFAULT GETDATE()
);
CREATE TABLE Course (
    id INT PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(255) ,
    description NVARCHAR(MAX),
    category NVARCHAR(100),
    level NVARCHAR(50),
    duration INT,
    status NVARCHAR(50),
    author NVARCHAR(255),
    createdAt DATETIME DEFAULT GETDATE(),
    updatedAt DATETIME NULL
);
CREATE TABLE ClassRoom (
    id INT PRIMARY KEY IDENTITY(1,1),
    className NVARCHAR(255) ,
    createAt DATETIME DEFAULT GETDATE(),
    createBy INT ,
    inviteCode NVARCHAR(100) UNIQUE,
    FOREIGN KEY (createBy) REFERENCES [User](id)
);
CREATE TABLE CourseContent (
    id INT PRIMARY KEY IDENTITY(1,1),
    courseId INT ,
    title NVARCHAR(255) ,
    type NVARCHAR(50), -- ví dụ: 'video', 'document', v.v.
    contentUrl NVARCHAR(500),
    duration INT NULL, -- có thể null nếu là tài liệu
    orderIndex INT ,
    createdAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (courseId) REFERENCES Course(id)
);
CREATE TABLE CourseTest (
    id INT PRIMARY KEY IDENTITY(1,1),
    courseId INT ,
    testTitle NVARCHAR(255) ,
    testDescription NVARCHAR(MAX),
    type NVARCHAR(50), -- ví dụ: 'quiz', 'exam', v.v.
    testUrl NVARCHAR(500),
    maxScore INT ,
    duration INT , -- thời lượng, đơn vị là phút hoặc giây
    status INT ,
    FOREIGN KEY (courseId) REFERENCES Course(id)
);
CREATE TABLE CourseTestResult (
    userId INT ,
    courseTestId INT ,
    mark INT ,
    doneAt DATETIME ,
    status INT ,
    PRIMARY KEY (userId, courseTestId),
    FOREIGN KEY (userId) REFERENCES [User](id),
    FOREIGN KEY (courseTestId) REFERENCES CourseTest(id)
);
CREATE TABLE EnrollCourse (
    userId INT ,
    courseId INT ,
    enrollAt DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (userId, courseId),
    FOREIGN KEY (userId) REFERENCES [User](id),
    FOREIGN KEY (courseId) REFERENCES Course(id)
);
CREATE TABLE EnrollClassRoom (
    userId INT ,
    classRoomId INT ,
    enrollAt DATETIME DEFAULT GETDATE(),
    role INT , -- ví dụ: 0 = học viên, 1 = giáo viên
    PRIMARY KEY (userId, classRoomId),
    FOREIGN KEY (userId) REFERENCES [User](id),
    FOREIGN KEY (classRoomId) REFERENCES ClassRoom(id)
);
CREATE TABLE Assignment (
    id INT PRIMARY KEY IDENTITY(1,1),
    classRoomId INT ,
    title NVARCHAR(255) ,
    description NVARCHAR(MAX),
    assignmentUrl NVARCHAR(500),
    startAt DATETIME ,
    endAt DATETIME ,
    maxScore INT ,
    createBy INT ,
    isAllowLate BIT ,
    FOREIGN KEY (classRoomId) REFERENCES ClassRoom(id),
    FOREIGN KEY (createBy) REFERENCES [User](id)
);
CREATE TABLE AssignmentResult (
    userId INT ,
    assignmentId INT ,
    doneAt DATETIME ,
    submissionUrl NVARCHAR(500),
    mark INT,
    comment NVARCHAR(MAX),
    status INT ,
    PRIMARY KEY (userId, assignmentId),
    FOREIGN KEY (userId) REFERENCES [User](id),
    FOREIGN KEY (assignmentId) REFERENCES Assignment(id)
);




