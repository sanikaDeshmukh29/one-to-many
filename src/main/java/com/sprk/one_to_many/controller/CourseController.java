package com.sprk.one_to_many.controller;

import com.sprk.one_to_many.entity.Courses;
import com.sprk.one_to_many.entity.Student;
import com.sprk.one_to_many.repository.CourseRepository;
import com.sprk.one_to_many.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;


    @PostMapping("/add-course/{rollNo}")
    public ResponseEntity<?> addCourse(@PathVariable int rollNo, @RequestBody Courses course) {

        Student existStudent = studentRepository.findById(rollNo).orElse(null);

        if (existStudent == null) {
            return ResponseEntity.status(404).body("Student not found");
        } else {

            //logic to add course
            course.setStudent(existStudent);
            courseRepository.save(course);
            List<Courses> courses = existStudent.getCourses();

            if (course == null) {
                new ArrayList<>();
            }
            courses.add(course);
            existStudent.setCourses(courses);
            return ResponseEntity.status(201).body(existStudent);

        }


    }

    @PostMapping("/add-courses/{rollNo}")
    public ResponseEntity<?> addCourses(@PathVariable int rollNo, @RequestBody List<Courses> courses) {

        Student existStudent = studentRepository.findById(rollNo).orElse(null);

        if (existStudent == null) {
            return ResponseEntity.status(404).body("Student not found");
        } else {

            if(courses.size() == 1){

                addCourse(rollNo, courses.get(0));
            }

            //logic to add courses
            List<Courses> savedCourses = new ArrayList<>();
            for(Courses course : courses){
                course.setStudent(existStudent);
                Courses savedCourse = courseRepository.save(course);
               savedCourses.add(savedCourse);
            }

            List<Courses> existingStudentCourses = existStudent.getCourses();
            if(existingStudentCourses == null){
                existingStudentCourses = new ArrayList<>();
            }

            existingStudentCourses.addAll(savedCourses);
            existStudent.setCourses(savedCourses);
            return ResponseEntity.status(201).body(existStudent);

        }


    }

}
