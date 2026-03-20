package selenium4demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v145.network.Network;

import java.time.Duration;
import java.util.Optional;

public class CDPCaptureNetworkResponse {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("145");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();

        //enable network
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.responseReceived(), response -> {
            System.out.println("Response: "
                    + response.getResponse().getUrl()
                    + " | Status: "
                    + response.getResponse().getStatus());
        });

        driver.get("https://demo1.cybersoft.edu.vn");

        driver.quit();
    }
}
