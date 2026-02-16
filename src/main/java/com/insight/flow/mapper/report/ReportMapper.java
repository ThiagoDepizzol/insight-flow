package com.insight.flow.mapper.report;

import com.insight.flow.dto.report.ReportDTO;
import com.insight.flow.entity.learning.UserCourseEvaluation;
import com.insight.flow.mapper.parameter.ParameterMapper;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    private static final Logger log = LoggerFactory.getLogger(ParameterMapper.class);


    public ReportDTO fromDto(@NotNull final UserCourseEvaluation evaluation) {

        log.info("fromDto() -> {}", evaluation);

        final ReportDTO newDto = new ReportDTO();

        newDto.setId(evaluation.getId());

        return newDto;
    }
}
