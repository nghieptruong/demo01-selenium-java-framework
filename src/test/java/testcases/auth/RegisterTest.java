package testcases.auth;

import base.BaseTest;
import helpers.utils.MessagesUI;
import helpers.utils.SoftAssertionHelper;
import helpers.providers.TestUserProvider;
import model.ui.RegisterInputs;
import model.TestUser;
import model.TestUserType;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.RegisterPage;
import reports.ExtentReportManager;

import static helpers.providers.AuthTestDataGenerator.*;
import static helpers.verifications.AuthVerificationHelper.verifyRegisterSuccessMsg;

public class RegisterTest extends BaseTest {

    private RegisterPage registerPage;
    private RegisterInputs formInputs;

    @BeforeMethod(alwaysRun = true)
    public void setUpMethod() {
        registerPage = new RegisterPage(getDriver());

        // Generate valid form inputs as base for use in tests
        formInputs = generateValidRegisterFormInputs();

        ExtentReportManager.info("Navigate to Register Page");
        registerPage.navigateToRegisterPage();
    }

    @Test(groups = {"component", "auth", "register", "smoke", "critical"})
    public void testValidRegister() {
        SoftAssert softAssert = new SoftAssert();

        ExtentReportManager.info("Submit Register form with valid data");
        // Fill and submit form with valid inputs
        registerPage.fillRegisterFormThenSubmit(formInputs);

        ExtentReportManager.info("Verify Register Success message");
        verifyRegisterSuccessMsg(registerPage, getDriver(), softAssert);

        softAssert.assertAll();
    }

    @Test(groups = {"component", "auth", "register", "negative"})
    public void testInvalidRegister_BlankField() {
        SoftAssert softAssert = new SoftAssert();

        ExtentReportManager.info("Submit Register form with one blank field: Email");
        // Clear email field to simulate blank input
        formInputs.setEmail("");
        // Fill and submit form
        registerPage.fillRegisterFormThenSubmit(formInputs);

        ExtentReportManager.info("Verify field validation error");
        String expectedMsg = MessagesUI.getRequiredFieldError();
        verifyFieldErrorMsg("email", expectedMsg, softAssert);

        softAssert.assertAll();
    }

    @Test(groups = {"component", "auth", "register", "negative"})
    public void testInvalidRegister_InvalidInput() {
        SoftAssert softAssert = new SoftAssert();

        ExtentReportManager.info("Submit Register form with one blank field: Full Name");
        // Set full name to invalid value containing numbers
        String invalidFullName = generateInvalidNameContainingNumbers();
        formInputs.setFullName(invalidFullName);
        // Fill and submit form
        registerPage.fillRegisterFormThenSubmit(formInputs);

        ExtentReportManager.info("Verify field validation error");
        String expectedMsg = MessagesUI.getNameContainsNumberError();
        verifyFieldErrorMsg("fullName", expectedMsg, softAssert);

        softAssert.assertAll();
    }

    @Test(groups = {"component", "auth", "register", "negative"})
    public void testInvalidRegister_MismatchedPasswords() {
        SoftAssert softAssert = new SoftAssert();

        ExtentReportManager.info("Submit Register form with mismatched confirm password");
        // Modify confirm password to not match password
        String mismatchedPassword = generateNewPassword(formInputs.getPassword());
        formInputs.setConfirmPassword(mismatchedPassword);
        // Fill and submit form
        registerPage.fillRegisterFormThenSubmit(formInputs);

        ExtentReportManager.info("Verify field validation error");
        String expectedMsg = MessagesUI.getPasswordMismatchError();
        verifyFieldErrorMsg("confirmPassword", expectedMsg, softAssert);

        softAssert.assertAll();
    }

    // Form Validation Tests (Server-side)
    @Test(groups = {"integration", "auth", "register", "negative", "critical"})
    public void testInvalidRegister_ExistingUsername() {
        SoftAssert softAssert = new SoftAssert();

        ExtentReportManager.info("Submit Register form with existing username");
        RegisterInputs request = generateValidRegisterFormInputs();
        // Get existing username from test user for simplicity - can also get from API if needed
        TestUser existingUser = TestUserProvider.getUser(TestUserType.USER_BASIC);
        // Set existing username in form inputs
        formInputs.setUsername(existingUser.getUsername());
        // Fill and submit form
        registerPage.fillRegisterFormThenSubmit(formInputs);

        ExtentReportManager.info("Verify form validation error");
        String expectedMsg = MessagesUI.getRegisterExistingUsernameError();
        verifyFormErrorAlert(expectedMsg, softAssert);

        softAssert.assertAll();
    }

    @Test(groups = {"integration", "auth", "register", "negative", "critical"})
    public void testRegister_ExistingEmail() {
        SoftAssert softAssert = new SoftAssert();

        ExtentReportManager.info("Step 2: Submit Register form with existing email");
        // Get existing email from test user for simplicity - can also get from API if needed
        TestUser existingUser = TestUserProvider.getUser(TestUserType.USER_BASIC);
        // Set existing email in form inputs
        formInputs.setEmail(existingUser.getEmail());
        // Fill and submit form
        registerPage.fillRegisterFormThenSubmit(formInputs);

        ExtentReportManager.info("Step 3: Verify form validation error");
        String expectedMsg = MessagesUI.getRegisterExistingEmailError();
        verifyFormErrorAlert(expectedMsg, softAssert);

        softAssert.assertAll();
    }

    // ---- Helper methods for verification ----
    private void verifyFormErrorAlert(String expectedMsg, SoftAssert softAssert) {
        // Verify error alert is displayed
        boolean errorDisplayed = SoftAssertionHelper.verifySoftTrue(registerPage.isRegisterErrorAlertDisplayed(),
                "Register error alert is displayed", getDriver(), softAssert);

        // Verify error message text
        if (errorDisplayed) {
            String actualMsg = registerPage.getRegisterErrorMsgText();
            SoftAssertionHelper.verifySoftEquals(actualMsg, expectedMsg, "Register form error message text", getDriver(), softAssert);
        }
    }

    private void verifyFieldErrorMsg(String fieldName, String expectedMsg, SoftAssert softAssert) {
        boolean errorDisplayed = registerPage.isFieldValidationErrorDisplayed(fieldName);
        SoftAssertionHelper.verifySoftTrue(errorDisplayed, fieldName + " field error is displayed", getDriver(), softAssert);

        if (errorDisplayed) {
            String actualMsg = registerPage.getFieldErrorText(fieldName);
            SoftAssertionHelper.verifySoftEquals(actualMsg, expectedMsg, fieldName + " error message text", getDriver(), softAssert);
        }
    }
}
