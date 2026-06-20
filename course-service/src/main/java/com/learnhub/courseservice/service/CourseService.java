package com.learnhub.courseservice.service;

import com.learnhub.courseservice.dtos.CreateCourseRequest;
import com.learnhub.courseservice.model.Course;
import com.learnhub.courseservice.repository.CourseRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepo courseRepo;

    public Course createCourse(@Valid CreateCourseRequest createCourseRequest, UUID teacherId) {

        Course course = Course.builder()
                .title(createCourseRequest.getTitle())
                .description(createCourseRequest.getDescription())
                .price(createCourseRequest.getPrice())
                .teacherId(teacherId)
                .build();

        return courseRepo.save(course);

    }

    public List<Course> getAllCourse() {

        return courseRepo.findAll();
    }

    public Course getcourseById(UUID id) {
        return courseRepo.findById(id).orElseThrow(()-> new RuntimeException("Course not found"));
    }
}
