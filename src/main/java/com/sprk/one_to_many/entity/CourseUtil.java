package com.sprk.one_to_many.entity;

import org.springframework.stereotype.Component;

@Component
public class CourseUtil {

    public CourseDto courseToCourseDto(Courses courses){

        CourseDto courseDto = new CourseDto();

        courseDto.setCourseName(courses.getCourseName());
        courseDto.setCourseDescription(courses.getCourseDescription());
        courseDto.setCourseDuration(courses.getCourseDuration());
        courseDto.setStudentId(courses.getStudent().getRollNo());

        return courseDto;
    }


}
