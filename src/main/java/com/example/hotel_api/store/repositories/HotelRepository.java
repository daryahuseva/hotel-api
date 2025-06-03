package com.example.hotel_api.store.repositories;

import com.example.hotel_api.store.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    List<HotelEntity> findByNameContainingIgnoreCase(String name);

    List<HotelEntity> findByBrandContainingIgnoreCase(String brand);

    List<HotelEntity> findByAddress_CityContainingIgnoreCase(String city);

    List<HotelEntity> findByAddress_CountyContainingIgnoreCase(String county);

}
