package model.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Cinema/theater system data model from API response.
 * Represents a cinema chain/system (e.g., CGV, BHD Star).
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CinemaSystem {
    private String maHeThongRap;
    private String tenHeThongRap;
    private String biDanh;
    private String logo;
}

