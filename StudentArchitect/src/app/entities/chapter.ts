import { Section } from './section';
export class Chapter {
    chapterId: number;
    chapterNo: number;
    name: string;
    description: string;
    numberOfSections: number;
    sections: Section[];
    subjectId: number;

}