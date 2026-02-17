package com.insight.flow.dto.generic;

import java.io.Serializable;
import java.time.Instant;

public class ReportRequestDTO implements Serializable {

    private Instant initialDate;

    private Instant finalDate;

    public ReportRequestDTO() {
    }

    public Instant getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Instant initialDate) {
        this.initialDate = initialDate;
    }

    public Instant getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Instant finalDate) {
        this.finalDate = finalDate;
    }
}
