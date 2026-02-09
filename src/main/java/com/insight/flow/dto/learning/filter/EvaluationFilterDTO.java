package com.insight.flow.dto.learning.filter;

import java.io.Serializable;
import java.math.BigDecimal;

public class EvaluationFilterDTO implements Serializable {

    private Long courseId;

    private Long userId;

    private BigDecimal rating;

    public EvaluationFilterDTO() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
    
}
