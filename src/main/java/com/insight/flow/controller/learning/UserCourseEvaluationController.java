package com.insight.flow.controller.learning;

import com.insight.flow.dto.learning.UserCourseEvaluationDTO;
import com.insight.flow.dto.learning.filter.EvaluationFilterDTO;
import com.insight.flow.entity.learning.UserCourseEvaluation;
import com.insight.flow.mapper.learning.UserCourseEvaluationMapper;
import com.insight.flow.service.learning.UserCourseEvaluationService;
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
@RequestMapping("lea/users-courses-evaluations")
public class UserCourseEvaluationController {

    private static final Logger log = LoggerFactory.getLogger(UserCourseEvaluationController.class);

    private final UserCourseEvaluationService userCourseEvaluationService;

    private final UserCourseEvaluationMapper courseEvaluationMapper;

    public UserCourseEvaluationController(final UserCourseEvaluationService userCourseEvaluationService, final UserCourseEvaluationMapper courseEvaluationMapper) {
        this.userCourseEvaluationService = userCourseEvaluationService;
        this.courseEvaluationMapper = courseEvaluationMapper;
    }

    @PostMapping
    public ResponseEntity<UserCourseEvaluationDTO> created(@RequestBody final UserCourseEvaluation evaluation) {

        log.info("POST -> lea/users-courses-evaluations -> {}", evaluation);

        return ResponseEntity.ok(userCourseEvaluationService.created(evaluation)
                .map(courseEvaluationMapper::fromDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot created evaluation")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserCourseEvaluationDTO> update(@PathVariable final Long id, @RequestBody final UserCourseEvaluation evaluation) {

        log.info("PUT -> lea/users-courses-evaluations/{id} -> {}, {}", id, evaluation);

        return ResponseEntity.ok(userCourseEvaluationService.findById(id)
                .flatMap(bdEvaluation -> userCourseEvaluationService.update(bdEvaluation, evaluation)
                        .map(courseEvaluationMapper::fromDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot update evaluation")));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserCourseEvaluationDTO>> findAll(final Pageable page) {

        log.info("GET -> /lea/users-courses-evaluations -> {}", page);

        return ResponseEntity.ok(userCourseEvaluationService.findAll(page)
                .stream()
                .map(courseEvaluationMapper::fromDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<UserCourseEvaluationDTO> findById(@PathVariable final Long id) {

        log.info("GET -> /lea/users-courses-evaluations/{id} -> {} ", id);

        return ResponseEntity.ok(userCourseEvaluationService.findById(id)
                .map(courseEvaluationMapper::fromDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {

        log.info("DELETE -> lea/users-courses-evaluations/{id} -> {}", id);

        userCourseEvaluationService.findById(id)
                .ifPresent(userCourseEvaluationService::delete);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("evaluate")
    public ResponseEntity<UserCourseEvaluationDTO> evaluate(@RequestBody final UserCourseEvaluation evaluation) {

        log.info("POST -> lea/users-courses-evaluations/evaluate -> {}", evaluation);

        return ResponseEntity.ok(userCourseEvaluationService.evaluate(evaluation)
                .map(courseEvaluationMapper::fromDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot created evaluation")));
    }

    @GetMapping("history")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserCourseEvaluationDTO>> history(@RequestBody final EvaluationFilterDTO filterDTO, final Pageable page) {

        log.info("GET -> /lea/users-courses-evaluations -> {},{}", filterDTO, page);

        return ResponseEntity.ok(userCourseEvaluationService.history(filterDTO, page)
                .stream()
                .map(courseEvaluationMapper::fromDto)
                .collect(Collectors.toList()));
    }
}
