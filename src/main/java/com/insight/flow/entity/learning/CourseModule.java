package com.insight.flow.entity.learning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.insight.flow.entity.base.BaseEntity;
import com.insight.flow.enumerated.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "lea_courses_modules")
public class CourseModule extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JoinColumn(name = "lea_course_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "modules", allowSetters = true)
    private Course course;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private String description;

    public CourseModule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CourseModule course = (CourseModule) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
