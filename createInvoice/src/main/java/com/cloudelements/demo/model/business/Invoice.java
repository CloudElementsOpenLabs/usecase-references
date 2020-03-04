package com.cloudelements.demo.model.business;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Invoice {
	private ArrayList<Product> item;
	private String message, subsidiary, amount, currency, entity, description, totalValue, dueDate, transactionDate;

}
