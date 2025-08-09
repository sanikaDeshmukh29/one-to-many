package com.sprk.one_to_many.entity;

import lombok.Data;

@Data

public class CourseDto {

    private String courseName;

    private String courseDescription;

    private String courseDuration;

    private int StudentId;

}
