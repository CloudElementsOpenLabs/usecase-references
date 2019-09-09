package com.cloudelements.demo.model;

import lombok.Data;

@Data
public class Product {
	public Product(String item, String quantity, String rate) {
		this.id = item;
		this.quantity = quantity;
		this.rate = rate;
	}

	private String id, quantity, rate, location;
}
