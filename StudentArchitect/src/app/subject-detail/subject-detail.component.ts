import { SubjectService } from './../services/subject.service';
import { Subject } from './../entities/subject';

import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-subject-detail',
  templateUrl: './subject-detail.component.html',
  styleUrls: ['./subject-detail.component.css']
})
export class SubjectDetailComponent implements OnInit {

  @Input() subject: Subject;

  constructor (
    private subjectService: SubjectService ,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit() {
    this.getSubject();
  }

  getSubject(): void {
    const id = +this.route.snapshot.paramMap.get('subjectId');
    this.subjectService.getSubject(id)
      .subscribe(subject => this.subject = subject);
  }

}
