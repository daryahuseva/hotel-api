package com.example.hotel_api.service;

import com.example.hotel_api.api.controllers.HotelController;
import com.example.hotel_api.api.dto.HotelBriefDto;
import com.example.hotel_api.api.dto.HotelDetailsDto;
import com.example.hotel_api.api.dto.HotelCreateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllHotels_shouldReturnList() throws Exception {
        when(hotelService.getAllHotelsBrief()).thenReturn(List.of(new HotelBriefDto()));

        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk());
    }

    @Test
    void getHotelById_shouldReturnHotel() throws Exception {
        when(hotelService.getHotelDetailsById(1L)).thenReturn(new HotelDetailsDto());

        mockMvc.perform(get("/property-view/hotels/1"))
                .andExpect(status().isOk());
    }

    @Test
    void createHotel_shouldReturnCreated() throws Exception {
        HotelCreateRequestDto requestDto = new HotelCreateRequestDto();
        HotelBriefDto responseDto = HotelBriefDto.builder().id(5L).build();

        when(hotelService.createHotel(any())).thenReturn(responseDto);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/property-view/hotels/5"));
    }

    @Test
    void addAmenities_shouldReturnUpdatedHotel() throws Exception {
        when(hotelService.addAmenitiesToHotel(eq(1L), anyList())).thenReturn(new HotelDetailsDto());

        mockMvc.perform(post("/property-view/hotels/1/amenities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of("Free WiFi", "Free parking"))))
                .andExpect(status().isOk());
    }

    @Test
    void getHistogram_shouldReturnMap() throws Exception {
        when(hotelService.getHistogram("brand")).thenReturn(Map.of("Hilton", 3L));

        mockMvc.perform(get("/property-view/histogram/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Hilton").value(3));
    }

    @Test
    void searchHotels_shouldReturnList() throws Exception {
        when(hotelService.searchHotels(any(), any(), any(), any(), any()))
                .thenReturn(List.of(new HotelBriefDto()));

        mockMvc.perform(get("/property-view/search?name=Hilton"))
                .andExpect(status().isOk());
    }
}

