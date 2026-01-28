package model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import utils.DateTimeNormalizer;

import java.util.List;

/**
 * Detailed showtime information from API response.
 * Includes movie details and complete seat layout with availability status.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowtimeBooking {

    private ShowtimeDetails thongTinPhim;
    private List<SeatBookingData> danhSachGhe;

    public ShowtimeDetails getThongTinPhim() {
        return thongTinPhim;
    }
    public List<SeatBookingData> getDanhSachGhe() {
        return danhSachGhe;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShowtimeDetails {
        private Integer maLichChieu; // Showtime ID
        private String tenCumRap;    // Cinema branch name
        private String tenRap;       // Theater name
        private String diaChi;       // Address
        private String tenPhim;      // Movie name
        private String ngayChieu;    // Show date
        private String gioChieu;     // Show time
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SeatBookingData {
        private Integer maGhe;        // Seat ID
        private String tenGhe;       // Seat number
        private String loaiGhe;      // Seat type
        private Integer giaVe;       // Ticket price
        private boolean daDat;       // Is booked
    }

    public String getShowtimeId() {
        return thongTinPhim.getMaLichChieu().toString();
    }

    public String getCinemaBranchName() {
        return thongTinPhim.getTenCumRap();
    }

    public String getCinemaAddress() {
        return thongTinPhim.getDiaChi();
    }

    public String getMovieName() {
        return thongTinPhim.getTenPhim();
    }

    public String getTheaterName() {
        return thongTinPhim.getTenRap();
    }

    public String getShowingDateTimeNormalized() {
        String dateTime = thongTinPhim.getNgayChieu() + " " + thongTinPhim.getGioChieu();
        return DateTimeNormalizer.normalize(dateTime);
    }

    public List<String> getAvailableSeats() {
        return danhSachGhe.stream()
                .filter(seat -> !seat.isDaDat())
                .map(seat -> seat.getTenGhe())
                .toList();
    }

    public List<String> getReservedSeats() {
        return danhSachGhe.stream()
                .filter(SeatBookingData::isDaDat)
                .map(SeatBookingData::getTenGhe)
                .toList();
    }

    public long getAvailableSeatsCount() {
        return danhSachGhe.stream()
                .filter(seat -> !seat.isDaDat())
                .count();
    }

    public long getReservedSeatsCount() {
        return danhSachGhe.stream()
                .filter(SeatBookingData::isDaDat)
                .count();
    }

}

