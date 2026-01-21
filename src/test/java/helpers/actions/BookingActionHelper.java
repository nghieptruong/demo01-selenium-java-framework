package helpers.actions;

import helpers.providers.BookingSamplesProvider;
import helpers.utils.PickRandomHelper;
import helpers.providers.TestUserProvider;
import model.api.response.ShowtimeBooking;
import model.TestUser;
import model.TestUserType;
import pages.LoginPage;
import pages.BookingPage;

import java.util.List;

/**
 * Helper class for common booking-related actions.
 * Provides reusable methods for booking flows used across component and E2E tests.
 */
public class BookingActionHelper {

    public static void loginAsBookingUser(LoginPage loginPage) {
        TestUser testUser = TestUserProvider.getUser(TestUserType.USER_BOOKING);

        loginPage.navigateToLoginPage();
        loginPage.fillLoginFormAndSubmit(testUser.getUsername(), testUser.getPassword());
        loginPage.topBarNavigation.waitForUserProfileLink();
    }

    /**
     * Navigates to a random showtime page that has available seats (at least 5 seats available if not specified).
     *
     * @param bookingPage The ShowtimePage instance
     * @throws Exception if no showtimes with available seats are found
     */
    public static void navigateToAvailableShowtimePage(BookingPage bookingPage) throws Exception {
        ShowtimeBooking showtime = BookingSamplesProvider.getShowtimeWithAvailableSeats();
        String showtimeId = showtime.getShowtimeId();

        bookingPage.navigateToShowtimePage(showtimeId);
    }

    /**
     * Selects a specified number of available seats on the showtime page.
     * Seats are selected randomly from the available seats.
     *
     * @param bookingPage The ShowtimePage instance
     * @param sampleSize Number of seats to select
     * @return List of selected seat numbers
     */
    public static List<String> selectSampleSeats(BookingPage bookingPage, int sampleSize) {
        List<String> availableSeats = bookingPage.getAvailableSeatNumbers();
        List<String> seatsToSelect = PickRandomHelper.getRandomSamplesFromList(availableSeats, sampleSize);

        bookingPage.selectAvailableSeats(seatsToSelect);
        return seatsToSelect;
    }

    /**
     * Selects 2 available seats on the showtime page by default.
     *
     * @param bookingPage The ShowtimePage instance
     * @return List of selected seat numbers
     */
    public static List<String> selectSampleSeats(BookingPage bookingPage) {
        return selectSampleSeats(bookingPage, 2);
    }

    /**
     * Selects a random number of available seats within the specified range on the showtime page.
     *
     *
     * @param bookingPage The ShowtimePage instance
     * @param minQuan Minimum number of seats to select
     * @param maxQuan Maximum number of seats to select
     * @return List of selected seat numbers
     */
    public static List<String> selectAvailableSeatsWithinRange(BookingPage bookingPage, int minQuan, int maxQuan) {
        List<String> availableSeats = bookingPage.getAvailableSeatNumbers();

        // Determine the maximum number of seats that can be selected
        int maxSize = Math.min(availableSeats.size(), maxQuan);

        // Ensure minQuan < maxQuan
        if (minQuan > maxQuan) {
            throw new IllegalArgumentException("minQuan should not be greater than maxQuan");
        }

        // Get a random size within the specified range
        int randomSize = PickRandomHelper.getRandomIntInRange(minQuan, maxSize);

        // Select random seats based on the determined size
        List<String> seatsToSelect = PickRandomHelper.getRandomSamplesFromList(availableSeats, randomSize);

        return seatsToSelect;
    }
}
