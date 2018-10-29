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
  displaySubjects: Subject[];

  selectedBoardId: number;
  selectedStandardId: number;


  constructor(private subjectService: SubjectService, private appService: AppService) { }

  ngOnInit() {
    this.getBoards();
  }
  // This method will get all the Educational Boards
  getBoards(): void {
    this.appService.getBoards().subscribe((response) => {
      this.boards = response;
      this.selectedBoardId = this.boards[0].boardId;
      this.getClasses();
    });
  }

  // This method will get all the Classes Supported for the selected Board
  getClasses(): void {
    console.log("selctecd board => " + this.selectedBoardId);
    this.appService.getClasses(this.selectedBoardId).subscribe((classes) => {
      this.classes = classes;
      this.selectedStandardId = this.classes[0].standardId;
      console.log("selectedStandardId => " + this.selectedStandardId);
      this.getSubjects();
    });

  }

  // This method will get all the subject for a given class
  getSubjects(): void {
    this.subjectService.getSubjects(this.selectedStandardId).subscribe((subjects) => {
      this.subjects = subjects;
      this.displaySubjects = this.subjects;
    });

  }

  getSubject(): void {
    this.subjects = this.displaySubjects.filter(Subject => (Subject.isSelected == true));

    // If All the subjects are de selected, then show all of them.
    if (this.subjects.length == 0) {
      this.subjects = this.displaySubjects;
    }
  }
}
