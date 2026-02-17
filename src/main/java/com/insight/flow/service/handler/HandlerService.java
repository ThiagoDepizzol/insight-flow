package com.insight.flow.service.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.flow.dto.generic.ReportRequestDTO;
import com.insight.flow.service.learning.UserCourseEvaluationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerService implements RequestHandler<SQSEvent, Void> {

    private static final Logger logger = LoggerFactory.getLogger(HandlerService.class);

    private final UserCourseEvaluationService userCourseEvaluationService;

    private final ObjectMapper objectMapper;

    public HandlerService(final UserCourseEvaluationService userCourseEvaluationService, final ObjectMapper objectMapper) {
        this.userCourseEvaluationService = userCourseEvaluationService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Void handleRequest(final SQSEvent event, final Context context) {

        logger.info("handleRequest() -> {}, {}", event, context);

        // TODO: Arrumar o string com a fila certa
        final String weeklyReportLambda = "";

        event.getRecords()
                .forEach(record -> {

                    if (weeklyReportLambda.equals(record.getEventSourceArn())) {

                        try {
                            final ReportRequestDTO dto = objectMapper.readValue(record.getBody(), ReportRequestDTO.class);

                            userCourseEvaluationService.exportWeeklyFeedbackReport(dto.getInitialDate(), dto.getFinalDate());

                        } catch (Exception e) {

                            context.getLogger().log("Erro ao processar mensagem: " + e.getMessage());

                            throw new RuntimeException(e);
                        }
                    }
                });

        return null;

    }

}
