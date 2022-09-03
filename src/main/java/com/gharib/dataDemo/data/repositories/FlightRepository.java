package com.gharib.dataDemo.data.repositories;

import com.gharib.dataDemo.data.models.FLight;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlightRepository extends PagingAndSortingRepository<FLight,Long> {
    List<FLight> findByOrigin(String origin);

    List<FLight> findByOriginAndDestination(String origin,String destination);


    List<FLight> findByDestinationIn(String...des);

    List<FLight> findByOriginIgnoreCase(String origin);


    Page<FLight> findByDestination(String destination, Pageable pageable);
}
