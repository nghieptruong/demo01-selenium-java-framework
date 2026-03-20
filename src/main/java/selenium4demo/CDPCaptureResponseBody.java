package selenium4demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v145.network.Network;
import org.openqa.selenium.devtools.v145.network.model.RequestId;
import org.openqa.selenium.devtools.v145.network.model.Response;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CDPCaptureResponseBody {
    public static void main(String[] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("145");
//        options.addArguments("--auto-open-devtools-for-tabs"); // Mở devtools để debug
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();

        //enable network
        devTools.send(Network.enable(
                Optional.of(1000000),  // max total buffer size
                Optional.of(1000000),  // max resource buffer size
                Optional.of(1000000),  // max post data size
                Optional.empty(),
                Optional.empty()
        ));

        devTools.addListener(Network.responseReceived(), response -> {
            String url = response.getResponse().getUrl();
            if (url.contains("/api/QuanLyPhim/LayDanhSachPhim")
                    && response.getResponse().getStatus() == 200) {

                System.out.println("Found matching URL: " + url);

                try {
                    Network.GetResponseBodyResponse body =
                            devTools.send(Network.getResponseBody(response.getRequestId()));

                    System.out.println("URL: " + url);
                    System.out.println("BODY: " + body.getBody());
                    System.out.println("=====================================");

                } catch (Exception e) {
                    System.out.println("Could not get body immediately: " + e.getMessage());
                }
            }
        });
        driver.get("https://demo1.cybersoft.edu.vn");

        Thread.sleep(30000);

        driver.quit();
    }
}
