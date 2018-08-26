import { Section } from './../entities/section';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { MOCK_SECTIONS } from '../mock-data/mock-sections';

@Injectable({
  providedIn: 'root'
})
export class SectionService {

  constructor() { }

  // Return all the Sections for the given ChapterId
  getSections(chapterId: number) : Observable<Section[]> {
    return of(MOCK_SECTIONS.filter(section => section.chapterId == chapterId));
  }
}
