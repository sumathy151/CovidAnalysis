package com.mindtree.covidAnalysis.entity;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="covid_data")
public class CovidData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private LocalDate date;
	private String state;
	private String district;
	private String tested;
	private String confirmed;
	private String recovered;
	
	@Transient
	private String firstState;
	@Transient
	private String secondState;
	@Transient
	private LocalDate startDate;
	
	@Transient
	private LocalDate endDate;
	
	
	
	public CovidData()
	{
		
	
	}
	
	
	public CovidData(int id, LocalDate date, String state, String district, String tested, String confirmed,
			String recovered) {
		
		this.id=id;
		this.date = date;
		this.state = state;
		this.district = district;
		this.tested = tested;
		this.confirmed=confirmed;
		
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	/**
	 * @return the tested
	 */
	public String getTested() {
		return tested;
	}
	/**
	 * @param tested the tested to set
	 */
	public void setTested(String tested) {
		this.tested = tested;
	}
	/**
	 * @return the confirmed
	 */
	public String getConfirmed() {
		return confirmed;
	}
	
	public Integer getConfirmedInt() {
		return Integer.valueOf(confirmed);
	}
	/**
	 * @param confirmed the confirmed to set
	 */
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	/**
	 * @return the recovered
	 */
	public String getRecovered() {
		return recovered;
	}
	/**
	 * @param recovered the recovered to set
	 */
	public void setRecovered(String recovered) {
		this.recovered = recovered;
	}
	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the firstState
	 */
	public String getFirstState() {
		return firstState;
	}
	/**
	 * @param firstState the firstState to set
	 */
	public void setFirstState(String firstState) {
		this.firstState = firstState;
	}
	/**
	 * @return the secondState
	 */
	public String getSecondState() {
		return secondState;
	}
	/**
	 * @param secondState the secondState to set
	 */
	public void setSecondState(String secondState) {
		this.secondState = secondState;
	}
	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CovidData [id=" + id + ", date=" + date + ", state=" + state + ", district=" + district + ", tested="
				+ tested + ", confirmed=" + confirmed + ", recovered=" + recovered + ", firstState=" + firstState
				+ ", secondState=" + secondState + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmed == null) ? 0 : confirmed.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((firstState == null) ? 0 : firstState.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recovered == null) ? 0 : recovered.hashCode());
		result = prime * result + ((secondState == null) ? 0 : secondState.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((tested == null) ? 0 : tested.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CovidData other = (CovidData) obj;
		if (confirmed == null) {
			if (other.confirmed != null)
				return false;
		} else if (!confirmed.equals(other.confirmed))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (firstState == null) {
			if (other.firstState != null)
				return false;
		} else if (!firstState.equals(other.firstState))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recovered == null) {
			if (other.recovered != null)
				return false;
		} else if (!recovered.equals(other.recovered))
			return false;
		if (secondState == null) {
			if (other.secondState != null)
				return false;
		} else if (!secondState.equals(other.secondState))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (tested == null) {
			if (other.tested != null)
				return false;
		} else if (!tested.equals(other.tested))
			return false;
		return true;
	}
	

}
