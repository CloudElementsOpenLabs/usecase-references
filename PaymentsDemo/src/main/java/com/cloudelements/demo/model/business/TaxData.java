package com.cloudelements.demo.model.business;

import lombok.Data;

@Data
public class TaxData {

	private String enabled, calculation_phase, fee_applies_to_custom_amounts, inclusion_type, name, percentage;
	
	
}
