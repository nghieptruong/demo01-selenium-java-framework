package helpers.verifications;

import helpers.utils.MessagesUI;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.BookingPage;
import reports.ExtentReportManager;

import java.util.List;

import static helpers.utils.SoftAssertionHelper.*;

/**
 * Helper class for booking-related verifications.
 * Handles verification of booking success, blocked bookings for unauthenticated users,
 * and error messages related to seat selection.
 *
 * <p>Uses soft assertions for multiple related checks and E2E test compatibility.
 */
public class BookingVerificationHelper {

    /**
     * Verify booking success - message displayed, seats no longer available after booking.
     * Uses SoftAssertionHelper to automatically capture screenshots on each failed soft assertion.
     *
     * @param bookingPage The ShowtimePage instance
     * @param selectedSeats List of seat numbers that were booked
     * @param driver WebDriver instance (needed for screenshot capture)
     * @param softAssert The SoftAssert instance for accumulating assertions
     */
    public static void verifyBookingSuccess(BookingPage bookingPage, List<String> selectedSeats, WebDriver driver, SoftAssert softAssert) throws InterruptedException {
        // Verify success by checking alert message and seat availability, current website did not implement payment flow
        boolean isAlertDisplayed = bookingPage.isBookingAlertDisplayed();
        verifySoftTrue(isAlertDisplayed,
                "Booking alert is displayed", driver, softAssert);

        if (isAlertDisplayed) {
            String expectedMsg = MessagesUI.getBookingSuccessMessage();
            String actualMsg = bookingPage.getBookingAlertText();
            verifySoftEquals(actualMsg, expectedMsg, "Alert success message text", driver, softAssert);

            // Close the alert to proceed
            bookingPage.clickAlertConfirmButton();
        }

        // Refresh page to ensure seat map is updated before verifying seats are no longer available
        bookingPage.refreshPage();
        bookingPage.waitForSeatMapToLoad();

        verifySoftFalse(bookingPage.areSeatsAvailable(selectedSeats),
                "Booked seats: " + selectedSeats + " are no longer available" , driver, softAssert);
    }

    /**
     * Verify booking blocked for guest user - login required message displayed, seats remains available after attempt.
     * Uses SoftAssertionHelper to automatically capture screenshots on each failed soft assertion.
     *
     * @param bookingPage The ShowtimePage instance
     * @param selectedSeats List of seat numbers that were selected
     * @param driver WebDriver instance (needed for screenshot capture)
     * @param softAssert The SoftAssert instance for accumulating assertions
     */
    public static void verifyBookingBlockedForGuest(BookingPage bookingPage, List<String> selectedSeats, WebDriver driver, SoftAssert softAssert) {
        String expectedMsg = MessagesUI.getUnauthenticatedBookingError();
        String actualMsg = bookingPage.getBookingAlertText();
        verifySoftEquals(actualMsg, expectedMsg, "Unauthenticated booking error text", driver, softAssert);

        bookingPage.refreshPage();
        bookingPage.waitForSeatMapToLoad();

        verifySoftTrue(bookingPage.areSeatsAvailable(selectedSeats),
                "Selected seats are still available after failed booking attempt by guest",
                driver, softAssert);
    }

    /**
     * Verify "no seat selected" error message is displayed.
     * Uses hard assertion since this is typically the main assertion of the test.
     *
     * @param bookingPage The ShowtimePage instance
     */
    public static void verifyNoSeatSelectedError(BookingPage bookingPage) {
        String expectedMsg = MessagesUI.getNoSeatSelectedError();
        String actualMsg = bookingPage.getBookingAlertText();
        Assert.assertEquals(actualMsg, expectedMsg, "Booking error for no seat selection");
        ExtentReportManager.pass("No seat selection error displayed correctly: " + actualMsg);
    }
}