package drivers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import config.ConfigManager;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Chrome browser driver manager.
 * Configures ChromeDriver with options for page load strategy and automation
 * detection.
 */
public class ChromeDriverManager extends DriverManager {

    @Override
    public WebDriver createLocalDriver() {
        return new ChromeDriver(getChromeOptions());
    }

    @Override
    public WebDriver createRemoteDriver(String remoteURL) {
        try {
            if(isMobile()) {
                // create mobile driver
                return null;
            } else {
                return new RemoteWebDriver(new URL(remoteURL), getChromeOptions());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private ChromeOptions getChromeOptions() {
        boolean eager = Boolean.parseBoolean(ConfigManager.getProperty("eagerPageLoadStrategy"));
        boolean headless = isHeadless();

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(eager ? PageLoadStrategy.EAGER : PageLoadStrategy.NORMAL);

        if (headless) {
            options.addArguments(
                    "--headless=new", // Use new headless mode (Chrome 109+)
                    "--window-size=1920,1080", // Set viewport size
                    "--disable-gpu",
                    "--no-sandbox",
                    "--disable-dev-shm-usage");
        } else {
            options.addArguments("--start-maximized");
        }

        options.setExperimentalOption("excludeSwitches",
                new String[] { "enable-automation" });
        options.setExperimentalOption("useAutomationExtension", false);

        return options;
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
