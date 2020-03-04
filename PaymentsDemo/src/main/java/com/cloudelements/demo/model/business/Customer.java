package com.cloudelements.demo.model.business;

import lombok.Data;

@Data
public class Customer {

	public Customer(String firstname, String lastname, String email, String company) {
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.companyName = company;
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.emailAddress = email;
		this.name = company;
		this.company = company;
	}

	public Customer() {
		
	};
	
	public String id, lastname, firstname, emailAddress, lastName, firstName, companyName, email, reference, currencyId, name, company;
	
}
