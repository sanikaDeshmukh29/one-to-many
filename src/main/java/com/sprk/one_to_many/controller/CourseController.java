package com.sprk.one_to_many.controller;

import com.sprk.one_to_many.entity.CourseDto;
import com.sprk.one_to_many.entity.CourseUtil;
import com.sprk.one_to_many.entity.Courses;
import com.sprk.one_to_many.entity.Student;
import com.sprk.one_to_many.repository.CourseRepository;
import com.sprk.one_to_many.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseUtil courseUtil;


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

    @DeleteMapping("/delete-course/{rollNo}/{courseName}")
    public ResponseEntity<?> deleteCourse(@PathVariable int rollNo, @PathVariable String courseName){

        Student existingStudent = studentRepository.findById(rollNo).orElse(null);

        if(existingStudent == null){
            return ResponseEntity.status(404).body("Student with roll no = " + rollNo + " not found");
        }

        List<Courses> existingStudentCourses = existingStudent.getCourses();
        Iterator<Courses> iterator = existingStudentCourses.iterator();
        boolean foundCourse = false;
        int courseId = 0;

       while(iterator.hasNext()){
           Courses course = iterator.next();
           if(course.getCourseName().equalsIgnoreCase(courseName)){
               foundCourse = true;
               courseId = course.getCourseID();
               iterator.remove();
           }
       }
        existingStudent.setCourses(existingStudentCourses);
        if(foundCourse){
            Courses existingCourse = courseRepository.findById(courseId).get();
            courseRepository.delete(existingCourse);
            return ResponseEntity.ok(existingStudent);

        }else{
            return ResponseEntity.status(404).body("Student with roll no = " + rollNo + " and course = "+courseName+" not found");
        }

    }

    @GetMapping("get-course-by-id/{courseID}")
    public ResponseEntity<?> getCourseById(@PathVariable int courseID){

        Courses dbCourse = courseRepository.findById(courseID).orElse(null);

        CourseDto courseDto =  courseUtil.courseToCourseDto(dbCourse);

        return ResponseEntity.ok(courseDto);
    }

}
