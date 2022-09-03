package com.gharib.dataDemo;


import com.gharib.dataDemo.data.models.FLight;
import com.gharib.dataDemo.data.repositories.FlightRepository;
import com.gharib.dataDemo.data.tranctions.TransactionService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class transactionTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    public void setUp(){
        flightRepository.deleteAll();
    }

    @Test
    public  void shouldSaveFlight(){
        FLight fLight = new FLight();
        fLight.setOrigin("test");
        fLight.setDestination("test2");

        try{
            transactionService.saveWithoutTransaction(fLight);

        }catch (Exception e){

        }finally {
            Iterable<FLight> fLights = flightRepository.findAll();
            Assertions.assertThat(fLights).hasSize(1);
        }


    }

    @Test
    public  void shouldNotSaveFlight(){
        FLight fLight = new FLight();
        fLight.setOrigin("test");
        fLight.setDestination("test2");

        try{
            transactionService.saveWithTransaction(fLight);

        }catch (Exception e){

        }finally {
            Iterable<FLight> fLights = flightRepository.findAll();
            Assertions.assertThat(fLights).hasSize(0);
        }


    }


}
