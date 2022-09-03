package com.gharib.dataDemo.data.repositories;

import com.gharib.dataDemo.data.models.FLight;

import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<FLight,Long> {
}
