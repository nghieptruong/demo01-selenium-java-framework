package testcases.browsing;

import base.BaseTest;
import helpers.providers.BookingSampleProvider;
import model.api.response.ShowtimeBooking;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import reports.ExtentReportManager;

public class TC36_DropdownAppliedFiltersNavigationTest extends BaseTest {

    @Test
    public void testValidFiltersNavigateToCorrectBookingPage() throws Exception {
        // Navigate to Homepage and wait for dropdowns to load
        ExtentReportManager.info("Navigate to Home Page and wait for dropdowns to load");
        HomePage homePage = new HomePage(getDriver());
        homePage.navigateToHomePage();
        homePage.showtimeFilterDropdowns.waitForDropdownsToLoad();

        // Find a random showtime and get its details: movie name, cinema branch, showtime id to apply for filters
        ExtentReportManager.info("Get sample showtime filter options for testing");
        ShowtimeBooking showtimeWithSeats = BookingSampleProvider.getRandomShowtime();

        String movieOption = showtimeWithSeats.getMovieName();
        String cinemaOption = showtimeWithSeats.getCinemaBranchName();
        String showtimeOption = showtimeWithSeats.getShowtimeId();

        // Apply filter and click Find Ticket button
        ExtentReportManager.info("Apply showtime filters: Movie='" + movieOption + "', Cinema='" + cinemaOption + "'" +
                ", Showtime ID='" + showtimeOption + "'");
        homePage.showtimeFilterDropdowns.selectAllFiltersAndConfirm(
                movieOption,
                cinemaOption,
                showtimeOption
        );

        // Verify correct navigation to showtime booking page
        ExtentReportManager.info("Verify successful redirection to correct showtime booking page");
        Assert.assertTrue(homePage.isRedirectedToBookingPage(showtimeOption),
                "Not redirected to correct booking page");
    }
}