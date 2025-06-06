package com.progiton.trainee.simple.devicemanagement.model.to;

import java.time.LocalDateTime;

import com.progiton.trainee.simple.devicemanagement.model.Department;

public class DepartmentTo implements Department {

	private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public DepartmentTo() {
    }

    public DepartmentTo(String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters & Setters
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
