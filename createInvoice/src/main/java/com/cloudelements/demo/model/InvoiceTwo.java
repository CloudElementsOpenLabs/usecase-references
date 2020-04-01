package com.cloudelements.demo.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class InvoiceTwo {
	private ArrayList<Product> item;
	private String message, subsidiary, amount, currency, entity, description, totalValue, dueDate, transactionDate;

}
