package com.insight.flow.service.engine;

import com.insight.flow.service.learning.UserCourseEvaluationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class EngineService {

    private static final Logger logger = LoggerFactory.getLogger(EngineService.class);

    private final UserCourseEvaluationService userCourseEvaluationService;

    public EngineService(final UserCourseEvaluationService userCourseEvaluationService) {
        this.userCourseEvaluationService = userCourseEvaluationService;
    }

    @Scheduled(cron = "0 0 2 ? * MON")
    public void generateWeeklyFeedbackReport() {

        logger.info("exportWeeklyFeedbackReport()");

        final ZoneId zone = ZoneId.of("America/Sao_Paulo");

        final LocalDate today = LocalDate.now(zone);

        final LocalDate startOfLastWeek = today
                .minusWeeks(1)
                .with(DayOfWeek.MONDAY);

        final LocalDate endOfLastWeek = today
                .minusWeeks(1)
                .with(DayOfWeek.SUNDAY);

        final Instant startInstant = startOfLastWeek
                .atStartOfDay(zone)
                .toInstant();

        final Instant endInstant = endOfLastWeek
                .atTime(23, 59, 59)
                .atZone(zone)
                .toInstant();

        userCourseEvaluationService.exportWeeklyFeedbackReport(startInstant, endInstant);
    }
}
