import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Student } from './Student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private baseURL = "http://localhost:8080/api/v1/students";
  constructor(private httpClient: HttpClient) { }

  getStudentList(): Observable<Student[]> {
    return this.httpClient.get<Student[]>(`${this.baseURL}`);
  }

  createStudent(student: Student): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, student);
  }

  getStudentById(id: string): Observable<Student> {
    return this.httpClient.get<Student>(`${this.baseURL}/${id}`);
  }

  updateStudent(id: string, student: Student): Observable<Object> {
    return this.httpClient.put(`${this.baseURL}/${id}`, student);
  }

  deleteStudent(id: string): Observable<Object> {
    return this.httpClient.delete(`${this.baseURL}/${id}`);
  }

}
