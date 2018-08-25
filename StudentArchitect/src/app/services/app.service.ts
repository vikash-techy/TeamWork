import { MOCK_CLASSES } from './../mock-data/mock-classes';
import { Class } from './../entities/class';
import { MOCK_BOARD } from './../mock-data/mock-bords';
import { Board } from './../entities/board';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor() { }

  getBoards(): Observable<Board[]> {
    return of(MOCK_BOARD);
  }

  getClasses(boardId: number): Observable<Class[]> {
    return of(MOCK_CLASSES.filter(Class => Class.boardId == boardId));
  }


}
