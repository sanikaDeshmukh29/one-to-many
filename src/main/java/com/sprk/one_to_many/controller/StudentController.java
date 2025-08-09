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
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/save-student")
    public Student saveStudent(@RequestBody Student student) {

        List<Courses> courses = new ArrayList<>();

        // now copy the course from student inti list obj
        for(Courses course:student.getCourses()){

            courses.add(course);
        }
        student.getCourses().clear();

        // save only student
        Student savedStudent = studentRepository.save(student);

        // now save courses with respect to that student
        if(courses != null){

            for(Courses course : courses){
                course.setStudent(savedStudent);
                Courses savedCourses = courseRepository.save(course);

            }

            savedStudent.setCourses(courses);
        }

        return savedStudent;
    }

    @GetMapping("/find-all-students")
    public List<Student> findAllStudents() {

        List<Student> allStudents = studentRepository.findAll();

        return allStudents;
    }

    @GetMapping("/find-by-roll-no")
    public Student findByRollNo(@RequestParam int rollNo) {

        return studentRepository.findById(rollNo).orElse(null);

    }


    @DeleteMapping("/delete-student")
    public String deleteStudent(@RequestParam int rollNo){

        studentRepository.deleteById(rollNo);

        return "Student deleted successfully";
    }

    // Delete whole student -> Delete all courses associate to that student
    @DeleteMapping("/student/delete/{rollNo}")
    public ResponseEntity<?> deleteWholeStudent(@PathVariable int rollNo){

        Student dbStudent = studentRepository.findById(rollNo).orElse(null);


        if(dbStudent != null){
            List<Courses> courses = dbStudent.getCourses();
            for(Courses course : courses){
                course.setStudent(null);
            }
            studentRepository.deleteById(rollNo);
            return ResponseEntity.ok().body("Student with roll no: "+rollNo+" deleted successfully");
        }
        return ResponseEntity.status(404).body("Student with roll no: "+rollNo+" not found!!");

    }

}
