package com.insight.flow.service.learning;

import com.insight.flow.dto.learning.filter.EvaluationFilterDTO;
import com.insight.flow.entity.learning.UserCourseEvaluation;
import com.insight.flow.repository.learning.UserCourseEvaluationRepository;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserCourseEvaluationService {

    private static final Logger logger = LoggerFactory.getLogger(UserCourseEvaluationService.class);

    public final UserCourseEvaluationRepository userCourseEvaluationRepository;

    public UserCourseEvaluationService(final UserCourseEvaluationRepository userCourseEvaluationRepository) {
        this.userCourseEvaluationRepository = userCourseEvaluationRepository;
    }

    public Optional<UserCourseEvaluation> created(@NotNull final UserCourseEvaluation evaluation) {

        logger.info("created() -> {}", evaluation);

        return Optional.of(userCourseEvaluationRepository.save(evaluation));

    }

    public Optional<UserCourseEvaluation> update(@NotNull final UserCourseEvaluation oldEvaluation, @NotNull final UserCourseEvaluation newEvaluation) {

        logger.info("update() -> {}, {}", oldEvaluation, newEvaluation);

        oldEvaluation.setActive(newEvaluation.getActive());
        oldEvaluation.setUser(newEvaluation.getUser());
        oldEvaluation.setCourse(newEvaluation.getCourse());
        oldEvaluation.setRating(newEvaluation.getRating());
        oldEvaluation.setComment(newEvaluation.getComment());


        return Optional.of(userCourseEvaluationRepository.save(oldEvaluation));

    }

    @Transactional(readOnly = true)
    public Page<UserCourseEvaluation> findAll(@NotNull final Pageable pageable) {

        logger.info("findAll() -> {}", pageable);

        return userCourseEvaluationRepository.findAllByActiveTrue(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<UserCourseEvaluation> findById(@NotNull final Long id) {

        logger.info("findById() -> {}", id);

        return userCourseEvaluationRepository.findOneActiveTrueById(id);
    }


    public void delete(@NotNull final UserCourseEvaluation evaluation) {

        logger.info("delete() -> {}", evaluation);

        evaluation.setActive(false);

        userCourseEvaluationRepository.save(evaluation);

    }

    public Optional<UserCourseEvaluation> evaluate(@NotNull final UserCourseEvaluation evaluation) {

        logger.info("evaluate() -> {}", evaluation);

        return Optional.of(userCourseEvaluationRepository.save(evaluation));

    }

    @Transactional(readOnly = true)
    public Page<UserCourseEvaluation> history(@NotNull final EvaluationFilterDTO filterDTO, @NotNull final Pageable pageable) {

        logger.info("history() -> {}, {}", filterDTO, pageable);

        final Long courseId = filterDTO.getCourseId();
        final Long userId = filterDTO.getUserId();
        final BigDecimal rating = filterDTO.getRating();

        return userCourseEvaluationRepository.history(courseId, userId, rating, pageable);
    }

}
