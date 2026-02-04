package com.insight.flow.controller.learning;

import com.insight.flow.dto.learning.CourseDTO;
import com.insight.flow.entity.learning.Course;
import com.insight.flow.mapper.learning.CourseMapper;
import com.insight.flow.service.learning.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("lea/courses")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    private final CourseMapper courseMapper;

    public CourseController(final CourseService courseService, final CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @PostMapping
    public ResponseEntity<CourseDTO> created(@RequestBody final Course course) {

        log.info("POST -> lea/courses -> {}", course);

        return ResponseEntity.ok(courseService.created(course)
                .map(courseMapper::fromDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot created course")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable final Long id, @RequestBody final Course course) {

        log.info("PUT -> lea/courses/{id} -> {}, {}", id, course);

        return ResponseEntity.ok(courseService.findById(id)
                .flatMap(bdCourse -> courseService.update(bdCourse, course)
                        .map(courseMapper::fromDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot update course")));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<CourseDTO>> findAll(final Pageable page) {

        log.info("GET -> /lea/courses -> {}", page);

        return ResponseEntity.ok(courseService.findAll(page)
                .stream()
                .map(courseMapper::fromDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<CourseDTO> findById(@PathVariable final Long id) {

        log.info("GET -> /lea/courses/{id} -> {} ", id);

        return ResponseEntity.ok(courseService.findById(id)
                .map(courseMapper::fromDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {

        log.info("DELETE -> lea/courses/{id} -> {}", id);

        courseService.findById(id)
                .ifPresent(courseService::delete);

        return ResponseEntity.noContent()
                .build();
    }
}
