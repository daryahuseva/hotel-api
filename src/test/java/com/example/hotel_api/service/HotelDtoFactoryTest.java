package com.example.hotel_api.service;

import com.example.hotel_api.api.dto.*;
import com.example.hotel_api.api.factories.HotelDtoFactory;
import com.example.hotel_api.store.entities.*;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HotelDtoFactoryTest {

    private final HotelDtoFactory factory = new HotelDtoFactory();

    @Test
    void makeHotelBriefDto_shouldReturnCorrectDto() {
        HotelEntity entity = HotelEntity.builder()
                .id(1L)
                .name("DoubleTree by Hilton Minsk")
                .description("The DoubleTree by Hilton Hotel Minsk offers 193 " +
                        "luxurious rooms in the Belorussian capital and stunning views of " +
                        "Minsk city from the hotel's 20th floor.")
                .address(AddressEntity.builder()
                        .houseNumber(9)
                        .street("Pobediteley Avenue")
                        .city("Minsk")
                        .county("Belarus")
                        .postCode("220004")
                        .build())
                .contacts(ContactEntity.builder()
                        .phone("+375 17 309-80-00")
                        .email("doubletreeminsk.info@hilton.com")
                        .build())
                .build();

        HotelBriefDto dto = factory.makeHotelBriefDto(entity);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("DoubleTree by Hilton Minsk");
        assertThat(dto.getDescription()).isEqualTo("The DoubleTree by Hilton Hotel Minsk offers 193 " +
                "luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's " +
                "20th floor.");
        assertThat(dto.getAddress()).contains("9 Pobediteley Avenue, Minsk, 220004, Belarus");
        assertThat(dto.getPhone()).isEqualTo("+375 17 309-80-00");
    }

    @Test
    void makeHotelDetailsDto_shouldMapFieldsCorrectly() {
        HotelEntity entity = HotelEntity.builder()
                .id(2L)
                .name("DoubleTree by Hilton Minsk")
                .brand("Hilton")
                .address(AddressEntity.builder().city("Minsk").build())
                .contacts(ContactEntity.builder().email("doubletreeminsk.info@hilton.com").build())
                .arrivalTime(ArrivalTimeEntity.builder()
                        .checkIn(String.valueOf(LocalTime.of(14, 0)))
                        .checkOut(String.valueOf(LocalTime.of(11, 0)))
                        .build())
                .amenities(List.of(
                        "Free parking", "Free WiFi", "Non-smoking rooms", "Concierge",
                        "On-site restaurant", "Fitness center", "Pet-friendly rooms",
                        "Room service", "Business center", "Meeting rooms"
                ))
                .build();

        HotelDetailsDto dto = factory.makeHotelDetailsDto(entity);

        assertThat(dto.getName()).isEqualTo("DoubleTree by Hilton Minsk");
        assertThat(dto.getBrand()).isEqualTo("Hilton");
        assertThat(dto.getAddress().getCity()).isEqualTo("Minsk");
        assertThat(dto.getContacts().getEmail()).isEqualTo("doubletreeminsk.info@hilton.com");
        assertThat(dto.getArrivalTime().getCheckIn()).isEqualTo("14:00");
        assertThat(dto.getAmenities()).containsExactlyInAnyOrder(
                "Free parking", "Free WiFi", "Non-smoking rooms", "Concierge",
                "On-site restaurant", "Fitness center", "Pet-friendly rooms",
                "Room service", "Business center", "Meeting rooms"
        );
    }

    @Test
    void makeHotel_shouldBuildEntityFromDto() {
        HotelCreateRequestDto request = HotelCreateRequestDto.builder()
                .name("DoubleTree by Hilton Minsk")
                .description("Modern")
                .brand("Hilton")
                .address(HotelAddressDto.builder().city("Minsk").build())
                .contacts(HotelContactsDto.builder().phone("+375 17 309-80-00").build())
                .arrivalTime(HotelArrivalTimeDto.builder()
                        .checkIn(String.valueOf(LocalTime.of(15, 0)))
                        .checkOut(String.valueOf(LocalTime.of(10, 0)))
                        .build())
                .build();

        HotelEntity entity = factory.makeHotel(request);

        assertThat(entity.getName()).isEqualTo("DoubleTree by Hilton Minsk");
        assertThat(entity.getAddress().getCity()).isEqualTo("Minsk");
        assertThat(entity.getContacts().getPhone()).isEqualTo("+375 17 309-80-00");
        assertThat(entity.getArrivalTime().getCheckIn()).isEqualTo("15:00");
    }
}

