package selenium4demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;

import java.time.Duration;

public class RelativeLocatorsDemo {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo1.cybersoft.edu.vn/sign-in");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        WebElement txtPassword = driver.findElement(By.id("matKhau"));
        WebElement txtEmail = driver.findElement(
                RelativeLocator.with(By.tagName("input"))
                        .above(txtPassword)
        );
        txtEmail.sendKeys("test@example.com");

        Thread.sleep(3000);

        driver.quit();
    }
}
