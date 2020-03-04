package com.cloudelements.demo.model.vdr;

import com.cloudelements.demo.model.business.TaxData;

import lombok.Data;

@Data
public class ErpGoodsIssue {

	private String id, present_at_all_location, type;
	private String[] present_at_location_ids;
	
	
	private TaxData tax_data;
}
