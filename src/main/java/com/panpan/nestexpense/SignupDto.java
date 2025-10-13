package com.panpan.nestexpense;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SignupDto {
    private String email;
    private String password;
    private String confirmPassword;
}
