package com.insight.flow.service.learning;

import com.insight.flow.entity.learning.Course;
import com.insight.flow.entity.learning.CourseUser;
import com.insight.flow.repository.learning.CourseUserRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseUserService {

    private static final Logger logger = LoggerFactory.getLogger(CourseUserService.class);

    public final CourseUserRepository courseUserRepository;

    public CourseUserService(final CourseUserRepository courseUserRepository) {
        this.courseUserRepository = courseUserRepository;
    }

    public void saveAllAndFlush(@NotNull final Course course, @NotNull final List<CourseUser> users) {

        logger.info("saveAllAndFlush() -> {}, {}", course, users);

        Optional.of(users)
                .ifPresent(bdUsers -> bdUsers.forEach(user -> {
                    user.setCourse(course);
                    courseUserRepository.save(user);
                }));


    }

}
