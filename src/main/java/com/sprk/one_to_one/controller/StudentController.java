package com.sprk.one_to_one.controller;

import com.sprk.one_to_one.entity.Student;
import com.sprk.one_to_one.entity.StudentDetail;
import com.sprk.one_to_one.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/save-student")
    public Student saveStudent(@RequestBody Student student) {

        Student savedStudent = studentRepository.save(student);

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

    @PutMapping("/edit-student")
    public Student editStudent(@RequestParam int rollNo, @RequestBody Student student) {

        Student exsistingStudent = studentRepository.findById(rollNo).orElse(null);

        student.setRollNo(rollNo);


        StudentDetail studentDetail = student.getStudentDetail();
        studentDetail.setStudentDetailId(exsistingStudent.getStudentDetail().getStudentDetailId());

        student.setStudentDetail(studentDetail);

        return studentRepository.save(student);


    }

    @DeleteMapping("/delete-student")
    public String deleteStudent(@RequestParam int rollNo){

        studentRepository.deleteById(rollNo);

        return "Student deleted successfully";
    }
}
