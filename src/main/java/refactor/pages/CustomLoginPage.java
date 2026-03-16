package refactor.pages;

import org.openqa.selenium.support.FindBy;
import refactor.annotations.CustomFindBy;

public class CustomLoginPage {

    @CustomFindBy(id = "taiKhoan")
    private String txtAccountLogin;

    @CustomFindBy(id = "matKhau")
    private String txtPasswordLogin;

    // ---- Form button ----
    @CustomFindBy (css = "button[type='submit']")
    private String btnLogin;

    // ---- Field validation message ----
    @CustomFindBy (id = "matKhau-helper-text")
    private String lblInvalidPasswordMsg;

    // ---- Form alerts ----
    @CustomFindBy (css = "div[role='alert']")
    private String alertLoginError;
}
