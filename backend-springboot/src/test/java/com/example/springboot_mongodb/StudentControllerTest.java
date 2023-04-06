package com.example.springboot_mongodb;

import com.example.springboot_mongodb.model.Address;
import com.example.springboot_mongodb.model.Gender;
import com.example.springboot_mongodb.model.Student;
import com.example.springboot_mongodb.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

//    @SpyBean
//    private StudentRepository studentRepositoryMock;


    // POST: Save student - positive scenario
    @Test
    public void givenStudentObject_whenCreateStudent_thenReturnSavedStudent() throws Exception {
        // GIVEN: Precondition or setup
        Address address = new Address("Mexico", "MexicoCity", "80000");
        Student student = Student.builder()
                .firstName("TestName")
                .lastName("TestLastName")
                .email("TestEmail@gmail.com")
                .gender(Gender.FEMALE)
                .address(address)
                .totalSpentInBooks(BigDecimal.TEN)
                .favouriteSubjects(List.of("Computer Sciences"))
                .created(LocalDateTime.now())
                .build();

        given(studentServiceMock.saveStudent(any(Student.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // WHEN: action or behaviour that are going test
        ResultActions response =  mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        // THEN: verify the result or output using assert statements
        response.andDo(print())
                .andExpect(jsonPath("$.firstName",
                        is(student.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(student.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(student.getEmail())))
                .andExpect(jsonPath("$.gender",
                        is("FEMALE")))
//                .andExpect(jsonPath("$.address",
//                        is(student.getAddress())))
//                .andExpect(jsonPath("$.totalSpentInBooks",
//                        is(BigDecimal.valueOf(student.getTotalSpentInBooks().intValue()))))
                .andExpect(jsonPath("$.favouriteSubjects",
                        is(student.getFavouriteSubjects())));
//                .andExpect(jsonPath("$.created",
//                        is(student.getCreated())));
    }

    // POST: Save student - negative scenario
//    @Test
//    public void givenStudentObject_whenCreateStudent_thenReturnResourceNotFoundException() throws Exception {
//        // GIVEN: Precondition or setup
//        Address address = new Address("Mexico", "MexicoCity", "80000");
//        Student student = Student.builder()
//                .firstName("TestName")
//                .lastName("TestLastName")
//                .email("TestEmail@gmail.com")
//                .gender(Gender.FEMALE)
//                .address(address)
//                .totalSpentInBooks(BigDecimal.TEN)
//                .favouriteSubjects(List.of("Computer Sciences"))
//                .created(LocalDateTime.now())
//                .build();
//
//
//        Optional<Student> checkForExistingStudent = studentRepositoryMock.findStudentByEmail(student.getEmail());
//
//        doReturn(true)
//                .when(checkForExistingStudent.isPresent());
//
//        when(studentServiceMock.saveStudent(any(Student.class)));
////        when(studentRepositoryMock.findStudentByEmail(anyString())).thenReturn(checkForExistingStudent.isPresent());
////        given(studentServiceMock.saveStudent(any(Student.class))).willReturn(student);
//
//        // WHEN: action or behaviour that are going test
//        ResultActions response =  mockMvc.perform(post("/api/v1/students")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(student)));
//
//        // THEN: verify the result or output using assert statements
//        response.andExpect(status().is5xxServerError())
//                .andDo(print());
//    }

    // GET: Fetch all students
    @Test
    public void givenListOfStudents_whenGetAllStudents_thenReturnStudentsList() throws Exception {
        // GIVEN: Precondition or setup
        List<Student> listOfStudents = new ArrayList<>();
        listOfStudents.add(Student.builder().firstName("TestName").lastName("TestLast").email("test@gmail.com").build());
        listOfStudents.add(Student.builder().firstName("TestName2").lastName("TestLast2").email("test2@gmail.com").build());

        given(studentServiceMock.getAllStudents())
                .willReturn(listOfStudents);

        // WHEN: Action or the behaviour that we are going test
        ResultActions response = mockMvc
                .perform(get("/api/v1/students"));

        // THEN: Verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfStudents.size())));
    }

    // PUT: Update student - Positive Scenario
    @Test
    public void givenUpdatedStudent_whenUpdateStudent_thenReturnUpdateStudentObjet() throws Exception {
        // GIVEN: Precondition or setup
        Address address = new Address("Mexico", "MexicoCity", "80000");

        String studentId = "1";
        Student savedStudent = Student.builder()
                .firstName("TestName")
                .lastName("TestLastName")
                .email("TestEmail@gmail.com")
                .gender(Gender.FEMALE)
                .address(address)
                .totalSpentInBooks(BigDecimal.TEN)
                .favouriteSubjects(List.of("Computer Sciences"))
                .created(LocalDateTime.now())
                .build();

        Student updatedStudent = Student.builder()
                .firstName("TestName2")
                .lastName("TestLastName2")
                .email("TestEmail2@gmail.com")
                .gender(Gender.MALE)
                .address(address)
                .totalSpentInBooks(BigDecimal.TEN)
                .favouriteSubjects(List.of("Computer Sciences"))
                .created(LocalDateTime.now())
                .build();

        given(studentServiceMock.getStudentById(studentId))
                .willReturn(Optional.of(savedStudent));
        given(studentServiceMock.updateStudent((any(Student.class))))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // WHEN: action or the behaviour that are going test
        ResultActions response = mockMvc
                .perform(put("/api/v1/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)));

        // THEN: verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName",
                        is(updatedStudent.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(updatedStudent.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(updatedStudent.getEmail())));
    }

    // PUT: Update student - Negative Scenario
    @Test
    public void givenUpdatedStudent_whenUpdatedStudent_thenReturn404() throws Exception {
        // GIVEN: Precondition or SetUp
        String id = "1";
        Student savedStudent = Student.builder()
                .firstName("TestName")
                .lastName("TestLastName")
                .email("Test@gmail.com")
                .build();

        Student updatedStudent = Student.builder()
                .firstName("TestNameChange")
                .lastName("TestLastNameChange")
                .email("testChange@gmail.com")
                .build();

        given(studentServiceMock.getStudentById(id)).willReturn(Optional.empty());
        given(studentServiceMock.updateStudent(any(Student.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // WHEN: Action or the behaviour that are going to test
        ResultActions response = mockMvc.perform(put("/api/v1/students/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)));

        // THEN: Verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // DELETE: Delete student with given id
    @Test
    public void givenStudentId_whenDeleteStudent_thenReturn204Response() throws Exception {
        // GIVEN: Precondition or setup
        String id = "1";
        willDoNothing().given(studentServiceMock).deleteStudent(id);

        // WHEN: Action or the behaviour that we are going to test
        ResultActions response = mockMvc
                .perform(delete("/api/v1/students/{id}",id));

        // THEN: Verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }

//    @Test
//    // DELETE: Delete student with given id - Negative Scenario
//    public void givenStudentId_whenDeleteStudent_thenReturn404Response() throws Exception {
//        // GIVEN: Precondition or setup
//        String id = "1";
//        willDoNothing().given(studentServiceMock).deleteStudent(id);
//
//        // WHEN: Action or the behaviour that we are going to test
//        ResultActions response = mockMvc
//                .perform(delete("/api/v1/students/{id}", id));
//
//        // THEN: Verify the output
//        response.andExpect(status().isNotFound())
//                .andDo(print());
//    }

}
