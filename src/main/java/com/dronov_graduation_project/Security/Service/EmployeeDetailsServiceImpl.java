package com.dronov_graduation_project.Security.Service;

import com.dronov_graduation_project.Model.User.Employee;
import com.dronov_graduation_project.Repository.User.EmployeeRepository;
import com.dronov_graduation_project.Security.Model.EmployeeDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(usernameOrEmail)
                .or(() -> employeeRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() ->
                        new UsernameNotFoundException("Сотрудник с username или email не найден: " + usernameOrEmail)
                );
        return EmployeeDetailsImpl.build(employee);
    }
}
