package com.example.hotel_api.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Contact information for the hotel")
public class HotelContactsDto {
    @Schema(description = "Hotel phone number", example = "+375 17 309-80-00")
    String phone;

    @Schema(description = "Hotel email address", example = "doubletreeminsk.info@hilton.com")
    String email;
}
