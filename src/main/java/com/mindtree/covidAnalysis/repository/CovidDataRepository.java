package com.mindtree.covidAnalysis.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mindtree.covidAnalysis.entity.CovidData;

@Repository
public interface CovidDataRepository extends JpaRepository<CovidData,String> {

	public List<CovidData> findByState(String state);
	
	@Transactional
    @Query(value="select * from covid_data where date between ?1 and ?2",nativeQuery=true)
	public List<CovidData> findDataByDateRange(LocalDate startDate,LocalDate endDate);
	
	@Transactional
     @Query(value="select * from covid_data where date between ?1 and ?2 and (state=?3 or state=?4)",nativeQuery=true)
	public List<CovidData> findByDateAndState(LocalDate startDate, LocalDate endDate, String firstState, String secondState);

    
	public List<CovidData> findAll();
	
	



}
