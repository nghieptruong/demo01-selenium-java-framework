package refactor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Proxy;

public class ElementFactory {
    public static WebElement createProxy(WebDriver driver, By locator) {

        return (WebElement) Proxy.newProxyInstance(
                WebElement.class.getClassLoader(),
                new Class[]{WebElement.class},
                new ElementProxyHandler(driver, locator)
        );
    }
}
