package model.ui;

import lombok.Data;

/**
 * UI model for login form input data.
 * Contains credentials entered by user in the login form.
 */
@Data
public class LoginDataUI {
    private final String taiKhoan;
    private final String matKhau;
}