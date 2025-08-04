package com.sprk.one_to_many.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int courseID;

    private String courseName;

    private String courseDescription;

    private String courseDuration;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roll_no")
    @JsonBackReference
    private Student student;
}
