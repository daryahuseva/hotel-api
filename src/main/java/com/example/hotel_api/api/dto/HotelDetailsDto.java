package com.example.hotel_api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Full detailed information about a hotel")
public class HotelDetailsDto {
    @Schema(description = "Unique identifier of the hotel", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;
    @Schema(description = "Name of the hotel", example = "Grand Hyatt Warsaw")
    String name;
    @Schema(description = "Brand of the hotel", example = "Hyatt")
    String brand;
    @Schema(description = "Address details of the hotel")
    HotelAddressDto address;
    @Schema(description = "Contact information of the hotel")
    HotelContactsDto contacts;
    @Schema(description = "Check-in/check-out times of the hotel")
    HotelArrivalTimeDto arrivalTime;
    @Schema(description = "List of available amenities (e.g., WiFi, Pool, Fitness Center)", type = "array", example = "[\"WiFi\", \"Swimming Pool\", \"Fitness Center\"]")
    List<String> amenities;
}
