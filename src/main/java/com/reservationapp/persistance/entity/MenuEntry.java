package com.reservationapp.persistance.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
public class MenuEntry {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@NotNull
	private Double price;
	@NotNull
	private String description;
	@NotNull
	private Double cantity;
	@NotNull
	private String productName;

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public MenuEntry() {
	}

	public MenuEntry(final Double price, final String description, final Double cantity, final String productName) {
		super();
		this.price = price;
		this.description = description;
		this.cantity = cantity;
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Double getCantity() {
		return cantity;
	}

	public void setCantity(final Double cantity) {
		this.cantity = cantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}
}
