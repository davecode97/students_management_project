package com.example.springboot_mongodb.service;

import com.example.springboot_mongodb.model.Student;
import com.example.springboot_mongodb.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentService implements IStudentService{
    private final StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        Optional<Student> saveStudent = studentRepository.findStudentByEmail(student.getEmail());
        if (saveStudent.isPresent())
            System.out.printf("Student with email: %s is present", student.getEmail()); // logger ERROR
       return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student updateStudent(Student updatedStudent) {
        return studentRepository.save(updatedStudent);
    }

    @Override
    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

}
