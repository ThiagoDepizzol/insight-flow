package com.insight.flow.entity.learning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.insight.flow.entity.base.BaseEntity;
import com.insight.flow.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "lea_users_courses_evaluations")
public class UserCourseEvaluation extends BaseEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(name = "usr_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private User user;

    @NotNull
    @JoinColumn(name = "lea_course_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Course course;

    @NotNull
    private BigDecimal rating;

    @Column(length = 1000)
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserCourseEvaluation that = (UserCourseEvaluation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
