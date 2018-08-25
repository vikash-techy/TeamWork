import { Injectable } from '@angular/core';
import { Subject } from '../entities/subject';
import { Observable, of } from 'rxjs';
import { SUBJECTS } from '../mock-data/mock-subjects';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  constructor() { }

  getSubjects(): Observable<Subject[]> {
    return of(SUBJECTS);
  }

  getSubject(name : String):Observable<Subject> {
    return of(SUBJECTS[0]);
  }
}
