package com.sprk.one_to_many.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString(exclude = "courses")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rollNo;

    private String firstName;

    private String lastName;

    private String phone;

    //Aggregation

    @OneToMany(mappedBy = "student", cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH},fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Courses> courses = new ArrayList<>();


}
