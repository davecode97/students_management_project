package com.example.springboot_mongodb;

import com.example.springboot_mongodb.model.Student;
import com.example.springboot_mongodb.repository.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@SpringBootApplication

public class SpringbootMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMongodbApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
//		return args -> {
//			Address address = new Address(
//					"England",
//					"London",
//					"NE9"
//			);
//
//			String email = "example@gmail.com";
//
//			Student student = new Student(
//					"Jamila",
//					"Ahmed",
//					"example5@gmail.com",
//					Gender.FEMALE,
//					address,
//					List.of("Computer Sciences"),
//					BigDecimal.TEN,
//					LocalDateTime.now()
//			);
//			repository.insert(student);
////			usingMongoTemplateAndQuery(repository, mongoTemplate, email, student)
//			repository.findStudentByEmail(email)
//				.ifPresentOrElse(student1 -> {
//					System.out.println(student1 + " already exits");
//				}, () -> {
//					System.out.println("Inserting student " + student);
//					repository.insert(student);
//				});
//		};
//	}

	public void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if (students.size() > 1) {
			throw new IllegalStateException("Found many with email " + email);
		}
		if (students.isEmpty()) {
			System.out.println("Inserting student " + student);
			repository.insert(student);
		} else {
			System.out.println(student + " already exits");
		}
	}
}