package com.insight.flow.service.learning;

import com.insight.flow.dto.learning.EvaluationMailDTO;
import com.insight.flow.dto.learning.filter.EvaluationFilterDTO;
import com.insight.flow.dto.report.ReportDTO;
import com.insight.flow.entity.learning.UserCourseEvaluation;
import com.insight.flow.mapper.report.ReportMapper;
import com.insight.flow.repository.learning.UserCourseEvaluationRepository;
import com.insight.flow.service.mail.MailService;
import com.insight.flow.service.parameter.ParameterService;
import com.insight.flow.service.report.ReportExportService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserCourseEvaluationService {

    private static final Logger logger = LoggerFactory.getLogger(UserCourseEvaluationService.class);

    public final UserCourseEvaluationRepository userCourseEvaluationRepository;

    public final ReportExportService reportExportService;

    public final ParameterService parameterService;

    public final ReportMapper reportMapper;

    public final MailService mailService;


    public UserCourseEvaluationService(final UserCourseEvaluationRepository userCourseEvaluationRepository,
                                       final ReportExportService reportExportService,
                                       final ParameterService parameterService,
                                       final ReportMapper reportMapper,
                                       final MailService mailService) {
        this.userCourseEvaluationRepository = userCourseEvaluationRepository;
        this.reportExportService = reportExportService;
        this.parameterService = parameterService;
        this.reportMapper = reportMapper;
        this.mailService = mailService;
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

        return Optional.of(userCourseEvaluationRepository.save(evaluation))
                .map(savedEvaluated -> {
                    sendNotificationForNotesBelowMedia(savedEvaluated);

                    return savedEvaluated;
                });

    }

    @Transactional(readOnly = true)
    public Page<UserCourseEvaluation> history(@NotNull final EvaluationFilterDTO filterDTO, @NotNull final Pageable pageable) {

        logger.info("history() -> {}, {}", filterDTO, pageable);

        final Long courseId = filterDTO.getCourseId();
        final Long userId = filterDTO.getUserId();
        final BigDecimal rating = filterDTO.getRating();

        return userCourseEvaluationRepository.history(courseId, userId, rating, pageable);
    }

    @Transactional
    public void exportWeeklyFeedbackReport(@NotNull final Instant initialDate, @NotNull final Instant finalDate) {

        logger.info("exportWeeklyFeedbackReport() -> {}, {}", initialDate, finalDate);

        final List<ReportDTO> evaluationsDTO = userCourseEvaluationRepository.getAllByInitialDateAndFinalDate(initialDate, finalDate)
                .stream()
                .map(reportMapper::fromDto)
                .collect(Collectors.toList());

        final ByteArrayInputStream csv = reportExportService.generateCsv(evaluationsDTO);

        final String archiveName = "report-" + LocalDate.now() + ".csv";

        reportExportService.uploadFile(archiveName, csv);
    }

    @Transactional
    public void sendNotificationForNotesBelowMedia(@NotNull final UserCourseEvaluation evaluation) {

        logger.info("sendNotificationForNotesBelowMedia() -> {}", evaluation);


        parameterService.findOneStatusActive()
                .ifPresent(parameter -> {

                    if (evaluation.getRating().compareTo(parameter.getRating()) < 0) {

                        final EvaluationMailDTO dto = new EvaluationMailDTO();

                        mailService.sendNotificationLowGrade(dto);

                    }
                });
    }

}
