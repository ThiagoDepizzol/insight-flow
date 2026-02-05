package com.insight.flow.service.learning;

import com.insight.flow.entity.learning.Course;
import com.insight.flow.entity.learning.CourseModule;
import com.insight.flow.repository.learning.CourseModuleRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseModuleService {

    private static final Logger logger = LoggerFactory.getLogger(CourseModuleService.class);

    public final CourseModuleRepository courseModuleRepository;

    public CourseModuleService(final CourseModuleRepository courseModuleRepository) {
        this.courseModuleRepository = courseModuleRepository;
    }

    public void saveAllAndFlush(@NotNull final Course course, @NotNull final List<CourseModule> modules) {

        logger.info("saveAllAndFlush() -> {}, {}", course, modules);

        Optional.of(modules)
                .ifPresent(bdModules -> bdModules.forEach(module -> {
                    module.setCourse(course);
                    courseModuleRepository.save(module);
                }));


    }

}
