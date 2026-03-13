package refactor.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import refactor.CustomPageFactory;
import refactor.pages.CustomLoginPageWebElement;

import java.time.Duration;

public class TestDemo {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demo1.cybersoft.edu.vn/sign-in");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        CustomLoginPageWebElement loginPage = new CustomLoginPageWebElement();
        CustomPageFactory.initElements(driver, loginPage);
        loginPage.txtAccountLogin.sendKeys("Test");

        Thread.sleep(3000);
        driver.quit();
    }
}
