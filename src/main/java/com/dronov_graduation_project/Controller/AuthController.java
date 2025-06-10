package com.dronov_graduation_project.Controller;

import com.dronov_graduation_project.Dto.Request.AuthRequest;
import com.dronov_graduation_project.Dto.Request.ResetPasswordRequest;
import com.dronov_graduation_project.Dto.Request.SignUpRequest;
import com.dronov_graduation_project.Dto.Response.User.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dronov_graduation_project.Security.Service.AuthService;
import com.dronov_graduation_project.Service.User.PasswordResetTokenService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class AuthController {

    private final AuthService authService;
    private final PasswordResetTokenService passwordResetTokenService;

    @PostMapping("/")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody SignUpRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/reset/validate")
    public ResponseEntity<?> validateResetToken(@RequestParam String token) {
        try {
            passwordResetTokenService.validateToken(token);
            return ResponseEntity.ok("Token is valid");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }

    @PostMapping("/reset/request")
    public ResponseEntity<?> requestReset(@RequestParam String email) {
        authService.sendPasswordResetLink(email);
        return ResponseEntity.ok("Password reset link sent");
    }

    @PostMapping("/reset/confirm")
    public ResponseEntity<?> confirmReset(@Valid @RequestBody ResetPasswordRequest request) {
        authService.confirmResetPassword(request);
        return ResponseEntity.ok("Password successfully reset");
    }
}
