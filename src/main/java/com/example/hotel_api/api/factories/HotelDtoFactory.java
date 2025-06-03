package com.example.hotel_api.api.factories;

import com.example.hotel_api.store.entities.HotelEntity;
import org.springframework.stereotype.Component;

import com.example.hotel_api.api.dto.HotelAddressDto;
import com.example.hotel_api.api.dto.HotelArrivalTimeDto;
import com.example.hotel_api.api.dto.HotelBriefDto;
import com.example.hotel_api.api.dto.HotelContactsDto;
import com.example.hotel_api.api.dto.HotelCreateRequestDto;
import com.example.hotel_api.api.dto.HotelDetailsDto;
import com.example.hotel_api.store.entities.AddressEntity;
import com.example.hotel_api.store.entities.ArrivalTimeEntity;
import com.example.hotel_api.store.entities.ContactEntity;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class HotelDtoFactory {
    public HotelBriefDto makeHotelBriefDto(HotelEntity hotel) {
        String fullAddress = Optional.ofNullable(hotel.getAddress())
                .map(address -> String.format("%d %s, %s, %s, %s",
                        address.getHouseNumber(),
                        address.getStreet(),
                        address.getCity(),
                        address.getPostCode(),
                        address.getCounty()))
                .orElse(null);

        String phoneNumber = Optional.ofNullable(hotel.getContacts())
                .map(ContactEntity::getPhone)
                .orElse(null);

        return HotelBriefDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(fullAddress)
                .phone(phoneNumber)
                .build();
    }

    public HotelDetailsDto makeHotelDetailsDto(HotelEntity hotel) {
        HotelAddressDto addressDto = Optional.ofNullable(hotel.getAddress())
                .map(address -> HotelAddressDto.builder()
                        .houseNumber(address.getHouseNumber())
                        .street(address.getStreet())
                        .city(address.getCity())
                        .postCode(address.getPostCode())
                        .county(address.getCounty())
                        .build())
                .orElse(null);

        HotelContactsDto contactsDto = Optional.ofNullable(hotel.getContacts())
                .map(contacts -> HotelContactsDto.builder()
                        .phone(contacts.getPhone())
                        .email(contacts.getEmail())
                        .build())
                .orElse(null);

        HotelArrivalTimeDto arrivalTimeDto = Optional.ofNullable(hotel.getArrivalTime())
                .map(arrivalTime -> HotelArrivalTimeDto.builder()
                        .checkIn(arrivalTime.getCheckIn())
                        .checkOut(arrivalTime.getCheckOut())
                        .build())
                .orElse(null);

        return HotelDetailsDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .brand(hotel.getBrand())
                .address(addressDto)
                .contacts(contactsDto)
                .arrivalTime(arrivalTimeDto)
                .amenities(
                        new ArrayList<>(Optional.ofNullable(hotel.getAmenities())
                                .orElseGet(ArrayList::new))
                )
                .build();
    }

    public HotelEntity makeHotel(HotelCreateRequestDto requestDto) {
        AddressEntity address = Optional.ofNullable(requestDto.getAddress())
                .map(dto -> new AddressEntity(
                        dto.getHouseNumber(),
                        dto.getStreet(),
                        dto.getCity(),
                        dto.getPostCode(),
                        dto.getCounty()
                ))
                .orElse(null);

        ContactEntity contacts = Optional.ofNullable(requestDto.getContacts())
                .map(dto -> new ContactEntity(
                        dto.getPhone(),
                        dto.getEmail()
                ))
                .orElse(null);

        ArrivalTimeEntity arrivalTime = Optional.ofNullable(requestDto.getArrivalTime())
                .map(dto -> new ArrivalTimeEntity(
                        dto.getCheckIn(),
                        dto.getCheckOut()
                ))
                .orElse(null);

        return HotelEntity.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .brand(requestDto.getBrand())
                .address(address)
                .contacts(contacts)
                .arrivalTime(arrivalTime)
                .amenities(new ArrayList<>())
                .build();
    }
}
