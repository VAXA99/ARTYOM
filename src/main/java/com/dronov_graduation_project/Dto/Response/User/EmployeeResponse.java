package com.dronov_graduation_project.Dto.Response.User;

public class EmployeeResponse {
    private Long id;
    private String surname;
    private String name;
    private String middleName;
    private String email;
    private String username;
    private String roleName;

    public EmployeeResponse(Long id, String surname, String name, String middleName, String email, String username, String roleName) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.email = email;
        this.username = username;
        this.roleName = roleName;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}
