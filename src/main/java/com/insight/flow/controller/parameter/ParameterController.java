package com.insight.flow.controller.parameter;

import com.insight.flow.dto.parameter.ParameterDTO;
import com.insight.flow.entity.parameter.Parameter;
import com.insight.flow.mapper.parameter.ParameterMapper;
import com.insight.flow.service.parameter.ParameterService;
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
@RequestMapping("par/parameters")
public class ParameterController {

    private static final Logger log = LoggerFactory.getLogger(ParameterController.class);

    private final ParameterService parameterService;

    private final ParameterMapper parameterMapper;

    public ParameterController(final ParameterService parameterService, final ParameterMapper parameterMapper) {
        this.parameterService = parameterService;
        this.parameterMapper = parameterMapper;
    }

    @PostMapping
    public ResponseEntity<ParameterDTO> created(@RequestBody final Parameter parameter) {

        log.info("POST -> par/parameters -> {}", parameter);

        return ResponseEntity.ok(parameterService.created(parameter)
                .map(parameterMapper::fromDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot created parameter")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParameterDTO> update(@PathVariable final Long id, @RequestBody final Parameter parameter) {

        log.info("PUT -> par/parameters/{id} -> {}, {}", id, parameter);

        return ResponseEntity.ok(parameterService.findById(id)
                .flatMap(bdParameter -> parameterService.update(bdParameter, parameter)
                        .map(parameterMapper::fromDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot update parameter")));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ParameterDTO>> findAll(final Pageable page) {

        log.info("GET -> /par/parameters -> {}", page);

        return ResponseEntity.ok(parameterService.findAll(page)
                .stream()
                .map(parameterMapper::fromDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<ParameterDTO> findById(@PathVariable final Long id) {

        log.info("GET -> /par/parameters/{id} -> {} ", id);

        return ResponseEntity.ok(parameterService.findById(id)
                .map(parameterMapper::fromDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parameter not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable final Long id) {

        log.info("DELETE -> par/parameters/{id} -> {}", id);

        parameterService.findById(id)
                .ifPresent(parameterService::delete);

        return ResponseEntity.noContent()
                .build();
    }
}
