import { MOCK_CHAPTERS } from './../mock-data/mock-chapters';
import { Chapter } from './../entities/chapter';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ChapterService {

  constructor() { }

  // Return all the Chanpter for the given SubjectId
  getChapters(subjectId: number) : Observable<Chapter[]> {
    return of(MOCK_CHAPTERS.filter(chapter => chapter.subjectId == subjectId));
  }

}
