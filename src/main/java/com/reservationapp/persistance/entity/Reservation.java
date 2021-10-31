package com.reservationapp.persistance.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
	
	public Reservation(final String tableName, final Timestamp reservationBegin) {
		this.tableName = tableName;
		this.reservationBegin = reservationBegin;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		return this.getTableNumber().equals(other.getTableNumber()) && this.getReservationBegin().toString().equals(other.getReservationBegin().toString());
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

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", tableName=" + tableName + ", reservationBegin=" + reservationBegin + "]";
	}
	
	@JsonIgnore
	public User getUser() {
		return this.user;
	}
	
	@JsonIgnore
	public void setUser(User user) {
		this.user = user;
	}

}
