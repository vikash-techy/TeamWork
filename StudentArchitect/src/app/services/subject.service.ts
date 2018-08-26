import { MOCK_SUBJECTS } from './../mock-data/mock-subjects';
import { Injectable } from '@angular/core';
import { Subject } from '../entities/subject';
import { Observable, of } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  constructor() { }

  // Get all the subjects for specific class
  getSubjects(classId : number): Observable<Subject[]> {
    // return of(MOCK_SUBJECTS);
    return of(MOCK_SUBJECTS.filter(Subject => Subject.classId == classId));
  }

  getSubject(id : Number):Observable<Subject> {
    return of(MOCK_SUBJECTS.find(subject => subject.subjectId === id));
    // return of(SUBJECTS.find(class => class.na === name));
  }
}
