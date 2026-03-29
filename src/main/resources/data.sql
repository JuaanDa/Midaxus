INSERT INTO users (id, user_name, user_type, dtype, first_name, last_name, email, password)
VALUES ('1', 'admin1', 'ADMIN','ADMIN', 'Carlos', 'Admin', 'c.admin@unbosque.edu.co', '1234');

INSERT INTO admin (id, admin_id)
VALUES ('1', 'ADM001');

INSERT INTO users (id, user_name, user_type, dtype, first_name, last_name, email, password)
VALUES ('2', 'teacher1', 'TEACHER', 'TEACHER', 'Laura', 'Gomez', 'l.gomez@unbosque.edu.co', '1234');

INSERT INTO teacher (id, teacher_id, start_date)
VALUES ('2', 'TCH001', '2024-01-01');

INSERT INTO users (id, user_name, user_type,dtype, first_name, last_name, email, password)
VALUES ('3', 'student1', 'STUDENT','STUDENT', 'Andres', 'Perez', 'a.perez@unbosque.edu.co', '1234');

INSERT INTO student (id, student_id)
VALUES ('3', 'STD001');


INSERT INTO users (id, user_name, user_type, dtype, first_name, last_name, email, password)
VALUES ('4', 'student2', 'STUDENT','STUDENT', 'Maria', 'Lopez', 'm.lopez@unbosque.edu.co', '1234');

INSERT INTO student (id, student_id)
VALUES ('4', 'STD002');