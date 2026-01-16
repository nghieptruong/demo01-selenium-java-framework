package helpers;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import pages.AccountPage;
import pages.CommonPage;
import pages.LoginPage;
import pages.RegisterPage;
import reports.ExtentReportManager;

import static helpers.AssertionHelper.*;

/**
 * Helper class for common authentication-related verifications.
 * Reduces code duplication across component, integration, and E2E tests.
 */
public class AuthVerificationHelper {

    /**
     * Verify registration success message is displayed and has correct text.
     * Commonly used in both component tests (RegisterTest) and E2E tests (RegisterLoginE2ETest).
     *
     * @param registerPage The RegisterPage instance
     * @param driver WebDriver instance for screenshot capture
     * @param softAssert The SoftAssert instance for accumulating assertions
     */
    public static void verifyRegisterSuccessMsg(RegisterPage registerPage, WebDriver driver, SoftAssert softAssert) {
        boolean isAlertDisplayed = registerPage.isRegisterSuccessAlertDisplayed();
        verifySoftTrue(isAlertDisplayed, "Register success message is displayed", driver, softAssert);

        if (isAlertDisplayed) {
            String expectedMessage = Messages.getRegisterSuccessMessage();
            String actualMessage = registerPage.getRegisterSuccessMsgText();
            verifySoftEquals(actualMessage, expectedMessage,
                    "Register success message text",
                    driver, softAssert);
        }
    }

    /**
     * Verify login success - message displayed, correct text, and user is logged in.
     * Uses AssertionHelper to automatically capture screenshots on each failed soft assertion.
     * Commonly used in login component tests and E2E tests.
     *
     * @param loginPage The LoginPage instance
     * @param driver WebDriver instance (needed for screenshot capture)
     * @param softAssert The SoftAssert instance for accumulating assertions
     */
    public static void verifyLoginSuccess(LoginPage loginPage, WebDriver driver, SoftAssert softAssert) {
        ExtentReportManager.info("Verify login success");

        // Verify login success message displayed
        boolean isSuccessAlertDisplayed = loginPage.isLoginSuccessMessageDisplayed();
        verifySoftTrue(isSuccessAlertDisplayed,
                "Login success alert is displayed", driver, softAssert);

        // Verify success message text (only if alert is displayed)
        if (isSuccessAlertDisplayed) {
            String expectedMsg = Messages.getLoginSuccessMessage();
            String actualMsg = loginPage.getLoginSuccessMsgText();
            AssertionHelper.verifySoftEquals(actualMsg, expectedMsg,
                    "Login success message text", driver, softAssert);
        }

        // Verify user is logged in
        boolean isLoggedIn = loginPage.topBarNavigation.isUserProfileVisible();
        verifySoftTrue(isLoggedIn,
                "User profile is visible (logged in)", driver, softAssert);
    }

    /**
     * Verify that the user button in the top bar displays the correct user's name.
     * Works with ANY page since all pages extend CommonPage which has topBarNavigation.
     * @param page
     * @param expectedName
     * @param driver
     * @param softAssert
     */
    public static void verifyUserButtonDisplaysCorrectName(CommonPage page, String expectedName, WebDriver driver, SoftAssert softAssert) {
        ExtentReportManager.info("Verify user button displays correct name");

        String actualName = page.topBarNavigation.getUserProfileName();
        verifySoftEquals(actualName, expectedName,
                "User profile name displayed in top bar", driver, softAssert);
    }

    /**
     * Verify logout success - alert visible, login link appears, user profile disappears.
     * Works with ANY page since all pages extend CommonPage which has topBarNavigation.
     *
     * @param page Any page object (HomePage, AccountPage, LoginPage, etc.) - all have topBarNavigation
     * @param driver WebDriver instance for screenshot capture
     * @param softAssert The SoftAssert instance for accumulating assertions
     */
    public static void verifyLogoutSuccess(CommonPage page, WebDriver driver, SoftAssert softAssert) {
        ExtentReportManager.info("Verify logout success");

        verifySoftTrue(page.topBarNavigation.isLogoutSuccessAlertVisible(),
                "Logout success alert is visible", driver, softAssert);

        verifySoftTrue(page.topBarNavigation.isLoginLinkVisible(),
                "Login link should be visible after logout", driver, softAssert);

        verifySoftFalse(page.topBarNavigation.isUserProfileVisible(),
                "User profile should not be visible after logout", driver, softAssert);
    }

    /**
     * Verify account update success message is displayed with correct text.
     *
     * @param accountPage The AccountPage instance
     * @param driver WebDriver instance for screenshot capture
     * @param softAssert The SoftAssert instance for accumulating assertions
     */
    public static void verifyAccountUpdateSuccessMsg(AccountPage accountPage, WebDriver driver, SoftAssert softAssert) {
        String expectedMsg = Messages.getAccountUpdateSuccessMessage();
        String actualMsg = accountPage.getUpdateAlertText();

        verifySoftEquals(actualMsg, expectedMsg, "Account update success message text", driver, softAssert);
        accountPage.waitForUpdateAlertToDisappear();
    }

    /**
     * Verify that the account full name is updated correctly.
     *
     * @param accountPage The AccountPage instance
     * @param expectedName The expected full name after update
     * @param driver WebDriver instance for screenshot capture
     * @param softAssert The SoftAssert instance for accumulating assertions
     */
    public static void verifyAccountFullNameUpdated(AccountPage accountPage, String expectedName, WebDriver driver, SoftAssert softAssert) {
        accountPage.refreshPage();
        String actualName = accountPage.getFullName();
        verifySoftEquals(actualName, expectedName, "Account full name after update", driver, softAssert);
    }

}

