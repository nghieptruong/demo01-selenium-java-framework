package model.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for registration form data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterInputs {
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String email;
}