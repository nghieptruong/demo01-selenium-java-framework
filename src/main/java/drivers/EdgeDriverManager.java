package drivers;

import config.ConfigManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Microsoft Edge browser driver manager.
 * Configures EdgeDriver with options for page load strategy.
 */
public class EdgeDriverManager extends DriverManager {

    @Override
    public WebDriver createLocalDriver() {
        return new EdgeDriver(getEdgeOptions());
    }

    @Override
    public WebDriver createRemoteDriver(String remoteURL) {
        try {
            return new RemoteWebDriver(new URL(remoteURL), getEdgeOptions());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private EdgeOptions getEdgeOptions() {
        boolean eager = Boolean.parseBoolean(ConfigManager.getProperty("eagerPageLoadStrategy"));

        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(eager ? PageLoadStrategy.EAGER : PageLoadStrategy.NORMAL);

        return options;
    }
}
