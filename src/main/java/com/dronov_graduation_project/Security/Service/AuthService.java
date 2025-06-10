package com.dronov_graduation_project.Security.Service;

import com.dronov_graduation_project.Dto.Request.AuthRequest;
import com.dronov_graduation_project.Dto.Request.ResetPasswordRequest;
import com.dronov_graduation_project.Dto.Request.SignUpRequest;
import com.dronov_graduation_project.Dto.Response.User.AuthResponse;
import com.dronov_graduation_project.Security.Jwt.JwtService;
import com.dronov_graduation_project.Model.User.Employee;
import com.dronov_graduation_project.Model.User.Role;
import com.dronov_graduation_project.Security.Model.EmployeeDetailsImpl;
import com.dronov_graduation_project.Service.Email.EmailService;
import com.dronov_graduation_project.Service.User.EmployeeService;
import com.dronov_graduation_project.Service.User.PasswordResetTokenService;
import com.dronov_graduation_project.Service.User.RoleService;
import com.dronov_graduation_project.Util.ResetUrlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Сервис аутентификации и регистрации сотрудников (Employee)
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final PasswordResetTokenService passwordResetTokenService;
    private final ResetUrlBuilder resetUrlBuilder;
    private final EmailService emailService;

    /**
     * Аутентификация сотрудника по логину/почте и паролю.
     * Возвращает JWT при успешной авторизации.
     */
    public AuthResponse authenticate(AuthRequest request) {
        try {
            String loginIdentifier = request.getUsername() != null ? request.getUsername() : request.getEmail();
            if (loginIdentifier == null || loginIdentifier.isBlank()) {
                throw new RuntimeException("Требуется указать имя пользователя или email");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginIdentifier,
                            request.getPassword()
                    )
            );

            Employee employee = Optional.ofNullable(request.getUsername())
                    .flatMap(employeeService::findByUsername)
                    .or(() -> employeeService.findByEmail(request.getEmail()))
                    .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));

            UserDetails userDetails = EmployeeDetailsImpl.build(employee);

            String jwt = jwtService.generateToken(
                    userDetails.getUsername(),
                    employee.getId(),
                    employee.getRole().getName()
            );

            return new AuthResponse(jwt);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Неверные учетные данные");
        }
    }

    /**
     * Регистрация нового сотрудника с присвоением роли по умолчанию.
     * Возвращает JWT для нового пользователя.
     */
    public AuthResponse register(SignUpRequest request) {
        if (employeeService.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Имя пользователя уже занято");
        }

        if (employeeService.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email уже зарегистрирован");
        }

        Role defaultRole = roleService.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Роль по умолчанию не найдена"));

        Employee newEmployee = new Employee();
        newEmployee.setName(request.getName());
        newEmployee.setSurname(request.getSurname());
        newEmployee.setMiddleName(request.getMiddleName());
        newEmployee.setUsername(request.getUsername());
        newEmployee.setEmail(request.getEmail());
        newEmployee.setPassword(passwordEncoder.encode(request.getPassword()));
        newEmployee.setRole(defaultRole);

        Employee savedEmployee = employeeService.create(newEmployee);

        String jwt = jwtService.generateToken(
                savedEmployee.getUsername(),
                savedEmployee.getId(),
                savedEmployee.getRole().getName()
        );

        return new AuthResponse(jwt);
    }

    /**
     * Подтверждение сброса пароля по токену.
     * Проверяет, что новый пароль не совпадает с текущим.
     * Сохраняет новый пароль и удаляет использованный токен.
     */
    public void confirmResetPassword(ResetPasswordRequest request) {
        var tokenEntity = passwordResetTokenService.validateToken(request.getToken());
        Employee employee = tokenEntity.getEmployee();

        if (passwordEncoder.matches(request.getNewPassword(), employee.getPassword())) {
            throw new RuntimeException("Новый пароль должен отличаться от текущего");
        }

        employee.setPassword(passwordEncoder.encode(request.getNewPassword()));
        employeeService.create(employee);

        passwordResetTokenService.removeToken(request.getToken());
    }

    /**
     * Отправка ссылки для сброса пароля на email сотрудника.
     * Генерирует токен, формирует ссылку и отправляет письмо.
     */
    @Transactional
    public void sendPasswordResetLink(String email) {
        Employee employee = employeeService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email не найден"));

        var token = passwordResetTokenService.createToken(employee);
        String resetUrl = resetUrlBuilder.buildUrl(token.getToken());

        String subject = "Восстановление пароля";
        String body = String.format("""
                Здравствуйте, %s!

                Мы получили запрос на восстановление пароля для вашей учётной записи.

                Перейдите по ссылке ниже, чтобы задать новый пароль:
                %s

                Если вы не запрашивали восстановление, просто проигнорируйте это письмо.
                """, employee.getUsername(), resetUrl);

        emailService.send(employee.getEmail(), subject, body);
    }
}
