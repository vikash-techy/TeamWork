import { Subject } from './../entities/subject';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-subject-detail',
  templateUrl: './subject-detail.component.html',
  styleUrls: ['./subject-detail.component.css']
})
export class SubjectDetailComponent implements OnInit {

  @Input() subject: Subject;

  constructor() { }

  ngOnInit() {
  }

}
