package com.insight.flow.repository.learning;

import com.insight.flow.entity.learning.UserCourseEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserCourseEvaluationRepository extends JpaRepository<UserCourseEvaluation, Long> {

    Page<UserCourseEvaluation> findAllByActiveTrue(Pageable pageable);

    @Query(nativeQuery = true,
            value = "select evaluations.* " +//
                    "from lea_users_courses_evaluations evaluations " +//
                    "where evaluations.active = true " +//
                    "  and evaluations.id = :evaluationId ")
    Optional<UserCourseEvaluation> findOneActiveTrueById(@Param("evaluationId") Long evaluationId);

    @Query(nativeQuery = true,
            value = "select evaluations.* " +//
                    "from lea_users_courses_evaluations evaluations " +//
                    "         join usr_users users " +//
                    "              on evaluations.usr_user_id = users.id " +//
                    "                  and users.active = true " +//
                    "         join lea_courses courses " +//
                    "              on evaluations.lea_course_id = courses.id " +//
                    "                  and courses.active = true " +//
                    "where evaluations.active = true " +//
                    "  and (:courseId is null " +//
                    "    or courses.id = :courseId) " +//
                    "  and (:userId is null " +//
                    "    or users.id = :userId) " +//
                    "  and (:rating is null " +//
                    "    or evaluations.id >= :rating) ")
    Page<UserCourseEvaluation> history(@Param("courseId") Long courseId, @Param("userId") Long userId, @Param("rating") BigDecimal rating, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select evaluations.* " +//
                    "from lea_users_courses_evaluations evaluations " +//
                    "where evaluations.active = true " +//
                    "  and (:startDate is null " +//
                    "    or evaluations.evaluation_date >= :startDate) " +//
                    "  and (:endDate is null " +//
                    "    or evaluations.evaluation_date <= :endDate)")
    List<UserCourseEvaluation> getAllByInitialDateAndFinalDate(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
