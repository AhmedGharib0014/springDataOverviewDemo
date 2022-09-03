package com.gharib.dataDemo;

import com.gharib.dataDemo.data.models.FLight;
import com.gharib.dataDemo.data.repositories.FlightRepository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class DataDemoApplicationTests {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private FlightRepository flightRepository;

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

}
