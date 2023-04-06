package com.example.springboot_mongodb.service;

import com.example.springboot_mongodb.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    Student saveStudent(Student student);

    List<Student> getAllStudents();

    Optional<Student> getStudentById(String Id);

    Student updateStudent(Student student);

    void deleteStudent(String id);
}
