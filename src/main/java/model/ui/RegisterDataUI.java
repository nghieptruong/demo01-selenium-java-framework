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
public class RegisterDataUI {
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String email;

    public RegisterDataUI setUsername(String username) {
        this.username = username;
        return this;
    }

    public RegisterDataUI setPassword(String password) {
        this.password = password;
        return this;
    }

    public RegisterDataUI setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public RegisterDataUI setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public RegisterDataUI setEmail(String email) {
        this.email = email;
        return this;
    }
}