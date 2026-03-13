package refactor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ElementProxyHandler implements InvocationHandler {

    private WebDriver driver;
    private By locator;

    public ElementProxyHandler(WebDriver driver, By locator) {
        this.driver = driver;
        this.locator = locator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        WebElement element = driver.findElement(locator);

        System.out.println("Element located: " + locator);

        return method.invoke(element, args);
    }
}
