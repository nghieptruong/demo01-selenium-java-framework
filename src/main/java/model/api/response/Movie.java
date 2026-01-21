package model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Movie data model from API response.
 * Contains movie information including title, trailer, image, and description.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private String maPhim;
    private String tenPhim;
    private String trailer;
    private String hinhAnh;
    private String moTa;
}

