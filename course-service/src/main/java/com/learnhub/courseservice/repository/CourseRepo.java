package com.learnhub.courseservice.repository;

import com.learnhub.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepo extends JpaRepository<Course, UUID> {
}
