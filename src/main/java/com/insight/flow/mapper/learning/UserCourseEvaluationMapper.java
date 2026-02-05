package com.insight.flow.mapper.learning;

import com.insight.flow.dto.learning.UserCourseEvaluationDTO;
import com.insight.flow.entity.learning.Course;
import com.insight.flow.entity.learning.UserCourseEvaluation;
import com.insight.flow.entity.user.User;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserCourseEvaluationMapper {

    private static final Logger log = LoggerFactory.getLogger(UserCourseEvaluationMapper.class);


    public UserCourseEvaluationDTO fromDto(@NotNull final UserCourseEvaluation evaluation) {

        log.info("fromDto() -> {}", evaluation);

        final String userName = Optional.ofNullable(evaluation.getUser())
                .map(User::getUsername)
                .orElse("");

        final String courseName = Optional.ofNullable(evaluation.getCourse())
                .map(Course::getName)
                .orElse("");

        final UserCourseEvaluationDTO newDto = new UserCourseEvaluationDTO();

        newDto.setId(evaluation.getId());
        newDto.setActive(evaluation.getActive());
        newDto.setUser(userName);
        newDto.setCourse(courseName);
        newDto.setRating(evaluation.getRating());
        newDto.setComment(evaluation.getComment());

        return newDto;
    }
}
