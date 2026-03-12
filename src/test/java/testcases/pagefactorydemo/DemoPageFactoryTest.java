package testcases.pagefactorydemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.pagefactorydemo.DynamicPageDemo;
import pages.pagefactorydemo.RegisterPageDemo;

public class DemoPageFactoryTest {

    @Test
    public void testPageFactory() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demo1.cybersoft.edu.vn/sign-up");
        driver.manage().window().maximize();

        RegisterPageDemo registerPageDemo = new RegisterPageDemo(driver);
        registerPageDemo.enterAccount("Test1");

        driver.navigate().refresh();
        registerPageDemo.enterAccount("Test2");

        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    public void testNotPageFactory() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://demo1.cybersoft.edu.vn/sign-up");
        driver.manage().window().maximize();

        WebElement element = driver.findElement(By.id("taiKhoan"));
        element.sendKeys("Test1");
        driver.navigate().refresh(); // reload page --> change state DOM

        element.sendKeys("Test2"); // stale element exception

        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    public void testAjaxWait() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        driver.manage().window().maximize();

        DynamicPageDemo dynamicPageDemo = new DynamicPageDemo(driver);
        dynamicPageDemo.clickStart();
        String recorded = dynamicPageDemo.getResultText();
        Assert.assertEquals(recorded, "Hello World!", "Incorrect Result!");

        Thread.sleep(3000);
        driver.quit();
    }
}
