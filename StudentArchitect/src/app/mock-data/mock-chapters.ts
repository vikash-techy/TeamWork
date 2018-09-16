import { Chapter } from './../entities/chapter';
/*
export const MOCK_BOARD : Board[] = [
    {boardId: 1, name: "CBSE"},
    {boardId: 2, name: "ICSE"}
];
*/

export const MOCK_CHAPTERS : Chapter[] = [
    
    {chapterId:1, chapterNo: 1, name: "Gravity-1", description: "Gravity Desc", numberOfSections: 15, sections:[{sectionId:1, name: "section1", description:"section1 Desc", videoPath:"https://www.youtube.com/watch?time_continue=487&v=6rLLZ_NSr8g", videoDuration:"10 min", chapterId: 1},
    {sectionId:2, name: "section2", description:"section1 Desc", videoPath:"https://www.youtube.com/watch?time_continue=487&v=6rLLZ_NSr8g", videoDuration:"10 min", chapterId: 1}
    ], subjectId: 11},
    
    {chapterId:2, chapterNo: 2, name: "Gravity-2", description: "Gravity Desc", numberOfSections: 14, sections:[{sectionId:3, name: "section3", description:"section1 Desc", videoPath:"https://www.youtube.com/watch?time_continue=487&v=6rLLZ_NSr8g", videoDuration:"10 min", chapterId: 2},
    {sectionId:4, name: "section4", description:"section1 Desc", videoPath:"https://www.youtube.com/watch?time_continue=487&v=6rLLZ_NSr8g", videoDuration:"10 min", chapterId: 2}], subjectId: 11},
    
    {chapterId:3, chapterNo: 1, name: "Gravity-3", description: "Gravity Desc", numberOfSections: 11, sections:[{sectionId:5, name: "section5", description:"section1 Desc", videoPath:"https://www.youtube.com/watch?time_continue=487&v=6rLLZ_NSr8g", videoDuration:"10 min", chapterId: 3},
    {sectionId:6, name: "section6", description:"section1 Desc", videoPath:"https://www.youtube.com/watch?time_continue=487&v=6rLLZ_NSr8g", videoDuration:"10 min", chapterId: 3}], subjectId: 12},
    
    {chapterId:4, chapterNo: 2, name: "Gravity-4", description: "Gravity Desc", numberOfSections: 9, sections:[{sectionId:7, name: "section7", description:"section1 Desc", videoPath:"https://www.youtube.com/watch?time_continue=487&v=6rLLZ_NSr8g", videoDuration:"10 min", chapterId: 4},
    {sectionId:8, name: "section8", description:"section1 Desc", videoPath:"https://www.youtube.com/watch?time_continue=487&v=6rLLZ_NSr8g", videoDuration:"10 min", chapterId: 4}], subjectId: 12},
    
    {chapterId:5, chapterNo: 1, name: "Gravity-5", description: "Gravity Desc", numberOfSections: 16, sections:[], subjectId: 13},
    
    {chapterId:6, chapterNo: 2, name: "Gravity-6", description: "Gravity Desc", numberOfSections: 14, sections:[], subjectId: 13},
    
    {chapterId:7, chapterNo: 1, name: "Gravity-7", description: "Gravity Desc", numberOfSections: 17, sections:[], subjectId: 14},
    
    {chapterId:8, chapterNo: 2, name: "Gravity-8", description: "Gravity Desc", numberOfSections: 19, sections:[], subjectId: 14},
    
    {chapterId:9, chapterNo: 1, name: "Gravity-9", description: "Gravity Desc", numberOfSections: 8, sections:[], subjectId: 15},
    
    {chapterId:10, chapterNo: 2, name: "Gravity-10", description: "Gravity Desc", numberOfSections: 10, sections:[], subjectId: 15},
    
    {chapterId:11, chapterNo: 1, name: "Gravity-11", description: "Gravity Desc", numberOfSections: 15, sections:[], subjectId: 16},
    
    {chapterId:12, chapterNo: 2, name: "Gravity-12", description: "Gravity Desc", numberOfSections: 12, sections:[], subjectId: 16},
    
];