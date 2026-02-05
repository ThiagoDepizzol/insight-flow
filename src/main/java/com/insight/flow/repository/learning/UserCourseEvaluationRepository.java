package com.insight.flow.repository.learning;

import com.insight.flow.entity.learning.UserCourseEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserCourseEvaluationRepository extends JpaRepository<UserCourseEvaluation, Long> {

    Page<UserCourseEvaluation> findAllByActiveTrue(Pageable pageable);

    @Query(nativeQuery = true,
            value = "select evaluations.* " +//
                    "from lea_users_courses_evaluations evaluations " +//
                    "where evaluations.active = true " +//
                    "  and evaluations.id = :evaluationId ")
    Optional<UserCourseEvaluation> findOneActiveTrueById(@Param("evaluationId") Long evaluationId);
}
