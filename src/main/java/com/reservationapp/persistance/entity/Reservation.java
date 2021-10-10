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
	private String tableName;
	@NotNull
	private Timestamp reservationBegin;

	public Reservation() {
		
	}
	
	public Reservation(final String tableNumber, final Timestamp reservationBegin) {
		this.tableName = tableNumber;
		this.reservationBegin = reservationBegin;
	}
	
	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public String getTableNumber() {
		return tableName;
	}

	public void setTableNumber(final String tableNumber) {
		this.tableName = tableNumber;
	}

	public Timestamp getReservationBegin() {
		return reservationBegin;
	}

	public void setReservationBegin(final Timestamp reservationBegin) {
		this.reservationBegin = reservationBegin;
	}

}
