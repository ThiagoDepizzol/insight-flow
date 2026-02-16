package com.insight.flow.dto.learning;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class EvaluationMailDTO implements Serializable {


    private Long id;

    private Boolean active;

    private String user;

    private String userEmail;

    private String course;

    private BigDecimal rating;

    private String comment;

    private Instant evaluationDate;

    public EvaluationMailDTO() {
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Instant evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
}
