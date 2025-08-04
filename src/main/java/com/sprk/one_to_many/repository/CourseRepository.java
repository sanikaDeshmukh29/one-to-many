package com.sprk.one_to_many.repository;

import com.sprk.one_to_many.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses, Integer> {
}
