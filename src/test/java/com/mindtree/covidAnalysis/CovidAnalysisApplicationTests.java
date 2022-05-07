package com.mindtree.covidAnalysis;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindtree.covidAnalysis.entity.CovidData;
import com.mindtree.covidAnalysis.exception.InvalidStateCodeException;
import com.mindtree.covidAnalysis.exception.NoDataFoundException;
import com.mindtree.covidAnalysis.repository.CovidDataRepository;
import com.mindtree.covidAnalysis.service.CovidDataService;

@RunWith(SpringRunner.class)
@SpringBootTest
class CovidAnalysisApplicationTests {
	
	@Autowired
	private CovidDataService service;

	@MockBean
	private CovidDataRepository repository;

	@Test
	void contextLoads() {
	}
	
	
	
	
	
	@Test
	public void testGetAllStatesValidData() {
		
		
		when(repository.findAll()).thenReturn(Stream.of(new CovidData(1,LocalDate.of(2020, 8, 07),"JK","Samba","0","11","0"), new CovidData(2,LocalDate.of(2020, 8, 07),"JK","Kupwara","0","28","6")).collect(Collectors.toList()));
		assertEquals(1, service.getAllStates().size());
	}
	
	@Test
	public void testGetDistrictsValidData() throws InvalidStateCodeException  {
		
	List<CovidData> list = Stream.of(new CovidData(2,LocalDate.of(2020, 8, 07),"JK","Kupwara","0","28","6"),new CovidData(1,LocalDate.of(2020, 8, 07),"JK","Samba","0","11","0")).collect(Collectors.toList());
		
	when(repository.findByState("JK")).thenReturn(list);
	
	assertEquals(list, service.getDistricts("JK"));
		
	}
	
	@Test
	public void testGetDistrictsInvalidSorting() throws InvalidStateCodeException  {
		
	List<CovidData> list = Stream.of(new CovidData(1,LocalDate.of(2020, 8, 07),"JK","Samba","0","11","0"),new CovidData(2,LocalDate.of(2020, 8, 07),"JK","Kupwara","0","28","6")).collect(Collectors.toList());
		
	when(repository.findByState("JK")).thenReturn(list);
	
	assertFalse(list.equals(service.getDistricts("JK")));
		
	}
	
	@Test
	public void testGetDistrictsInvalidState() throws InvalidStateCodeException{
		
		InvalidStateCodeException thrown = Assertions.assertThrows(InvalidStateCodeException.class, () -> {
			
			
			//Stream.of(new CovidData(1,LocalDate.of(2020, 8, 07),"JK","Samba","0","11","0"),new CovidData(2,LocalDate.of(2020, 8, 07),"JK","Kupwara","0","28","6")).collect(Collectors.toList());
			
			when(repository.findByState("zz")).thenReturn(new ArrayList<>());
			
			service.getDistricts("zz");
	  });

	  Assertions.assertEquals("Invalid State Code, Please check your input", thrown.getMessage());
		
	}
	
	
	@Test
	public void testDisplayDataWithinDateRangedValidData() throws NoDataFoundException{
		
			
			
			List<CovidData> list= Stream.of(new CovidData(1,LocalDate.of(2020, 8, 07),"JK","Samba","0","11","0"),new CovidData(2,LocalDate.of(2020, 8, 07),"JK","Kupwara","0","28","6")).collect(Collectors.toList());
			
			when(repository.findDataByDateRange(LocalDate.of(2020, 8, 07),LocalDate.of(2020, 8, 8))).thenReturn(list);
			
			assertEquals(list,service.displayDataWithinDateRange(LocalDate.of(2020, 8, 07),LocalDate.of(2020, 8, 8)));
	
	}
	
	
	
	
	@Test
	public void testDisplayDataWithinDateRangedNoRecords() throws NoDataFoundException{
		
		NoDataFoundException thrown = Assertions.assertThrows(NoDataFoundException.class, () -> {
			
			
			//Stream.of(new CovidData(1,LocalDate.of(2020, 8, 07),"JK","Samba","0","11","0"),new CovidData(2,LocalDate.of(2020, 8, 07),"JK","Kupwara","0","28","6")).collect(Collectors.toList());
			
			when(repository.findDataByDateRange(LocalDate.of(2019, 8, 07),LocalDate.of(2019, 8, 8))).thenReturn(new ArrayList<>());
			
			service.displayDataWithinDateRange(LocalDate.of(2019, 8, 07),LocalDate.of(2019, 8, 8));
	  });

	  Assertions.assertEquals("No Data Present", thrown.getMessage());
		
	}
	
	
	
	@Test
	public void testDisplayConfirmedCasesValidData() throws NoDataFoundException
	{

		List<CovidData> list = Stream.of(new CovidData(1,LocalDate.of(2020, 8, 07),"JK","Samba","0","11","0"),new CovidData(2,LocalDate.of(2020, 8, 07),"JK","Kupwara","0","28","6")).collect(Collectors.toList());

		when(repository.findByDateAndState(LocalDate.of(2020, 8, 07),LocalDate.of(2020, 8, 07),"JK","TN")).thenReturn(list);
		
       assertEquals(list,service.displayConfirmedCases(LocalDate.of(2020, 8, 07),LocalDate.of(2020, 8, 07),"JK","TN"));
	}
	
	
	
	
	@Test
	public void testDisplayConfirmedCasesNoRecords() throws NoDataFoundException
	{
		NoDataFoundException thrown = Assertions.assertThrows(NoDataFoundException.class, () -> {


		when(repository.findByDateAndState(LocalDate.of(2019, 8, 07),LocalDate.of(2019, 8, 07),"JK","TN")).thenReturn(new ArrayList<>());
		
		service.displayConfirmedCases(LocalDate.of(2019, 8, 07),LocalDate.of(2019, 8, 07),"JK","TN");
		});
		  Assertions.assertEquals("No Data Present", thrown.getMessage());

	}
	

}
