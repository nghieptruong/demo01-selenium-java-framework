package drivers;

import config.ConfigManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Firefox browser driver manager.
 * Configures FirefoxDriver with options for page load strategy.
 */
public class FirefoxDriverManager extends DriverManager {

    @Override
    public WebDriver createLocalDriver() {
        return new FirefoxDriver(getFirefoxOptions());
    }

    @Override
    public WebDriver createRemoteDriver(String remoteURL) {
        try {
            return new RemoteWebDriver(new URL(remoteURL), getFirefoxOptions());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private FirefoxOptions getFirefoxOptions() {
        boolean eager = Boolean.parseBoolean(ConfigManager.getProperty("eagerPageLoadStrategy"));

        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(eager ? PageLoadStrategy.EAGER : PageLoadStrategy.NORMAL);

        return options;
    }
}
