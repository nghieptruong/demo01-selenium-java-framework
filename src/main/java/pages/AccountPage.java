package pages;

import config.Routes;
import model.UserAccount;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import reports.ExtentReportManager;

/**
 * Page Object for Account management page.
 * Handles user profile updates and account information.
 */
public class AccountPage extends CommonPage {

    // ============================================
    // ---- Page Elements ----
    // ============================================

    // ---- Main form ----
    @FindBy (css = "form")
    private WebElement formAccount;

    // ---- Form fields ----
    @FindBy (id = "taiKhoan")
    private WebElement txtUsername;
    @FindBy (id = "matKhau")
    private WebElement txtPassword;
    @FindBy (id = "hoTen")
    private WebElement txtFullName;
    @FindBy (id = "email")
    private WebElement txtEmail;
    @FindBy (id = "soDt")
    private WebElement txtPhoneNumber;

    // ---- Form button ----
    @FindBy (xpath = "//button[.='Cập Nhật']")
    private WebElement btnSaveChanges;

    // ---- Form alerts ----
    @FindBy (xpath = "//div[@role='dialog']//h2")
    private WebElement alertFormUpdate;
    @FindBy(id = "hoTen-helper-text")
    private WebElement lblFullNameError;
    @FindBy (id = "email-helper-text")
    private WebElement lblEmailError;
    @FindBy (id = "matKhau-helper-text")
    private WebElement lblPasswordError;

    // ============================================
    // Constructor
    // ============================================
    public AccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ============================================
    // ---- Public Methods  ----
    // ============================================

    // ---- Navigation ----
    public void navigateToAccountPage() {
        LOG.info("Navigate to Account Page");
        driver.get(url(Routes.ACCOUNT));
    }

    // ---- Waits ----
    public void waitForAccountForm(){
        LOG.info("Wait for Account form to be visible");
        waitForVisibilityOfElementLocated(formAccount);
    }

    public void waitForUpdateAlert(){
        waitForVisibilityOfElementLocated(alertFormUpdate);
    }

    public void waitForUpdateAlertToDisappear() {
        waitForInvisibilityOfElementLocated(alertFormUpdate);
    }

    // ---- Form interactions: fill/update fields, click buttons ----
    public void changeName(String newName) {
        clear(txtFullName);
        enterText(txtFullName, newName);
    }

    public void changeEmail(String newEmail) {
        clear(txtEmail);
        enterText(txtEmail, newEmail);
    }

    public void changePhoneNumber(String newPhoneNumber) {
        clear(txtPhoneNumber);
        enterText(txtPhoneNumber, newPhoneNumber);
    }

    public void changeAccountInfo(String newName, String newEmail, String newPhoneNumber) {
        changeName(newName);
        changeEmail(newEmail);
        changePhoneNumber(newPhoneNumber);
    }

    public void changePassword(String newPassword) {
        clear(txtPassword);
        enterText(txtPassword, newPassword);
    }

    public void changeUsername(String newUsername) {
        clear(txtUsername);
        enterText(txtUsername, newUsername);
    }

    public void saveChanges() {
        click(btnSaveChanges);
    }

    // ---- Getters ----
    //  Get field values
    public String getUsername() {
        return getFieldValue(txtUsername);
    }

    public String getFullName() {
        return getFieldValue(txtFullName);
    }

    public String getEmail() {
        return getFieldValue(txtEmail);
    }

    public String getPhoneNumber() {
        return getFieldValue(txtPhoneNumber);
    }

    public String getPassword() {
        return getFieldValue(txtPassword);
    }

    /**
     * Get all account data as UserAccount object.
     * Useful for verification against expected data.
     *
     * @return UserAccount object populated with current UI values
     */
    public UserAccount getAccountData() {
        return UserAccount.builder()
                .taiKhoan(getUsername())
                .hoTen(getFullName())
                .email(getEmail())
                .soDt(getPhoneNumber())
                .matKhau(getPassword())
                .build();
    }

    // Get alerts and validation errors visibility and text
    public boolean isUpdateAlertDisplayed() {
        return isElementDisplayedShort(alertFormUpdate);
    }

    public String getUpdateAlertText() {
        return getText(alertFormUpdate);
    }

    public boolean isNameValidationErrorDisplayed() {
        return isElementDisplayedShort(lblFullNameError);
    }

    public String getNameValidationErrorText() {
        return getText(lblFullNameError);
    }

    public boolean isEmailValidationErrorDisplayed() {
        return isElementDisplayedShort(lblEmailError);
    }

    public String getEmailValidationErrorText() {
        return getText(lblEmailError);
    }

    public boolean isPasswordValidationErrorDisplayed() {
        return isElementDisplayedShort(lblPasswordError);
    }

    public String getPasswordValidationErrorText() {
        return getText(lblPasswordError);
    }
}
