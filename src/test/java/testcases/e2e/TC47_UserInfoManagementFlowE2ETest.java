package testcases.e2e;

import api.services.UserService;
import base.BaseTest;
import helpers.actions.AuthActionHelper;
import helpers.providers.AccountInfoTestDataGenerator;
import helpers.verifications.AccountVerificationHelper;
import model.UserAccount;
import model.api.request.RegisterRequestPayload;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import reports.ExtentReportManager;

import static helpers.providers.AccountInfoTestDataGenerator.generateRegisterRequestPayload;
import static helpers.verifications.AccountVerificationHelper.verifyAccountDataMatchesRegistration;
import static helpers.verifications.AuthVerificationHelper.*;

public class TC47_UserInfoManagementFlowE2ETest extends BaseTest {

    private RegisterRequestPayload registerPayload;

    @BeforeMethod
    public void setupMethod() {
        // Create a new user
        registerPayload = generateRegisterRequestPayload();
        UserService userService = new UserService();
        userService.sendRegisterRequest(registerPayload);
    }

    @Test
    public void testUserInfoManagementFlow() {
        SoftAssert softAssert = new SoftAssert();

        // ============================================
        // Step 1: Login and verify user profile link op top bar displays correct name
        // ============================================
        ExtentReportManager.info("Login");
        LoginPage loginPage = new LoginPage(getDriver());
        AuthActionHelper.login(loginPage, registerPayload.getTaiKhoan(), registerPayload.getMatKhau());

        ExtentReportManager.info("Verify user profile button on top bar displays correct name");
        verifyUserButtonDisplaysCorrectName(loginPage, registerPayload.getHoTen(), getDriver(), softAssert);

        // =================================================
        // Step 2: Navigate to account page and verify displayed user info
        // =================================================
        ExtentReportManager.info("Navigate to Account page");
        AccountPage accountPage = new AccountPage(getDriver());
        accountPage.navigateToAccountPage();

        ExtentReportManager.info("Verify displayed user data matches registered account data");
        UserAccount accountDataFromUI = accountPage.getAccountData();
        verifyAccountDataMatchesRegistration(registerPayload, accountDataFromUI, "Account Page", getDriver(), softAssert);

        // ======================================================
        // Step 3: Update user info and verify update success on both UI and backend
        // ======================================================
        ExtentReportManager.info("Update account: name, email, phone");
        String newName = AccountInfoTestDataGenerator.generateNewName(accountPage.getFullName());
        String newEmail = AccountInfoTestDataGenerator.generateNewUniqueEmail();
        String newPhoneNr = AccountInfoTestDataGenerator.generateNewPhoneNumber(accountPage.getPhoneNumber());

        accountPage.changeUserInfoAndSave(newName, newEmail, newPhoneNr);

        ExtentReportManager.info("Verify user info updated successfully on UI");
        AccountVerificationHelper.verifyUserInfoUpdateSuccessOnUI(accountPage, newName, newEmail, newPhoneNr, getDriver(), softAssert);

        ExtentReportManager.info("Verify update persisted in backend");
        // Fetch user info via API using username
        UserService userService = new UserService();
        UserAccount apiUserInfo = userService.getUserDetails(registerPayload.getTaiKhoan());
        // Verify the returned user account info matches updated values
        AccountVerificationHelper.verifyUserInfoUpdateSuccessInBackend(apiUserInfo, newName, newEmail, newPhoneNr, softAssert);

        // ======================================================
        // Step 4: Logout and login again to verify update persists across sessions
        // ======================================================
        ExtentReportManager.info("Logout and login again to verify update persistence");
        AuthActionHelper.logout(accountPage);

        loginPage.navigateToLoginPage();
        AuthActionHelper.login(loginPage, registerPayload.getTaiKhoan(), registerPayload.getMatKhau());

        ExtentReportManager.info("Verify user profile button on top bar displays updated name");
        verifyUserButtonDisplaysCorrectName(loginPage, newName, getDriver(), softAssert);

        softAssert.assertAll();
    }
}