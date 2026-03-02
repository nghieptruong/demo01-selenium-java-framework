package drivers;

import config.ConfigManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Safari browser driver manager.
 * Configures SafariDriver with options for page load strategy.
 */
public class SafariDriverManager extends DriverManager {

    @Override
    public WebDriver createLocalDriver() {
        return new SafariDriver(getSafariOptions());
    }

    @Override
    public WebDriver createRemoteDriver(String remoteURL) {
        try {
            return new RemoteWebDriver(new URL(remoteURL), getSafariOptions());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private SafariOptions getSafariOptions() {
        boolean eager = Boolean.parseBoolean(ConfigManager.getProperty("eagerPageLoadStrategy"));

        SafariOptions options = new SafariOptions();
        options.setPageLoadStrategy(eager ? PageLoadStrategy.EAGER : PageLoadStrategy.NORMAL);

        return options;
    }
}
