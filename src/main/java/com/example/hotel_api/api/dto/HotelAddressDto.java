package com.example.hotel_api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Information about the hotel's address")
public class HotelAddressDto {
    @Schema(description = "House number", example = "10")
    Integer houseNumber;
    @Schema(description = "Street name", example = "Main Street")
    String street;
    @Schema(description = "City where the hotel is located", example = "Minsk")
    String city;
    @Schema(description = "County", example = "Belarus")
    String county;
    @Schema(description = "Postal code", example = "00-001")
    String postCode;
}
