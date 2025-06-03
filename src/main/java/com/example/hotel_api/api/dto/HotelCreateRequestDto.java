package com.example.hotel_api.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Request body for creating a new hotel")
public class HotelCreateRequestDto {
    @Schema(description = "Name of the hotel", example = "New Grand Hotel", required = true)
    String name;
    @Schema(description = "Brand of the hotel", example = "Hilton")
    String brand;
    @Schema(description = "Full description of the hotel", example = "The DoubleTree by Hilton Hotel " +
            "Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of " +
            "Minsk city from the hotel's 20th floor.Good!")
    String description;

    @Schema(description = "Address details of the hotel")
    HotelAddressDto address;

    @Schema(description = "Contact information of the hotel")
    HotelContactsDto contacts;

    @Schema(description = "Check-in/check-out times of the hotel")
    HotelArrivalTimeDto arrivalTime;

}
