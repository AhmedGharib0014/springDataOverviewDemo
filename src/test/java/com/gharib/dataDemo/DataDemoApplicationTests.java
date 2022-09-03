package com.gharib.dataDemo;

import com.gharib.dataDemo.data.models.FLight;
import com.gharib.dataDemo.data.repositories.FlightRepository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DataDemoApplicationTests {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private FlightRepository flightRepository;


	@BeforeEach
	public void setUp(){
		flightRepository.deleteAll();
	}

	@Test
	void testSavingData() {

		//save data
		FLight fLight = new FLight();
		fLight.setOrigin("test");
		fLight.setDestination("test2");
		entityManager.persist(fLight);


		// query data
		final TypedQuery<FLight> result = entityManager.createQuery("SELECT f FROM FLight f",FLight.class);
		final List<FLight> flights = result.getResultList();

		Assertions.assertThat(flights).hasSize(1).first().isEqualTo(fLight);


	}



	@Test
	public void testCrudOperation(){

		FLight fLight = new FLight();
		fLight.setOrigin("test");
		fLight.setDestination("test2");

		flightRepository.save(fLight);

		Assertions.assertThat(flightRepository.findAll())
			.hasSize(1)
			.first()
			.isEqualTo(fLight);


		flightRepository.deleteById(fLight.getId());

		Assertions.assertThat(flightRepository.count()).isZero();

	}

	@Test
	public  void testDerivedByField(){
		FLight fLight1 = createFlightByOrigin("origin");
		FLight fLight2 = createFlightByOrigin("origin");
		FLight fLight3 = createFlightByOrigin("origin2");


		flightRepository.save(fLight1);
		flightRepository.save(fLight2);
		flightRepository.save(fLight3);

		List<FLight> flights = flightRepository.findByOrigin("origin");

		Assertions.assertThat(flights).hasSize(2);

	}

	@Test
	public  void testDerivedLogicAnd(){
		FLight fLight1 = createFlightByOriginAndDestination("origin","des");
		FLight fLight2 = createFlightByOriginAndDestination("origin","de2");
		FLight fLight3 = createFlightByOriginAndDestination("origin","de2");

		flightRepository.save(fLight1);
		flightRepository.save(fLight2);
		flightRepository.save(fLight3);

		List<FLight> flights = flightRepository.findByOriginAndDestination("origin","des");

		Assertions.assertThat(flights).hasSize(1);

	}

	@Test
	public  void testDerivedLogicOr(){
		FLight fLight1 = createFlightByOriginAndDestination("origin","des");
		FLight fLight2 = createFlightByOriginAndDestination("origin","de2");
		FLight fLight3 = createFlightByOriginAndDestination("origin","de2");

		flightRepository.save(fLight1);
		flightRepository.save(fLight2);
		flightRepository.save(fLight3);

		List<FLight> flights = flightRepository.findByDestinationIn("de2","des");

		Assertions.assertThat(flights).hasSize(3);

	}


	@Test
	public  void testDerivedLogicIgnoreCase(){
		FLight fLight1 = createFlightByOriginAndDestination("origin","des");
		FLight fLight2 = createFlightByOriginAndDestination("origin","de2");
		FLight fLight3 = createFlightByOriginAndDestination("origin","de2");

		flightRepository.save(fLight1);
		flightRepository.save(fLight2);
		flightRepository.save(fLight3);

		List<FLight> flights = flightRepository.findByOriginIgnoreCase("origin");

		Assertions.assertThat(flights).hasSize(3);

	}

	public  FLight createFlightByOrigin(String origin){
		FLight fLight = new FLight();
		fLight.setOrigin(origin);
		fLight.setDestination("any thing");
		return  fLight;
	}


	public  FLight createFlightByOriginAndDestination(String origin,String des){
		FLight fLight = new FLight();
		fLight.setOrigin(origin);
		fLight.setDestination(des);
		return  fLight;
	}
}
