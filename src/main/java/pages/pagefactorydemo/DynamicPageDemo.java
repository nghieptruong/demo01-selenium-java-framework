package pages.pagefactorydemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class DynamicPageDemo {

    protected WebDriver driver;

    @FindBy(xpath = "//button[text()='Start']")
    private WebElement btnStart;

    @FindBy(xpath = "//div[@id='finish']/h4")
    private WebElement lblMessageResult;

//    public DynamicPageDemo(WebDriver driver) {
//        PageFactory.initElements(driver, this);
//    }

    public DynamicPageDemo(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    public void clickStart() {
        btnStart.click();
    }

    public String getResultText() {
        return lblMessageResult.getText();
    }
}
