package com.insight.flow.entity.learning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.insight.flow.entity.base.BaseEntity;
import com.insight.flow.enumerated.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lea_courses")
public class Course extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private String description;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "courses", allowSetters = true)
    private Set<CourseModule> modules = new HashSet<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "courses", allowSetters = true)
    @Where(clause = "type = 'TEACHER'")
    private Set<CourseUser> teachers = new HashSet<>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "courses", allowSetters = true)
    @Where(clause = "type = 'STUDENT'")
    private Set<CourseUser> students = new HashSet<>();

    public Course() {
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

    public Set<CourseModule> getModules() {
        return modules;
    }

    public void setModules(Set<CourseModule> modules) {
        this.modules = modules;
    }

    public Set<CourseUser> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<CourseUser> teachers) {
        this.teachers = teachers;
    }

    public Set<CourseUser> getStudents() {
        return students;
    }

    public void setStudents(Set<CourseUser> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
