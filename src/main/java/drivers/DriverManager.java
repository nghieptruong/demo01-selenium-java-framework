package drivers;

import org.openqa.selenium.WebDriver;

/**
 * Abstract base class for browser-specific driver managers.
 * Implements Factory Pattern to create WebDriver instances based on browser type.
 * 
 * Subclasses must implement createDriver() to return a configured WebDriver instance.
 * Driver lifecycle (storage, quit) is managed by BaseTest using ThreadLocal.
 */
public abstract class DriverManager {

    /**
     * Creates and returns a new WebDriver instance configured for the specific browser.
     * 
     * @return WebDriver instance ready for use
     */
    public abstract WebDriver createLocalDriver();
    public abstract WebDriver createRemoteDriver(String remoteURL);

    // Check if running on mobile devices or not
    public boolean isMobile() {
        return false;
    }
}
