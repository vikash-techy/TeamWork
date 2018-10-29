import { HttpClient } from '@angular/common/http';
import { MOCK_CLASSES } from './../mock-data/mock-classes';
import { Class } from './../entities/class';
import { MOCK_BOARD } from './../mock-data/mock-bords';
import { Board } from './../entities/board';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  baseUrl: string = "http://localhost:8090/api";
  boardsEndpoint: string = "/boards";
  classesEndpoint: string = "/standards";
  boards :Board[];
  constructor(private http: HttpClient) { }

  getBoards(): Observable<Board[]> {
   return this.http.get<Board[]>(this.baseUrl + this.boardsEndpoint);
  }


  getClasses(boardId: number): Observable<Class[]> {
    return this.http.get<Class[]>(this.baseUrl + this.boardsEndpoint + "/" + boardId + this.classesEndpoint);
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
 
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      return of(result as T);
    };
  }


}
