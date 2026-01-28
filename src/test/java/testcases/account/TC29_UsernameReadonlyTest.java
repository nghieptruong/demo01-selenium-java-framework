package testcases.account;

import api.services.UserService;
import base.BaseTest;
import helpers.actions.AuthActionHelper;
import helpers.providers.AccountInfoTestDataGenerator;
import model.api.request.RegisterRequestPayload;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.LoginPage;
import reports.ExtentReportManager;

import static helpers.providers.AccountInfoTestDataGenerator.generateRegisterRequestPayload;

public class TC29_UsernameReadonlyTest extends BaseTest {

    private RegisterRequestPayload requestPayload;

    @BeforeMethod(alwaysRun = true)
    public void setupMethod() {
        // Generate payload and send POST request to create new user via API
        requestPayload = generateRegisterRequestPayload();

        // TEMP: Remove non-alphabetic characters in full name due to known backend bug to make sure account form is displayed for this test
        requestPayload.setHoTen(requestPayload.getHoTen().replaceAll("[^a-zA-Z]", ""));

        UserService userService = new UserService();
        userService.sendRegisterRequest(requestPayload);
    }

    @Test
    public void testUsernameIsReadonly() {
        // Login
        ExtentReportManager.info("Login with newly created user credentials");
        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, requestPayload.getTaiKhoan(), requestPayload.getMatKhau());

        // Attempt to change username field value
        ExtentReportManager.info("Navigate to account page and attempt update with invalid full name");
        AccountPage accountPage = new AccountPage(getDriver());
        accountPage.navigateToAccountPage();
        accountPage.waitForAccountFormDisplay();

        String newUsername = AccountInfoTestDataGenerator.generateUniqueUsername();
        ExtentReportManager.info("Attempt to change username field input to: " + newUsername);
        accountPage.attemptToChangeUsername(newUsername);

        // Verify username input value is not changed
        String usernameFieldValue = accountPage.getUsername();
        Assert.assertEquals(requestPayload.getTaiKhoan(), usernameFieldValue,
                "Username field does not display original username. Expected = " + requestPayload.getTaiKhoan() + ", Actual = " + usernameFieldValue);

        ExtentReportManager.pass("Username field is readonly");
    }
}