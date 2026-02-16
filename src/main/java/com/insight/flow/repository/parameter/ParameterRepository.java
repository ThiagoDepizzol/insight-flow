package com.insight.flow.repository.parameter;

import com.insight.flow.entity.parameter.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    Page<Parameter> findAllByActiveTrue(Pageable pageable);

    @Query(nativeQuery = true,
            value = "select parameters.* " +//
                    "from par_parameters parameters " +//
                    "where parameters.active = true " +//
                    "  and parameters.id = :parameterId ")
    Optional<Parameter> findOneActiveTrueById(@Param("parameterId") Long parameterId);

    @Query(nativeQuery = true,
            value = "select parameters.* " +//
                    "from par_parameters parameters " +//
                    "where parameters.active = true " +//
                    "  and parameters.status = 'ACTIVE' ")
    Optional<Parameter> findOneStatusActive();

}
