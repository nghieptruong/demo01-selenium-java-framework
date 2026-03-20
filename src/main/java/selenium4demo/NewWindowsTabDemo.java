package selenium4demo;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

public class NewWindowsTabDemo {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        //Selenium 3
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.open('https://google.com','_blank');");
//        js.executeScript("window.open('https://facebook.com','_blank','width=800,height=600');");

//        //Selenium 4
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://google.com");

        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get("https://facebook.com");

        Thread.sleep(5000);

        driver.quit();
    }
}
