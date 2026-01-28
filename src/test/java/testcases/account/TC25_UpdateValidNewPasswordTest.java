package testcases.account;

import api.services.UserService;
import base.BaseTest;
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

public class TC25_UpdateValidNewPasswordTest extends BaseTest {

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

    @Test(description = "Test Succesful Update User Info: Name, Email, Phone Number")
    public void testSuccessfulUpdatePassword() {

        SoftAssert softAssert = new SoftAssert();

        String username = requestPayload.getTaiKhoan();
        String originalPassword = requestPayload.getMatKhau();

        // Login
        ExtentReportManager.info("Login with newly created user credentials");
        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, username, originalPassword);

        // Navigate to Account page and update password
        ExtentReportManager.info("Navigate to account page and update password");
        AccountPage accountPage = new AccountPage(getDriver());
        accountPage.navigateToAccountPage();
        accountPage.waitForAccountFormDisplay();

        String newPassword = AccountInfoTestDataGenerator.generateNewPassword(originalPassword);
        accountPage.changePasswordAndSave(newPassword);

        // Verify success message, failed login with old password, successful login with new password
        AccountVerificationHelper.verifyPasswordUpdateSuccess(accountPage, username, originalPassword, newPassword, getDriver(), softAssert);

        softAssert.assertAll();
    }
}