package com.insight.flow.service.learning;

import com.insight.flow.entity.learning.Course;
import com.insight.flow.entity.learning.CourseModule;
import com.insight.flow.entity.learning.CourseUser;
import com.insight.flow.repository.learning.CourseRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    public final CourseRepository courseRepository;

    public final CourseUserService courseUserService;

    public final CourseModuleService courseModuleService;

    public CourseService(final CourseRepository courseRepository, final CourseUserService courseUserService, final CourseModuleService courseModuleService) {
        this.courseRepository = courseRepository;
        this.courseUserService = courseUserService;
        this.courseModuleService = courseModuleService;
    }

    public Optional<Course> created(@NotNull final Course course) {

        logger.info("created() -> {}", course);

        final List<CourseModule> modules = Optional.ofNullable(course.getModules())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
        course.setModules(null);

        final List<CourseUser> teachers = Optional.ofNullable(course.getTeachers())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
        course.setTeachers(null);

        final List<CourseUser> students = Optional.ofNullable(course.getStudents())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
        course.setStudents(null);

        return Optional.of(courseRepository.save(course))
                .map(savedCourse -> {
                    courseUserService.saveAllAndFlush(savedCourse, teachers);

                    return savedCourse;
                })
                .map(savedCourse -> {
                    courseUserService.saveAllAndFlush(savedCourse, students);

                    return savedCourse;
                })
                .map(savedCourse -> {
                    courseModuleService.saveAllAndFlush(savedCourse, modules);

                    return savedCourse;
                });

    }

    public Optional<Course> update(@NotNull final Course oldCourse, @NotNull final Course newCourse) {

        logger.info("update() -> {}, {}", oldCourse, newCourse);

        oldCourse.setName(newCourse.getName());
        oldCourse.setStatus(newCourse.getStatus());
        oldCourse.setDescription(newCourse.getDescription());
        oldCourse.setActive(newCourse.getActive());

        final List<CourseModule> modules = Optional.ofNullable(oldCourse.getModules())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
        oldCourse.setModules(null);

        final List<CourseUser> teachers = Optional.ofNullable(oldCourse.getTeachers())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
        oldCourse.setTeachers(null);

        final List<CourseUser> students = Optional.ofNullable(oldCourse.getStudents())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
        oldCourse.setStudents(null);

        return Optional.of(courseRepository.save(oldCourse))
                .map(savedCourse -> {
                    courseUserService.saveAllAndFlush(savedCourse, teachers);

                    return savedCourse;
                })
                .map(savedCourse -> {
                    courseUserService.saveAllAndFlush(savedCourse, students);

                    return savedCourse;
                })
                .map(savedCourse -> {
                    courseModuleService.saveAllAndFlush(savedCourse, modules);

                    return savedCourse;
                });

    }

    @Transactional(readOnly = true)
    public Page<Course> findAll(@NotNull final Pageable pageable) {

        logger.info("findAll() -> {}", pageable);

        return courseRepository.findAllByActiveTrue(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Course> findById(@NotNull final Long id) {

        logger.info("findById() -> {}", id);

        return courseRepository.findOneActiveTrueById(id);
    }


    public void delete(@NotNull final Course course) {

        logger.info("delete() -> {}", course);

        course.setActive(false);

        courseRepository.save(course);

    }
}
