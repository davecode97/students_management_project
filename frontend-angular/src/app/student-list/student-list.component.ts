import { Component, OnInit } from '@angular/core';
import { Student } from '../Student';
import { StudentService } from '../student.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.css']
})
export class StudentListComponent implements OnInit {
  students: Student[];

  constructor(private studentService: StudentService, private router: Router) { } 

  ngOnInit(): void {
    this.getStudents();
  }

  private getStudents() {
    this.studentService.getStudentList().subscribe(data => {
      this.students = data;
    });
  }

  studentDetails(id: string) {
    this.router.navigate(['students-details', id]);
  }

  updateStudent(id: string) {
    this.router.navigate(['update-student', id]);
  }

  deleteStudent(id: string) {
    this.studentService.deleteStudent(id).subscribe(data => {
      console.log(data);
      this.getStudents();
    })
  }
}
