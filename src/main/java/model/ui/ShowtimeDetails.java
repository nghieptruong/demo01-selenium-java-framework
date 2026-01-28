package model.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowtimeDetails {
    protected String movieName;
    protected String cinemaBranchName;
    protected String cinemaAddress;
    protected String theaterName;
    protected String showtimeDateTime;

    public ShowtimeDetails setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public ShowtimeDetails setCinemaBranchName(String cinemaBranchName) {
        this.cinemaBranchName = cinemaBranchName;
        return this;
    }

    public ShowtimeDetails setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
        return this;
    }

    public ShowtimeDetails setTheaterName(String theaterName) {
        this.theaterName = theaterName;
        return this;
    }

    public ShowtimeDetails setShowtimeDateTime(String showtimeDateTime) {
        this.showtimeDateTime = showtimeDateTime;
        return this;
    }
}
