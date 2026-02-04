package com.insight.flow.mapper.parameter;

import com.insight.flow.dto.parameter.ParameterDTO;
import com.insight.flow.entity.parameter.Parameter;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ParameterMapper {

    private static final Logger log = LoggerFactory.getLogger(ParameterMapper.class);


    public ParameterDTO fromDto(@NotNull final Parameter parameter) {

        log.info("fromDto() -> {}", parameter);

        final ParameterDTO newDto = new ParameterDTO();

        newDto.setId(parameter.getId());
        newDto.setActive(parameter.getActive());
        newDto.setName(parameter.getName());
        newDto.setStatus(parameter.getStatus());
        newDto.setRating(parameter.getRating());

        return newDto;
    }
}
