package com.example.hotel_api.service;

import com.example.hotel_api.api.dto.HotelBriefDto;
import com.example.hotel_api.api.dto.HotelCreateRequestDto;
import com.example.hotel_api.api.dto.HotelDetailsDto;
import com.example.hotel_api.api.exceptions.NotFoundException;
import com.example.hotel_api.api.factories.HotelDtoFactory;
import com.example.hotel_api.store.entities.HotelEntity;
import com.example.hotel_api.store.repositories.HotelRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelDtoFactory hotelDtoFactory;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllHotelsBrief_returnsList() {
        HotelEntity hotel = new HotelEntity();
        HotelBriefDto dto = new HotelBriefDto();
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));
        when(hotelDtoFactory.makeHotelBriefDto(hotel)).thenReturn(dto);

        List<HotelBriefDto> result = hotelService.getAllHotelsBrief();

        assertEquals(1, result.size());
        verify(hotelRepository).findAll();
        verify(hotelDtoFactory).makeHotelBriefDto(hotel);
    }

    @Test
    void getHotelDetailsById_found_returnsDto() {
        long id = 1L;
        HotelEntity hotel = new HotelEntity();
        HotelDetailsDto dto = new HotelDetailsDto();

        when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));
        when(hotelDtoFactory.makeHotelDetailsDto(hotel)).thenReturn(dto);

        HotelDetailsDto result = hotelService.getHotelDetailsById(id);

        assertEquals(dto, result);
    }

    @Test
    void getHotelDetailsById_notFound_throwsException() {
        long id = 2L;
        when(hotelRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> hotelService.getHotelDetailsById(id));
    }

    @Test
    void createHotel_savesAndReturnsDto() {
        HotelCreateRequestDto requestDto = new HotelCreateRequestDto();
        HotelEntity entity = new HotelEntity();
        HotelEntity savedEntity = new HotelEntity();
        HotelBriefDto dto = new HotelBriefDto();

        when(hotelDtoFactory.makeHotel(requestDto)).thenReturn(entity);
        when(hotelRepository.save(entity)).thenReturn(savedEntity);
        when(hotelDtoFactory.makeHotelBriefDto(savedEntity)).thenReturn(dto);

        HotelBriefDto result = hotelService.createHotel(requestDto);

        assertEquals(dto, result);
    }

    @Test
    void addAmenitiesToHotel_addsAndReturnsUpdatedHotel() {
        long id = 1L;
        HotelEntity hotel = new HotelEntity();
        hotel.setAmenities(new ArrayList<>(List.of("WiFi")));
        List<String> newAmenities = List.of("Pool");
        HotelEntity updatedHotel = new HotelEntity();
        updatedHotel.setAmenities(List.of("WiFi", "Pool"));
        HotelDetailsDto dto = new HotelDetailsDto();

        when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(hotel)).thenReturn(updatedHotel);
        when(hotelDtoFactory.makeHotelDetailsDto(updatedHotel)).thenReturn(dto);

        HotelDetailsDto result = hotelService.addAmenitiesToHotel(id, newAmenities);

        assertEquals(dto, result);
        assertTrue(result != null);
    }

    @Test
    void getHistogram_brand_returnsMap() {
        HotelEntity hotel1 = new HotelEntity();
        hotel1.setBrand("Hilton");
        HotelEntity hotel2 = new HotelEntity();
        hotel2.setBrand("Hilton");

        when(hotelRepository.findAll()).thenReturn(List.of(hotel1, hotel2));

        Map<String, Long> result = hotelService.getHistogram("brand");

        assertEquals(1, result.size());
        assertEquals(2L, result.get("Hilton"));
    }
}
