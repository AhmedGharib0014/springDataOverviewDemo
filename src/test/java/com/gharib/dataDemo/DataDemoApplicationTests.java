package com.gharib.dataDemo;

import com.gharib.dataDemo.data.models.FLight;
import com.gharib.dataDemo.data.repositories.FlightRepository;
import java.util.Iterator;
import java.util.List;
import net.bytebuddy.TypeCache;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
	public  void shouldSortResultByOrigin(){
		FLight fLight1 = createFlightByOriginAndDestination("c","des");
		FLight fLight2 = createFlightByOriginAndDestination("b","de2");
		FLight fLight3 = createFlightByOriginAndDestination("a","de2");

		flightRepository.save(fLight1);
		flightRepository.save(fLight2);
		flightRepository.save(fLight3);

		Iterable<FLight> flights = flightRepository.findAll(Sort.by("origin"));
		Iterator<FLight> it = flights.iterator();


		Assertions.assertThat(flights).hasSize(3);
		Assertions.assertThat(it.next().getOrigin()).isEqualTo("a");
		Assertions.assertThat(it.next().getOrigin()).isEqualTo("b");
		Assertions.assertThat(it.next().getOrigin()).isEqualTo("c");

	}

	@Test
	public  void shouldSortResultByOriginAndDesination(){
		FLight fLight1 = createFlightByOriginAndDestination("b","des2");
		FLight fLight2 = createFlightByOriginAndDestination("a","des2");
		FLight fLight3 = createFlightByOriginAndDestination("a","des1");

		flightRepository.save(fLight1);
		flightRepository.save(fLight2);
		flightRepository.save(fLight3);

		Iterable<FLight> flights = flightRepository.findAll(Sort.by("origin","destination"));
		Iterator<FLight> it = flights.iterator();


		Assertions.assertThat(flights).hasSize(3);
		Assertions.assertThat(it.next().getDestination()).isEqualTo("des1");
		Assertions.assertThat(it.next().getDestination()).isEqualTo("des2");
		Assertions.assertThat(it.next().getOrigin()).isEqualTo("b");

	}


	@Test
	public  void testPaging(){

		for (int i = 0; i < 50; i++) {
			FLight fLight = createFlightByOrigin(String.valueOf(i));
			flightRepository.save(fLight);
		}




		Page<FLight> flightPage = flightRepository.findAll(PageRequest.of(0,5));


		Assertions.assertThat(flightPage.getTotalPages()).isEqualTo(10);
		Assertions.assertThat(flightPage.getTotalElements()).isEqualTo(50);
		Assertions.assertThat(flightPage.getNumberOfElements()).isEqualTo(5);
		Assertions.assertThat(flightPage.getContent())
			.extracting(FLight::getOrigin)
			.containsExactly("0","1","2","3","4");

	}

	@Test
	public  void testPagingAndSorting(){
		for (int i = 0; i < 50; i++) {
			FLight fLight = createFlightByOrigin(String.valueOf(i));
			flightRepository.save(fLight);
		}



		Page<FLight> flightPage = flightRepository.findAll(PageRequest.of(10,5,Sort.by(Sort.Direction.DESC,"origin")));


		Assertions.assertThat(flightPage.getTotalPages()).isEqualTo(10);
		Assertions.assertThat(flightPage.getTotalElements()).isEqualTo(50);
		Assertions.assertThat(flightPage.getNumberOfElements()).isEqualTo(5);
		Assertions.assertThat(flightPage.getContent())
			.extracting(FLight::getOrigin)
			.containsExactly("49","48","47","46","45");

	}

	@Test
	public  void testPagingAndSortingByDerivedQuery(){
		for (int i = 0; i < 50; i++) {
			FLight fLight = createFlightByOrigin(String.valueOf(i));
			flightRepository.save(fLight);
		}



		Page<FLight> flightPage = flightRepository.findByDestination("any thing",PageRequest.of(0,5,Sort.by(Sort.Direction.ASC,"origin")));


		Assertions.assertThat(flightPage.getTotalPages()).isEqualTo(10);
		Assertions.assertThat(flightPage.getTotalElements()).isEqualTo(50);
		Assertions.assertThat(flightPage.getNumberOfElements()).isEqualTo(5);
		Assertions.assertThat(flightPage.getContent())
			.extracting(FLight::getOrigin)
			.containsExactly("0","1","10","11","12");

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
