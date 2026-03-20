package selenium4demo;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;


public class FullPageScreenshotDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://demo1.cybersoft.edu.vn/sign-in");

        File src = ((FirefoxDriver) driver).getFullPageScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("fullpage.png"));

        Thread.sleep(3000);

        driver.quit();

    }
}
