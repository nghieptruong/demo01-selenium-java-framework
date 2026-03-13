package refactor.pages;

import org.openqa.selenium.support.FindBy;
import refactor.annotations.CustomFindBy;

public class CustomLoginPage {

    @CustomFindBy(id = "taiKhoan")
    private String txtAccountLogin;

    @FindBy(id = "matKhau")
    private String txtPasswordLogin;

    // ---- Form button ----
    @FindBy (css = "button[type='submit']")
    private String btnLogin;

    // ---- Field validation message ----
    @FindBy (id = "matKhau-helper-text")
    private String lblInvalidPasswordMsg;

    // ---- Form alerts ----
    @FindBy (css = "div[role='alert']")
    private String alertLoginError;
}
