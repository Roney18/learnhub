package com.learnhub.courseservice.controller;

import com.learnhub.courseservice.dtos.CreateCourseRequest;
import com.learnhub.courseservice.model.Course;
import com.learnhub.courseservice.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody @Valid CreateCourseRequest createCourseRequest) {

        UUID teacherId = UUID.randomUUID();
        return ResponseEntity.ok(courseService.createCourse(createCourseRequest, teacherId));

    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {

        return ResponseEntity.ok(courseService.getAllCourse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable UUID id){

        return ResponseEntity.ok(courseService.getcourseById(id));
    }
}
