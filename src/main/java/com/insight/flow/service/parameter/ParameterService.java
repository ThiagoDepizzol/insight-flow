package com.insight.flow.service.parameter;

import com.insight.flow.entity.parameter.Parameter;
import com.insight.flow.repository.parameter.ParameterRepository;
import com.insight.flow.utils.exceptions.SystemParameterRegisteredException;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ParameterService {

    private static final Logger logger = LoggerFactory.getLogger(ParameterService.class);

    public final ParameterRepository parameterRepository;

    public ParameterService(final ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    public Optional<Parameter> created(@NotNull final Parameter parameter) {

        logger.info("created() -> {}", parameter);

        if (parameterRepository.hasParameterActive()) {

            logger.info("Parâmetro do sistema já cadastrado");

            throw new SystemParameterRegisteredException("Parâmetro do sistema já cadastrado");
        }

        return Optional.of(parameterRepository.save(parameter));

    }

    public Optional<Parameter> update(@NotNull final Parameter oldParameter, @NotNull final Parameter newParameter) {

        logger.info("update() -> {}, {}", oldParameter, newParameter);

        oldParameter.setActive(newParameter.getActive());
        oldParameter.setName(newParameter.getName());
        oldParameter.setStatus(newParameter.getStatus());
        oldParameter.setRating(newParameter.getRating());

        return Optional.of(parameterRepository.save(oldParameter));

    }

    @Transactional(readOnly = true)
    public Page<Parameter> findAll(@NotNull final Pageable pageable) {

        logger.info("findAll() -> {}", pageable);

        return parameterRepository.findAllByActiveTrue(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Parameter> findById(@NotNull final Long id) {

        logger.info("findById() -> {}", id);

        return parameterRepository.findOneActiveTrueById(id);
    }


    public void delete(@NotNull final Parameter parameter) {

        logger.info("delete() -> {}", parameter);

        parameter.setActive(false);

        parameterRepository.save(parameter);

    }

    @Transactional(readOnly = true)
    public Optional<Parameter> findOneStatusActive() {

        logger.info("findOneStatusActive()");

        return parameterRepository.findOneStatusActive();
    }
}
