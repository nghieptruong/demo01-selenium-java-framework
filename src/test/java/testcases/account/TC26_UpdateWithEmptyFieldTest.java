package testcases.account;

import api.services.UserService;
import base.BaseTest;
import helpers.actions.AuthActionHelper;
import helpers.providers.AccountInfoTestDataGenerator;
import helpers.providers.MessagesProvider;
import helpers.verifications.AccountVerificationHelper;
import model.api.request.RegisterRequestPayload;
import model.enums.AccountDataField;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AccountPage;
import pages.LoginPage;
import reports.ExtentReportManager;

import static helpers.providers.AccountInfoTestDataGenerator.generateRegisterRequestPayload;

public class TC26_UpdateWithEmptyFieldTest extends BaseTest {

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

    @Test(description = "Test Blocked Update due to an empty field: Phone Number")
    public void testUpdateBlockedWithEmptyPhoneNr() {

        SoftAssert softAssert = new SoftAssert();

        // Login
        ExtentReportManager.info("Login with newly created user credentials");
        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, requestPayload.getTaiKhoan(), requestPayload.getMatKhau());

        // Attempt to update user info: valid email, valid phone number but empty phone number
        ExtentReportManager.info("Navigate to account page and attempt update with empty phone number");
        AccountPage accountPage = new AccountPage(getDriver());
        accountPage.navigateToAccountPage();
        accountPage.waitForAccountFormDisplay();

        String newName = AccountInfoTestDataGenerator.generateNewName(requestPayload.getHoTen());
        String newEmail = AccountInfoTestDataGenerator.generateNewUniqueEmail();

        accountPage.changeUserInfoAndSave(newName, newEmail, "");

        // Verify correct validation message and form displays original values
        ExtentReportManager.info("Verify failed updated of user info");
        String expectedMsg = MessagesProvider.getAccountPhoneNrRequiredError();

        AccountVerificationHelper.verifyUpdateFailsDueToFieldValidation(
                accountPage, AccountDataField.PHONE_NUMBER,
                expectedMsg, requestPayload,
                getDriver(), softAssert);

        softAssert.assertAll();
    }
}