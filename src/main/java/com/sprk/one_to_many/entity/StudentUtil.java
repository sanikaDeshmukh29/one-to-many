package com.sprk.one_to_many.entity;

import org.springframework.stereotype.Component;

@Component
public class StudentUtil {


    public StudentDto studentToStudentDto(Student student){
        StudentDto studentDto = new StudentDto();

        studentDto.setRollNo(student.getRollNo());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setPhone(student.getPhone());

        return studentDto;

    }
}
