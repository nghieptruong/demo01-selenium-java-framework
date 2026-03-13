package refactor.pages;

import org.openqa.selenium.WebElement;
import refactor.annotations.CustomFindBy;

public class CustomLoginPageWebElement {

    @CustomFindBy(id = "taiKhoan")
    public WebElement txtAccountLogin;

}
