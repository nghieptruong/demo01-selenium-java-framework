package pages.pagefactorydemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPageDemo {

    protected WebDriver driver;

    @FindBy (id = "taiKhoan")
    private WebElement txtAccountLogin;

    public RegisterPageDemo(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void enterAccount(String account) {
        txtAccountLogin.sendKeys(account);
    }
}
