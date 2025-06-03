package com.example.hotel_api.service;

import com.example.hotel_api.api.dto.HotelBriefDto;
import com.example.hotel_api.api.dto.HotelCreateRequestDto;
import com.example.hotel_api.api.dto.HotelDetailsDto;
import com.example.hotel_api.api.exceptions.BadRequestException;
import com.example.hotel_api.api.exceptions.NotFoundException;
import com.example.hotel_api.api.factories.HotelDtoFactory;
import com.example.hotel_api.store.entities.HotelEntity;
import com.example.hotel_api.store.repositories.HotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelDtoFactory hotelDtoFactory;

    @Autowired
    public HotelService(HotelRepository hotelRepository, HotelDtoFactory hotelDtoFactory) {
        this.hotelRepository = hotelRepository;
        this.hotelDtoFactory = hotelDtoFactory;
    }

    @Transactional(readOnly = true)
    public List<HotelBriefDto> getAllHotelsBrief() {
        return hotelRepository.findAll().stream()
                .map(hotelDtoFactory::makeHotelBriefDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HotelDetailsDto getHotelDetailsById(Long id) {
        return hotelRepository.findById(id)
                .map(hotelDtoFactory::makeHotelDetailsDto)
                .orElseThrow(() -> new NotFoundException("Hotel with ID " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<HotelBriefDto> searchHotels(String name, String brand, String city, String county, String amenities) {
        List<HotelEntity> hotels;

        if (name != null && !name.isEmpty()) {
            hotels = hotelRepository.findByNameContainingIgnoreCase(name);
        } else if (brand != null && !brand.isEmpty()) {
            hotels = hotelRepository.findByBrandContainingIgnoreCase(brand);
        } else if (city != null && !city.isEmpty()) {
            hotels = hotelRepository.findByAddress_CityContainingIgnoreCase(city);
        } else if (county != null && !county.isEmpty()) {
            hotels = hotelRepository.findByAddress_CountyContainingIgnoreCase(county);
        } else {
            hotels = hotelRepository.findAll();
        }

        if (amenities != null && !amenities.isEmpty()) {
            String searchAmenityLower = amenities.toLowerCase();
            hotels = hotels.stream()
                    .filter(hotel -> Optional.ofNullable(hotel.getAmenities())
                            .orElseGet(java.util.ArrayList::new)
                            .stream()
                            .anyMatch(a -> a.toLowerCase().contains(searchAmenityLower)))
                    .collect(Collectors.toList());
        }

        return hotels.stream()
                .map(hotelDtoFactory::makeHotelBriefDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public HotelBriefDto createHotel(HotelCreateRequestDto requestDto) {
        HotelEntity hotel = hotelDtoFactory.makeHotel(requestDto);
        HotelEntity savedHotel = hotelRepository.save(hotel);
        return hotelDtoFactory.makeHotelBriefDto(savedHotel);
    }

    @Transactional
    public HotelDetailsDto addAmenitiesToHotel(Long id, List<String> newAmenities) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    if (hotel.getAmenities() == null) {
                        hotel.setAmenities(new java.util.ArrayList<>());
                    }
                    hotel.getAmenities().addAll(newAmenities);
                    HotelEntity updatedHotel = hotelRepository.save(hotel);
                    return hotelDtoFactory.makeHotelDetailsDto(updatedHotel);
                })
                .orElseThrow(() -> new NotFoundException("Hotel with ID " + id + " not found for adding amenities"));
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getHistogram(String param) {
        List<HotelEntity> hotels = hotelRepository.findAll();

        switch (param.toLowerCase()) {
            case "brand":
                return hotels.stream()
                        .filter(hotel -> hotel.getBrand() != null && !hotel.getBrand().isEmpty())
                        .collect(Collectors.groupingBy(HotelEntity::getBrand, Collectors.counting()));
            case "city":
                return hotels.stream()
                        .filter(hotel -> hotel.getAddress() != null && hotel.getAddress().getCity() != null && !hotel.getAddress().getCity().isEmpty())
                        .collect(Collectors.groupingBy(hotel -> hotel.getAddress().getCity(), Collectors.counting()));
            case "county":
                return hotels.stream()
                        .filter(hotel -> hotel.getAddress() != null && hotel.getAddress().getCounty() != null && !hotel.getAddress().getCounty().isEmpty())
                        .collect(Collectors.groupingBy(hotel -> hotel.getAddress().getCounty(), Collectors.counting()));
            case "amenities":
                return hotels.stream()
                        .flatMap(hotel -> Optional.ofNullable(hotel.getAmenities())
                                .orElseGet(java.util.ArrayList::new)
                                .stream())
                        .filter(amenity -> amenity != null && !amenity.isEmpty())
                        .collect(Collectors.groupingBy(amenity -> amenity, Collectors.counting()));
            default:
                throw new BadRequestException("Invalid histogram parameter: " + param + ". Supported parameters" +
                        " are brand, city, county, amenities.");
        }
    }
}
