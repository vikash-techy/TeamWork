import { Injectable } from '@angular/core';
import { Subject } from '../subject';
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
}
