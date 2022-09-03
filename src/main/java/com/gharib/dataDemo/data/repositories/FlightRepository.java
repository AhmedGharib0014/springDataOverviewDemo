package com.gharib.dataDemo.data.repositories;

import com.gharib.dataDemo.data.models.FLight;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<FLight,Long> {
    List<FLight> findByOrigin(String origin);

    List<FLight> findByOriginAndDestination(String origin,String destination);


    List<FLight> findByDestinationIn(String...des);

    List<FLight> findByOriginIgnoreCase(String origin);
}
