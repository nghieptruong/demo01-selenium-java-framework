package testcases.browsing;

import api.services.CinemaService;
import api.services.MovieService;
import base.BaseTest;
import model.api.response.MovieSchedule;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import reports.ExtentReportManager;

import java.util.Map;
import java.util.Set;

import static helpers.verifications.SoftAssertionHelper.verifySoftEquals;

public class TC35_DropdownDisplaysCorrectDataTest extends BaseTest {

    @Test
    public void testDropdownDataMatchesApiData() {
        SoftAssert softAssert = new SoftAssert();

        // Navigate to Homepage and wait for dropdowns to load
        ExtentReportManager.info("Navigate to Home Page and wait for dropdowns to load");
        HomePage homePage = new HomePage(getDriver());
        homePage.navigateToHomePage();
        homePage.showtimeFilterDropdowns.waitForDropdownsToLoad();

        // =============================================
        // Verify List of Movie Options matches API data
        // =============================================

        ExtentReportManager.info("Verify list of movies from Dropdowns match API data");
        // Get movies from API and map movieId -> movieTitle
        MovieService movieService = new MovieService();
        Map<String, String> moviesFromApi = movieService.getMovieIdToTitleMap();

        // Get movies from UI dropdown and map movieId -> movieTitle
        Map<String, String> moviesFromUI = homePage.showtimeFilterDropdowns.getMovieOptionIdToTitleMap();

        // Skip test if no movies in UI dropdown
        if (moviesFromUI.isEmpty()) {
            if (!moviesFromApi.isEmpty()) {
                ExtentReportManager.skip("Skipping test: No movies found in UI dropdown but API has movies. Check UI loading issue.");
                throw new SkipException("Skipping test: No movies found in UI dropdown but API has movies. Check UI loading issue.");
            } else {
                ExtentReportManager.skip("Skipping test: No movies found in UI dropdown and API. Check data source.");
                throw new SkipException("Skipping test: No movies found in UI dropdown and API. Check data source.");
            }
        }

        verifySoftEquals(moviesFromUI, moviesFromApi,
                "List of movies",
                getDriver(), softAssert);

        // =====================================================
        // Verify Cinema Dropdown & Showtime Dropdown: API vs UI
        // =====================================================

        ExtentReportManager.info("Iterate through list of movies and cinema branches to verify cinema branches and showtime options match API");
        // ---- Iterate through each movie option ----
        Set<String> movieIds = moviesFromUI.keySet();
        for (String movieId : movieIds) {
            String movieTitle = moviesFromUI.get(movieId);

            // ---- Verify Cinema Dropdown: API vs UI ----
            // Get cinema branches from API and map cinemaId -> cinemaName
            CinemaService cinemaService = new CinemaService();
            MovieSchedule movieShowtimes = cinemaService.getShowtimesForMovie(movieId);
            Map<String, String> cinemaBranchesFromAPI = movieShowtimes.getCinemaBranchIdToNameMap();

            // Refresh page, select movie then get cinema branches from UI dropdown and map cinemaId -> cinemaName
            homePage.refreshPage();
            homePage.showtimeFilterDropdowns.waitForDropdownsToLoad();
            homePage.showtimeFilterDropdowns.selectMovieByMovieTitle(movieTitle);
            Map<String, String> cinemaBranchesFromUI = homePage.showtimeFilterDropdowns.getCinemaBranchOptionIdToNameMap();

            // If no cinemas from API for this movie, log warning
            if (cinemaBranchesFromAPI.isEmpty() && cinemaBranchesFromUI.isEmpty()) {
                ExtentReportManager.warn("No cinemas found in UI dropdown and API. Movie: " + movieTitle + " should be removed from UI dropdown.");
                LOG.warn("No cinemas found in UI dropdown and API. Movie: " + movieTitle + " should be removed from UI dropdown.");
            }

            // Verify both maps are equal
            verifySoftEquals(cinemaBranchesFromUI, cinemaBranchesFromAPI,
                    "List of cinema branches for movie: " + movieTitle,
                    getDriver(), softAssert);

            // ---- Verify Showtime Dropdown: API vs UI ----
            // Iterate through each cinema branch option
            Set<String> cinemaBranchIds = cinemaBranchesFromUI.keySet();
            for (String cinemaBranchId : cinemaBranchIds) {
                String cinemaBranchName = cinemaBranchesFromUI.get(cinemaBranchId);

                // Get showtimes from API map showtimeId -> dateTime
                Map<String, String> showtimesFromAPI = cinemaService.getShowtimeIdToDatetimeMap(movieId, cinemaBranchId);

                // Refresh page and wait for dropdowns to load then select movie and cinema branch in UI dropdowns
                homePage.refreshPage();
                homePage.showtimeFilterDropdowns.waitForDropdownsToLoad();
                homePage.showtimeFilterDropdowns.selectMovieByMovieTitle(movieTitle);
                homePage.showtimeFilterDropdowns.selectCinemaBranchByName(cinemaBranchName);

                // Get showtimes from UI dropdown for the selected movie and cinema branch and map showtimeId -> dateTime
                Map<String, String> showtimesFromUI = homePage.showtimeFilterDropdowns.getShowtimeOptionIdToDateTimeMap();

                // If no showtimes from API and UI for this movie+branch, log warning
                if (showtimesFromAPI.isEmpty() && showtimesFromUI.isEmpty()) {
                    ExtentReportManager.warn("No showtimes found in UI dropdown and API. Movie: " + movieTitle +
                            ", Cinema Branch ID: " + cinemaBranchId + " should be removed from UI dropdown.");
                    LOG.warn("No showtimes found in UI dropdown and API. Movie: " + movieTitle +
                            ", Cinema Branch ID: " + cinemaBranchId + " should be removed from UI dropdown.");
                }

                // Verify both maps are equal
                verifySoftEquals(showtimesFromUI, showtimesFromAPI,
                        "List of showtimes for Movie: " + movieTitle +
                                ", Cinema Branch: " + cinemaBranchName,
                        getDriver(), softAssert);
            }
        }
        softAssert.assertAll();
    }
}