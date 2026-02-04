package com.insight.flow.repository.learning;

import com.insight.flow.entity.learning.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findAllByActiveTrue(Pageable pageable);

    @Query(nativeQuery = true,
            value = "select courses.* " +//
                    "from lea_courses courses " +//
                    "where courses.active = true " +//
                    "  and courses.id = :courseId ")
    Optional<Course> findOneActiveTrueById(@Param("courseId") Long courseId);
}
