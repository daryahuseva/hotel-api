package com.example.hotel_api.store.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity {
    private Integer houseNumber;
    private String street;
    private String city;
    private String county;
    private String postCode;
}
