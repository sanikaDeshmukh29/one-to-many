package com.sprk.one_to_one.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class StudentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentDetailId;

    private String hobby;

    private String education;
}
