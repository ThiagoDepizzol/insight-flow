package com.insight.flow.entity.learning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.insight.flow.entity.base.BaseEntity;
import com.insight.flow.entity.user.User;
import com.insight.flow.enumerated.CourseUserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "lea_courses_users")
public class CourseUser extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(name = "lea_course_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Course course;

    @NotNull
    @JoinColumn(name = "usr_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "modules", allowSetters = true)
    private User user;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseUserType type;

    public CourseUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CourseUserType getType() {
        return type;
    }

    public void setType(CourseUserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CourseUser course = (CourseUser) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
