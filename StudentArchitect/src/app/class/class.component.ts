import { AppService } from './../services/app.service';
import { Class } from './../entities/class';
import { Board } from './../entities/board';
import { Subject } from '../entities/subject';
import { Component, OnInit } from '@angular/core';
import { SubjectService } from '../services/subject.service';

@Component({
  selector: 'app-class',
  templateUrl: './class.component.html',
  styleUrls: ['./class.component.css']
})
export class ClassComponent implements OnInit {


  boards: Board[];
  classes: Class[];
  subjects: Subject[];

  selectedBoardId: number;
  selectedClassId: number;


  constructor(private subjectService: SubjectService, private appService: AppService) { }

  ngOnInit() {
    this.getBoards();
    this.getClasses();
    this.getSubjects();
    
  }
  // This method will get all the Educational Boards
  getBoards(): void {
    this.appService.getBoards().subscribe(boards => this.boards = boards);
    this.selectedBoardId = this.boards[0].boardId;
  }


  // This method will get all the Classes Supported for the selected Board
  getClasses(): void {
    console.log("selctecd board => " + this.selectedBoardId);
    this.appService.getClasses(this.selectedBoardId).subscribe(classes => this.classes = classes);
    this.selectedClassId = this.classes[1].classId;
    console.log("selectedClassId => " + this.selectedClassId);
    this.getSubjects();
  }


  // This method will get all the subject for a given class
  getSubjects(): void {
    this.subjectService.getSubjects(this.selectedClassId).subscribe(subjects => this.subjects = subjects);
  }


}
