package helpers.actions;

import config.ConfigManager;
import model.ui.LoginDataUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.CommonPage;
import pages.LoginPage;

public class AuthActionHelper {

    private static final Logger LOG = LogManager.getLogger(AuthActionHelper.class);

    public static void login(LoginPage loginPage, String username, String password) {
        LOG.info("Logging in with username: " + username + " and password: " + password );
        loginPage.navigateToLoginPage();
        loginPage.fillLoginFormThenSubmit(username, password);
        loginPage.topBarNavigation.waitForUserProfileLink();
    }

    public static void login(LoginPage loginPage, LoginDataUI userCredentials) {
        login(loginPage, userCredentials.getTaiKhoan(), userCredentials.getMatKhau());
    }

    // Login as default user if no credentials provided
    public static void loginAsDefaultUser(LoginPage loginPage) {
        String username = ConfigManager.getDefaultUserUsername();
        String password = ConfigManager.getDefaultUserPassword();

        login(loginPage, username, password);
    }

    public static void logout(CommonPage page) {
        LOG.info("Logging out");
        page.topBarNavigation.clickLogoutLinkAndConfirm();
        page.topBarNavigation.waitForLoginLink();
    }

}
