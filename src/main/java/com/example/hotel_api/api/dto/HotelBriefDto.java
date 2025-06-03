package com.example.hotel_api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Brief information about a hotel")
public class HotelBriefDto {
    @Schema(description = "Unique identifier of the hotel", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Schema(description = "Name of the hotel", example = "DoubleTree by Hilton Minsk")
    String name;

    @Schema(description = "Brief description of the hotel", example = "The DoubleTree by Hilton Hotel Minsk offers" +
            " 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the" +
            " hotel's 20th floor ...")
    String description;

    @Schema(description = "Hotel address (concise string)", example = "9 Pobediteley Avenue, Minsk, 220004, Belarus")
    String address;

    @Schema(description = "Hotel contact phone number", example = "+375 17 309-80-00")
    String phone;
}
