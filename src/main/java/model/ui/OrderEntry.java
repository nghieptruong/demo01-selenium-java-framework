package model.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntry {
    private String purchaseDatetime;
    private String movieName;
    private Integer price;
    private String cinemaBranchName;
    private String theaterName;
    private List<String> seatNumbers;

    public OrderEntry setPurchaseDatetime(String purchaseDatetime) {
        this.purchaseDatetime = purchaseDatetime;
        return this;
    }

    public OrderEntry setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public OrderEntry setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public OrderEntry setCinemaBranchName(String cinemaBranchName) {
        this.cinemaBranchName = cinemaBranchName;
        return this;
    }

    public OrderEntry setTheaterName(String theaterName) {
        this.theaterName = theaterName;
        return this;
    }

    public OrderEntry setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
        return this;
    }
}