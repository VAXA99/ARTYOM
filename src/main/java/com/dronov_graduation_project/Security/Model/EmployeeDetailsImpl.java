package com.dronov_graduation_project.Security.Model;

import com.dronov_graduation_project.Model.User.Employee;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EmployeeDetailsImpl implements UserDetails {

    @Getter
    private final Long id;

    @Getter
    private final String email;

    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    // Конструктор
    public EmployeeDetailsImpl(
            Long id,
            String username,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Создаёт объект UserDetailsImpl из сущности Employee
    public static EmployeeDetailsImpl build(Employee employee) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole().getName());
        return new EmployeeDetailsImpl(
                employee.getId(),
                employee.getUsername(),
                employee.getEmail(),
                employee.getPassword(),
                Collections.singletonList(authority)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
