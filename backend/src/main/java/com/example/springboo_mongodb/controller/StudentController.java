package com.example.springboo_mongodb.controller;

import com.example.springboo_mongodb.exception.ResourceNotFoundException;
import com.example.springboo_mongodb.model.Student;
import com.example.springboo_mongodb.service.StudentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping("/students")
    public List<Student> fetchAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student studentDetails) {
        return studentService.getStudentById(id)
                .map(auxStudent -> {
                    auxStudent.setFirstName(studentDetails.getFirstName());
                    auxStudent.setLastName(studentDetails.getLastName());
                    auxStudent.setAddress(studentDetails.getAddress());
                    auxStudent.setEmail(studentDetails.getEmail());
                    auxStudent.setGender(studentDetails.getGender());
                    auxStudent.setFavouriteSubjects(studentDetails.getFavouriteSubjects());
                    auxStudent.setTotalSpentInBooks(studentDetails.getTotalSpentInBooks());
                    auxStudent.setCreated(studentDetails.getCreated());

                    Student updatedStudent = studentService.updateStudent(auxStudent);
                    return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable String id) {
        studentService.getStudentById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Student with Id: %s not found", id)));

        studentService.deleteStudent(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);


        return ResponseEntity.ok(response);
    }
}
