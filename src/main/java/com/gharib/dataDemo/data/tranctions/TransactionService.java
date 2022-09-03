package com.gharib.dataDemo.data.tranctions;


import com.gharib.dataDemo.data.models.FLight;
import com.gharib.dataDemo.data.repositories.FlightRepository;

import javax.transaction.Transactional;

import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
   private final FlightRepository flightRepository;


    public TransactionService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }



    public  void saveWithoutTransaction(FLight fLight){
        flightRepository.save(fLight);
        throw new  RuntimeException();
    }

    @Transactional
    public  void saveWithTransaction(FLight fLight){
        flightRepository.save(fLight);
        throw new  RuntimeException();
    }

}
