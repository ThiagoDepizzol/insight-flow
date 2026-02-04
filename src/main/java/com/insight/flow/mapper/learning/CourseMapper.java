package com.insight.flow.mapper.learning;

import com.insight.flow.dto.learning.CourseDTO;
import com.insight.flow.entity.learning.Course;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    private static final Logger log = LoggerFactory.getLogger(CourseMapper.class);


    public CourseDTO fromDto(@NotNull final Course course) {

        log.info("fromDto() -> {}", course);

        final CourseDTO newDto = new CourseDTO();

        newDto.setId(course.getId());
        newDto.setActive(course.getActive());
        newDto.setName(course.getName());
        newDto.setStatus(course.getStatus());
        newDto.setDescription(course.getDescription());

        return newDto;
    }
}
