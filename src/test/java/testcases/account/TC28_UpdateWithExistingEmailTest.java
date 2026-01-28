package testcases.account;

import api.services.UserService;
import base.BaseTest;
import config.ConfigManager;
import helpers.actions.AuthActionHelper;
import helpers.providers.AccountInfoTestDataGenerator;
import helpers.verifications.AccountVerificationHelper;
import model.api.request.RegisterRequestPayload;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AccountPage;
import pages.LoginPage;
import reports.ExtentReportManager;

import static helpers.providers.AccountInfoTestDataGenerator.generateRegisterRequestPayload;

public class TC28_UpdateWithExistingEmailTest extends BaseTest {

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

    @Test(description = "Test Blocked Update due to an invalid field: Full Name")
    public void testUpdateBlockedWithExistingEmail() {
        SoftAssert softAssert = new SoftAssert();

        // Login
        ExtentReportManager.info("Login with newly created user credentials");
        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, requestPayload.getTaiKhoan(), requestPayload.getMatKhau());

        // Attempt to update user info with valid phone nr and email but invalid name (containing numbers)
        ExtentReportManager.info("Navigate to account page and attempt update with invalid full name");
        AccountPage accountPage = new AccountPage(getDriver());
        accountPage.navigateToAccountPage();
        accountPage.waitForAccountFormDisplay();

        String existingEmail = ConfigManager.getDefaultUserEmail();
        String newName = AccountInfoTestDataGenerator.generateNewName(requestPayload.getHoTen());
        String newPhoneNr = AccountInfoTestDataGenerator.generateNewPhoneNumber(requestPayload.getSoDt());

        accountPage.changeUserInfoAndSave(newName, existingEmail, newPhoneNr);

        // Verify correct validation message and form displays original values
        ExtentReportManager.info("Verify failed updated of user info due to existing email");
        AccountVerificationHelper.verifyUpdateFailsDueToExistingEmailError(accountPage, requestPayload, getDriver(), softAssert);

        softAssert.assertAll();
    }
}