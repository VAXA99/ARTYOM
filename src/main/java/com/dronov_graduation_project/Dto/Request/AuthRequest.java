package com.dronov_graduation_project.Dto.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String username; // optional
    private String email;    // optional
    private String password;
}
