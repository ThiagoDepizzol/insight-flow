package com.insight.flow.dto.parameter;

import com.insight.flow.enumerated.Status;

import java.io.Serializable;
import java.math.BigDecimal;

public class ParameterDTO implements Serializable {

    private Long id;

    private Boolean active;

    private String name;

    private Status status;

    private BigDecimal rating;

    public ParameterDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
}
