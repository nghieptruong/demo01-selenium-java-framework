package refactor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import refactor.annotations.CustomFindBy;

import java.lang.reflect.Field;

public class CustomPageFactory {
    public static void initElements(WebDriver driver, Object page) {
        Field[] fields = page.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CustomFindBy.class)) {
                CustomFindBy annotation = field.getAnnotation(CustomFindBy.class);
                By locator = getSelector(annotation);
                WebElement proxy = ElementFactory.createProxy(driver, locator);
                field.setAccessible(true);
                try {
                    field.set(page, proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static By getSelector(CustomFindBy annotation) {
        if (!annotation.id().isEmpty()) {
            return By.id(annotation.id());
        } else if (!annotation.css().isEmpty()) {
            return By.cssSelector(annotation.css());
        } else if (!annotation.xpath().isEmpty()) {
            return By.xpath(annotation.xpath());
        } if (!annotation.name().isEmpty()) {
            return By.name(annotation.name());
        } else if (!annotation.className().isEmpty()) {
            return By.className(annotation.className());
        } else if (!annotation.tagName().isEmpty()) {
            return By.tagName(annotation.tagName());
        } else if (!annotation.linkText().isEmpty()) {
            return By.linkText(annotation.linkText());
        } else if (!annotation.partialLinkText().isEmpty()) {
            return By.partialLinkText(annotation.partialLinkText());
        }
        return null;
    }
}
