package com.example.hotel_api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Contact information for the hotel")
public class HotelContactsDto {
    @Schema(description = "Hotel phone number", example = "+375 29 4556344")
    String phone;
    @Schema(description = "Hotel email address", example = "hotel@example.com")
    String email;
}
