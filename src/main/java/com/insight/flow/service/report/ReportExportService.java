package com.insight.flow.service.report;

import com.insight.flow.dto.report.ReportDTO;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ReportExportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportExportService.class);

    private final S3Client s3Client;

    public ReportExportService(final S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public ByteArrayInputStream generateCsv(@NotNull final List<ReportDTO> evaluationsDTO) {

        logger.info("generateCsv() -> {}", evaluationsDTO);

        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Id,Nome,Email,Nota\n");

        evaluationsDTO.forEach((dto) -> stringBuilder.append(dto.getId()).append(","));

        return new ByteArrayInputStream(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
    }

    public void uploadFile(@NotNull final String fileName, @NotNull final InputStream inputStream) {

        logger.info("uploadFile() -> {}, {}", fileName, inputStream);

        final String bucketName = System.getenv("S3_BUCKET_NAME");

        // Validação de segurança simples
        if (bucketName == null || bucketName.isEmpty()) {
            throw new RuntimeException("S3_BUCKET_NAME not found.");
        }

        final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType("text/csv")
                .build();

        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, inputStream.available()));
        } catch (IOException e) {
            throw new RuntimeException("Error send archive for S3", e);
        }
    }
}
