package com.example.cms.controller;

import com.example.cms.controller.exceptions.CourseNotFoundException;
import com.example.cms.controller.exceptions.DepartmentNotFoundException;
import com.example.cms.model.entity.CourseMark;
import com.example.cms.model.entity.Department;
import com.example.cms.model.repository.CourseMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CourseMarkController {
    @Autowired
    private final CourseMarkRepository repository;

    public CourseMarkController(CourseMarkRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/marks")
    List<CourseMark> retrieveAllMarks() {
        return repository.findAll();
    }

    @PostMapping("/marks/increaseFive/{code}")
    List<CourseMark> increaseFive(@PathVariable("code") String courseCode) {
        repository.increaseFive(courseCode);
        return repository.findAll();
        //

    }

}
