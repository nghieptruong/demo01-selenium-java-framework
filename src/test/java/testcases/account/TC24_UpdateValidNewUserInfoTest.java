package testcases.account;

import api.services.UserService;
import base.BaseTest;
import helpers.actions.AuthActionHelper;
import helpers.providers.AccountInfoTestDataGenerator;
import helpers.verifications.AccountVerificationHelper;
import model.UserAccount;
import model.api.request.RegisterRequestPayload;
import model.ui.LoginDataUI;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AccountPage;
import pages.LoginPage;
import reports.ExtentReportManager;

import static helpers.providers.AccountInfoTestDataGenerator.generateRegisterRequestPayload;

public class TC24_UpdateValidNewUserInfoTest extends BaseTest {

    private RegisterRequestPayload requestPayload;
    private LoginDataUI loginCredentials;

    @BeforeMethod(alwaysRun = true)
    public void setupMethod() {
        // Generate payload and send POST request to create new user via API
        requestPayload = generateRegisterRequestPayload();

        // TEMP: Remove non-alphabetic characters in full name due to known backend bug to make sure account form is displayed for this test
        requestPayload.setHoTen(requestPayload.getHoTen().replaceAll("[^a-zA-Z]", ""));

        UserService userService = new UserService();
        userService.sendRegisterRequest(requestPayload);

        loginCredentials = new LoginDataUI(requestPayload.getTaiKhoan(), requestPayload.getMatKhau());
    }

    @Test(description = "Test Succesful Update User Info: Name, Email, Phone Number")
    public void testSuccessfulUpdateUserInfo() {

        SoftAssert softAssert = new SoftAssert();

        // Login
        ExtentReportManager.info("Login with newly created user credentials");
        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, loginCredentials);

        // Navigate to account page and update user info: Name, Email, Phone Number
        ExtentReportManager.info("Navigate to account page and update user info: name, email, phone number");
        AccountPage accountPage = new AccountPage(getDriver());
        accountPage.navigateToAccountPage();
        accountPage.waitForAccountFormDisplay();

        String newName = AccountInfoTestDataGenerator.generateNewName(requestPayload.getHoTen());
        String newPhoneNr = AccountInfoTestDataGenerator.generateNewPhoneNumber(requestPayload.getSoDt());
        String newEmail = AccountInfoTestDataGenerator.generateNewUniqueEmail();

        accountPage.changeUserInfoAndSave(newName, newEmail, newPhoneNr);

        ExtentReportManager.info("Verify user info updated successfully on UI");
        // Verify update success message and new values are displayed
        AccountVerificationHelper.verifyUserInfoUpdateSuccessOnUI(accountPage, newName, newEmail, newPhoneNr, getDriver(), softAssert);

        ExtentReportManager.info("Verify updated user info persist in backend");
        // Fetch user info via API using username
        UserService userService = new UserService();
        UserAccount apiUserInfo = userService.getUserDetails(requestPayload.getTaiKhoan());
        // Verify the returned user account info matches updated values
        AccountVerificationHelper.verifyUserInfoUpdateSuccessInBackend(apiUserInfo, newName, newEmail, newPhoneNr, softAssert);

        softAssert.assertAll();
    }
}
