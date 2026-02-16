package com.insight.flow.service.mail;


import com.insight.flow.dto.learning.EvaluationMailDTO;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    public MailService(final JavaMailSender mailSender, final TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendNotificationLowGrade(@NonNull final EvaluationMailDTO dto) {

        logger.info("sendNotificationLowGrade() -> {}", dto);

        try {

            final MimeMessage message = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            final Context context = new Context();

            context.setVariable("name", dto.getUser());
            context.setVariable("email", dto.getUserEmail());
            context.setVariable("evaluationDate", dto.getEvaluationDate());

            final String html = templateEngine.process("email/evaluation-email", context);

            helper.setTo(dto.getUserEmail());
            helper.setSubject("Curso com nota baixa em avaliação");
            helper.setText(html, true);
            helper.setFrom("notification.fiap@insightflow.com.br");

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }

    }
}
