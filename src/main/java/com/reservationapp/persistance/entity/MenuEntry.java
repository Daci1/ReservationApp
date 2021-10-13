package com.reservationapp.persistance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

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
	@Column(unique = true)
	private String productName;
	@NotNull
	private String category;
	@Lob
	private String image;

	public MenuEntry() {
	}

	public MenuEntry(final Double price, final String description, final Double cantity, final String productName,
			final String category, String image) {
		super();
		this.price = price;
		this.description = description;
		this.cantity = cantity;
		this.productName = productName;
		this.category = category;
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@JsonIgnore
	public Integer getId() {
		return id;
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
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "MenuEntry [price=" + price + ", description=" + description + ", cantity=" + cantity + ", productName="
				+ productName + ", category=" + category + "]";
	}
}
