package com.example.carcatalog.dto;


import java.time.LocalDate;
import java.util.Optional;


public class UserFilterOptions {
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> email;
    private Optional<String> username;
    private Optional<String> roleDescription;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public UserFilterOptions() {
        this(null, null, null, null, null, null,
                null, null, null, null, null);
    }

    public UserFilterOptions(String firstName, String lastName, String email, String username, String phoneNumber, LocalDate minDate,
                             LocalDate maxDate, String roleDescription, Integer visitId,
                             String sortBy, String sortOrder) {
        this.username = Optional.ofNullable(username);
        this.firstName = Optional.ofNullable(firstName);
        this.lastName = Optional.ofNullable(lastName);
        this.email = Optional.ofNullable(email);
        this.roleDescription = Optional.ofNullable(roleDescription);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<String> getUsername() {
        return username;
    }

    public void setUsername(Optional<String> username) {
        this.username = username;
    }

    public Optional<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(Optional<String> firstName) {
        this.firstName = firstName;
    }

    public Optional<String> getLastName() {
        return lastName;
    }

    public void setLastName(Optional<String> lastName) {
        this.lastName = lastName;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    public Optional<String> getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(Optional<String> roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(Optional<String> sortBy) {
        this.sortBy = sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Optional<String> sortOrder) {
        this.sortOrder = sortOrder;
    }


}

