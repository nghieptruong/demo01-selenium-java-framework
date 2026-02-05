package drivers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import config.ConfigManager;

/**
 * Chrome browser driver manager.
 * Configures ChromeDriver with options for page load strategy and automation detection.
 */
public class ChromeDriverManager extends DriverManager {

    protected final Logger LOG = LogManager.getLogger(getClass());

    @Override
    public WebDriver createDriver() {

        boolean eager = Boolean.parseBoolean(ConfigManager.getProperty("eagerPageLoadStrategy"));
        boolean headless = isHeadless();

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(eager ? PageLoadStrategy.EAGER : PageLoadStrategy.NORMAL);

//        "--window-size=1920,1080",
        if (headless) {
            LOG.info("Set Headless options");
            options.addArguments(
                    "--headless=new",
                    "--screen-info={0,0 1920x1080}",
                    "--disable-gpu",
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
            );
        } else {
            options.addArguments("--start-maximized");
//            options.addArguments("start-maximized");
        }

        options.setExperimentalOption("excludeSwitches",
                new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        WebDriver driver = new ChromeDriver(options);
        LOG.info("Browser size: " + driver.manage().window().getSize());
        return driver;
    }


    // ---- Private Helper Methods ----
    private boolean isHeadless() {
        String systemValue = System.getProperty("headless");

        if (systemValue != null) {
            return Boolean.parseBoolean(systemValue);
        }

        String configValue = ConfigManager.getProperty("headless");
        return configValue != null && Boolean.parseBoolean(configValue);
    }
}
