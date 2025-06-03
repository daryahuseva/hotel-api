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
    @Schema(description = "Name of the hotel", example = "Grand Hotel")
    String name;
    @Schema(description = "Brief description of the hotel", example = "A luxurious hotel located in the city center.")
    String description;
    @Schema(description = "Hotel address (concise string)", example = "Main Street 10, Minsk")
    String address;
    @Schema(description = "Hotel contact phone number", example = "+375 29 4354366")
    String phone;
}
