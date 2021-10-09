package com.reservationapp.persistance.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@NotNull
	private Integer tableNumber;
	@NotNull
	private Timestamp reservationBegin;

	public Reservation() {
		
	}
	
	public Reservation(final Integer tableNumber, final Timestamp reservationBegin) {
		this.tableNumber = tableNumber;
		this.reservationBegin = reservationBegin;
	}
	
	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(final Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Timestamp getReservationBegin() {
		return reservationBegin;
	}

	public void setReservationBegin(final Timestamp reservationBegin) {
		this.reservationBegin = reservationBegin;
	}

}
