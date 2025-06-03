package com.example.hotel_api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Information about hotel check-in and check-out times")
public class HotelArrivalTimeDto {
    @Schema(description = "Hotel check-in time", example = "15:00")
    String checkIn;
    @Schema(description = "Hotel check-out time", example = "11:00")
    String checkOut;
}
