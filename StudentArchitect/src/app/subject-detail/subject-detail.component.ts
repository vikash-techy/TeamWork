import { Section } from './../entities/section';
import { Chapter } from './../entities/chapter';
import { SectionService } from './../services/section.service';
import { ChapterService } from './../services/chapter.service';
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

  chapters: Chapter[];
  sections: Section[];
  selectedSubjectId: number;
  selectedSectionId: number;
  selectedSection: Section;
  

  constructor (
    private subjectService: SubjectService ,
    private chapterService: ChapterService ,
    private sectionService: SectionService ,
    private route: ActivatedRoute ,
    private location: Location
  ) { }

  ngOnInit() {
    this.getSubject();
    this.getChapters();
  }

  getSubject(): void {
    this.selectedSubjectId = +this.route.snapshot.paramMap.get('subjectId');
    this.subjectService.getSubject(this.selectedSubjectId)
      .subscribe(subject => this.subject = subject);
  }

  getChapters(): void {
    this.chapterService.getChapters(this.selectedSubjectId).subscribe(chapters => this.chapters = chapters);
  }

  getSections(): void {
    this.sectionService.getSections(this.selectedSectionId).subscribe(sections => this.sections = sections);
  }

  setSectionId(sectionId) : void {
    this.selectedSectionId = sectionId;console.log('selectedSectionId='+this.selectedSectionId);
    this.sectionService.getSections(this.selectedSectionId).subscribe(sections => {this.sections = sections;
      this.selectedSection = this.sections[0];});
    console.log('selectedSection='+this.selectedSection);
  }
}
