package com.example.hotel_api.store.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalTimeEntity {
    private String checkIn;
    private String checkOut;
}
